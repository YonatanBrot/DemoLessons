package frc.robot.Demo_helpers;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public abstract class FlyWheelBase extends SubsystemBase {
    public abstract void setSpeed(double speed);
    public abstract void stop();
    public abstract void manualController(double value);

    public abstract double getMotor1Current();
    public abstract double getMotor2Current();
    public abstract double getSpeed();

    public abstract boolean isAtSpeed(double speed);

    public abstract void resetPID();
}
