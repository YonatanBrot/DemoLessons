package frc.robot.subsystems.fourbar;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.Command;
import team2679.atlantiskit.tunables.TunablesManager;
import team2679.atlantiskit.tunables.extensions.TunableCommand;
import team2679.atlantiskit.valueholders.DoubleHolder;
import team2679.atlantiskit.valueholders.ValueHolder;

public class FourbarCommands {
    private Fourbar fourbar;

    public FourbarCommands(Fourbar fourbar) {
        this.fourbar = fourbar;
        TunablesManager.add("TunableSetVoltages/FourbarSetVoltage", tunableSetVoltage().fullTunable());
    }

    public Command moveToAngle(DoubleSupplier angle) {
        ValueHolder<TrapezoidProfile.State> state = new ValueHolder<TrapezoidProfile.State>(null);
        return fourbar.runOnce(() -> {
            state.set(new TrapezoidProfile.State(fourbar.getAngleDegrees(), fourbar.getVelocity()));
            fourbar.resetPID();
        }).andThen(fourbar.run(() -> {
            state.set(fourbar.calculateTrapezoidProfile(
                    0.02, state.get(), new TrapezoidProfile.State(angle.getAsDouble(), 0)));
            double voltage = fourbar.calculateFeedforward(
                    state.get().position, state.get().velocity, true);
            fourbar.setVoltage(voltage);
        })).withName("Fourbar move to angle");
    }

    public Command getToAngleDegrees(double angle) {
        return moveToAngle(() -> angle);
    }

    public Command manualController(DoubleSupplier speed) {
        return fourbar.run(() -> {
            double ignore_mg = fourbar.calculateFeedforward(fourbar.getAngleDegrees(), fourbar.getVelocity(), false);
            fourbar.setVoltage(ignore_mg + speed.getAsDouble() * FourbarConstants.MAX_VOLTAGE);
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
