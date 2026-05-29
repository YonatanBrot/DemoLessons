package frc.robot.Demo_helpers;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.flywheel.FlyWheel;

public class FlyWheelCommands {
    private FlyWheel flyWheel;
    public FlyWheelCommands(FlyWheel flyWheel) {
        this.flyWheel = flyWheel;
    }

    public Command reachSpeed(Double speed){
        return flyWheel.run(flyWheel::resetPID)
        .andThen(flyWheel.run(() -> {
            flyWheel.setSpeed(speed);
        }))
        .finallyDo(flyWheel::stop)
        .withName("Flywheel reach speed");
    }

    public Command manualController(DoubleSupplier val){
        return flyWheel.run(() -> {
            flyWheel.manualController(val.getAsDouble());
        })
        .finallyDo(flyWheel::stop)
        .withName("Flywheel Maunal Controller");
    }


}
