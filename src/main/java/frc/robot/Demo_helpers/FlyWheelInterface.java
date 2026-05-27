package frc.robot.Demo_helpers;

public interface FlyWheelInterface {
    public void setSpeed(double speed);
    public void stop();
    public void manualController(double value);

    public double getMotor1Current();
    public double getMotor2Current();
    public double getSpeed();

    public boolean isAtSpeed(double speed);

    public void resetPID();
}
