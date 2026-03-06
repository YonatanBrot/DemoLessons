package frc.robot.subsystems.fourbar;

import static frc.robot.subsystems.fourbar.FourbarConstants.CLOSE_ANGLE;
import static frc.robot.subsystems.fourbar.FourbarConstants.CLOSING_VOLTAGE;
import static frc.robot.subsystems.fourbar.FourbarConstants.HOMING_VOLTAGE;
import static frc.robot.subsystems.fourbar.FourbarConstants.OPENING_VOLTAGE;
import static frc.robot.subsystems.fourbar.FourbarConstants.OPEN_ANGLE;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.utils.CommandsUtils;
import frc.robot.utils.MathUtils;
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
        TunablesManager.add(fourbar.getName() + "/TunableBounce", tunableBounce().fullTunable());
        TunablesManager.add(fourbar.getName() + "/Open", TunableCommand.wrap((tunablesTable) -> open()).fullTunable());
        TunablesManager.add(fourbar.getName() + "/Close",
                TunableCommand.wrap((tunablesTable) -> close()).fullTunable());
    }

    public Command moveToAngle(DoubleSupplier angle) {
        return homing().andThen(fourbar.runOnce(() -> {
            fourbar.resetPID();
        }).andThen(fourbar.run(() -> {
            fourbar.setVoltage(fourbar.calculatePID(angle.getAsDouble()));
        }))).finallyDo(fourbar::stop).withName("Move to angle");
    }

    public TunableCommand tunableBounce() {
        return TunableCommand.wrap((tunablesTable) -> {
            DoubleHolder changeRate = tunablesTable.addNumber("Change Rate", 1.0);
            DoubleHolder minAngle = tunablesTable.addNumber("minAngle", 0.0);
            DoubleHolder maxAngle = tunablesTable.addNumber("maxAngle", 0.0);
            return moveToAngle(
                    () -> MathUtils.cosineWave(minAngle.get(), maxAngle.get(),
                            Timer.getFPGATimestamp() * changeRate.get()))
                    .withName("tunableBounce");
        });
    }

    public Command bounce(DoubleSupplier minAngle, DoubleSupplier maxAngle) {
        return moveToAngle(
                () -> MathUtils.cosineWave(minAngle.getAsDouble(), maxAngle.getAsDouble(),
                        Timer.getFPGATimestamp() * 3))
                .withName("bounce");
    }

    public Command bounce(double minAngle, double maxAngle) {
        return bounce(() -> minAngle, () -> maxAngle);
    }

    public Command moveToAngle(double angle) {
        return moveToAngle(() -> angle);
    }

    public TunableCommand tunableMoveToAngle() {
        return TunableCommand.wrap((tunablesTable) -> {
            DoubleHolder angle = tunablesTable.addNumber("angle", 0.0);
            return moveToAngle(angle::get).withName("tunableMoveToAngle");
        });
    }

    public Command open() {
        return CommandsUtils.dynamicSwitchBetweenCommands(() -> fourbar.isAtAngle(OPEN_ANGLE),
                fourbar.run(fourbar::stop).repeatedly(),
                fourbar.run(() -> fourbar.setVoltage(OPENING_VOLTAGE)).repeatedly())
                .withName("open");
    }

    public Command close() {
        Command closeCommand = CommandsUtils.dynamicSwitchBetweenCommands(() -> fourbar.isAtAngle(CLOSE_ANGLE),
                fourbar.run(fourbar::stop).repeatedly(),
                fourbar.run(() -> fourbar.setVoltage(CLOSING_VOLTAGE)).repeatedly());
        return CommandsUtils
                .dynamicSwitchBetweenCommands(fourbar::isStuck, moveToAngle(fourbar::getAngleDegrees), closeCommand)
                .withName("close");
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
