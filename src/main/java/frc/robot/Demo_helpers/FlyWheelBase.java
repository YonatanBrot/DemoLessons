package frc.robot.Demo_helpers;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import team2679.atlantiskit.logfields.LogFieldsTable;

public abstract class FlyWheelBase extends SubsystemBase {
    public abstract void setSpeed(double speed);
    public abstract void stop();
    public abstract void manualController(double value);

    public abstract double getMotor1Current();
    public abstract double getMotor2Current();
    public abstract double getSpeed();

    public abstract boolean isAtSpeed(double speed);

    public abstract void resetPID();

    private LogFieldsTable logFieldsTable = new LogFieldsTable("Flywheel");

    protected void log(String name, double value){
        logFieldsTable.recordOutput(name, value);
    }

    public FlyWheelBase() {
        logFieldsTable.addDouble("current 1", this::getMotor1Current);
        logFieldsTable.addDouble("current 2", this::getMotor2Current);
        logFieldsTable.addDouble("speed", this::getSpeed);
    }
}
