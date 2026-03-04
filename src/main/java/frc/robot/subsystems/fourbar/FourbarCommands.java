package frc.robot.subsystems.fourbar;

import static frc.robot.subsystems.fourbar.FourbarConstants.HOMING_VOLTAGE;
import static frc.robot.subsystems.fourbar.FourbarConstants.MAX_ANGLE;
import static frc.robot.subsystems.fourbar.FourbarConstants.MIN_ANGLE;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import team2679.atlantiskit.tunables.TunablesManager;
import team2679.atlantiskit.tunables.extensions.TunableCommand;
import team2679.atlantiskit.valueholders.DoubleHolder;

public class FourbarCommands {
    private Fourbar fourbar;

    public FourbarCommands(Fourbar fourbar) {
        this.fourbar = fourbar;
        TunablesManager.add("TunableSetVoltages/FourbarSetVoltage", tunableSetVoltage().fullTunable());
        TunablesManager.add(fourbar.getName() + "/TunableMoveToAngle", tunableMoveToAngle().fullTunable());
        TunablesManager.add(fourbar.getName() + "/TunableHoming", tunableHoming().fullTunable());
    }

    public Command moveToAngle(DoubleSupplier angle) {
        return homing().andThen(fourbar.runOnce(() -> {
            fourbar.resetPID();
        }).andThen(fourbar.run(() -> {
            double voltage = fourbar.calculatePID(angle.getAsDouble());
            fourbar.setVoltage(voltage);
        }))).finallyDo(fourbar::stop).withName("Move to angle");
    }

    public Command moveToAngle(double angle) {
        return moveToAngle(() -> angle);
    }

    public TunableCommand tunableMoveToAngle() {
        return TunableCommand.wrap((tunablesTable) -> {
            DoubleHolder angle = tunablesTable.addNumber("angle", MIN_ANGLE);
            return moveToAngle(angle::get).withName("tunableMoveToAngle");
        });
    }

    public Command open() {
        return moveToAngle(MAX_ANGLE).withName("Open");
    }

    public Command close() {
        return moveToAngle(MIN_ANGLE).withName("Close");
    }

    public Command homing() {
        return fourbar.run(() -> fourbar.setVoltage(HOMING_VOLTAGE)).onlyWhile(() -> !fourbar.isCalibrated())
                .finallyDo(fourbar::stop)
                .withName("Homing");
    }

    public TunableCommand tunableHoming() {
        return TunableCommand.wrap((tunablesTable) -> {
            DoubleHolder voltage = tunablesTable.addNumber("voltage", HOMING_VOLTAGE);
            return fourbar.run(() -> fourbar.setVoltage(voltage.get())).withName("tunableHoming");
        });
    }

    public Command manualController(DoubleSupplier speed) {
        return fourbar.run(() -> {
            fourbar.setVoltage(speed.getAsDouble() * FourbarConstants.MAX_VOLTAGE);
        }).withName("Fourbar manual controller");
    }

    private TunableCommand tunableSetVoltage() {
        return TunableCommand.wrap((tunablesTable) -> {
            DoubleHolder voltage = tunablesTable.addNumber("voltage", 0.0);
            return fourbar.run(() -> fourbar.setVoltage(voltage.get())).finallyDo(fourbar::stop)
                    .withName("Tunable fourbar set voltage");
        });
    }
}
