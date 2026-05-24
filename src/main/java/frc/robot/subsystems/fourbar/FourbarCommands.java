package frc.robot.subsystems.fourbar;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import team2679.atlantiskit.tunables.TunablesManager;
import team2679.atlantiskit.tunables.extensions.TunableCommand;
import team2679.atlantiskit.valueholders.DoubleHolder;

public class FourbarCommands {
    private Fourbar fourbar;

    public FourbarCommands(Fourbar fourbar) {
        this.fourbar = fourbar;
        TunablesManager.add("TunableSetVoltages/FourbarSetVoltage", tunableSetVoltage().fullTunable());
        TunablesManager.add(fourbar.getName() + "/SysId", sysId());
    }

    public Command sysId() {
        return Commands.sequence(
                fourbar.sysIdRoutine.quasistatic(SysIdRoutine.Direction.kForward),
                fourbar.sysIdRoutine.quasistatic(SysIdRoutine.Direction.kReverse),
                fourbar.sysIdRoutine.dynamic(SysIdRoutine.Direction.kForward),
                fourbar.sysIdRoutine.dynamic(SysIdRoutine.Direction.kReverse)).withName("sysId");
    }

    public Command open() {
        return fourbar.run(() -> {
            fourbar.setVoltage(-4, false);
        })
        .finallyDo(fourbar::stop)
        .withTimeout(1)
        .andThen(Commands.repeatingSequence(
            fourbar.run(() -> {
                fourbar.setVoltage(0.5, false);
            })
            .finallyDo(fourbar::stop),
            Commands.waitSeconds(4)
        ));
    }

    public Command close() {
        return fourbar.run(() -> {
            fourbar.setVoltage(4, false);
        }).finallyDo(fourbar::stop)
        .withTimeout(2);
    }

    public Command manualController(DoubleSupplier speed) {
        return fourbar.run(() -> {
            fourbar.setVoltage(speed.getAsDouble() * FourbarConstants.MAX_VOLTAGE, false);
        }).withName("Fourbar manual controller");
    }

    private TunableCommand tunableSetVoltage() {
        return TunableCommand.wrap((tunablesTable) -> {
            DoubleHolder voltage = tunablesTable.addNumber("voltage", 0.0);
            return fourbar.run(() -> fourbar.setVoltage(voltage.get(), true)).finallyDo(fourbar::stop)
                    .withName("Tunable fourbar set voltage");
        });
    }
}
