package frc.robot.Demo_helpers;

import static frc.robot.subsystems.flywheel.Const.*;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import team2679.atlantiskit.logfields.LogFieldsTable;

public abstract class FlyWheelBase extends SubsystemBase {
    protected Motor motor1, motor2;
    protected VoltCalculator voltCalculator = new VoltCalculator();
    
    private LogFieldsTable logFieldsTable = new LogFieldsTable("Flywheel");
    
    public abstract void setSpeed(double speed);

    public abstract double getMotor1Current();
    public abstract double getMotor2Current();
    public abstract double getSpeed();

    public abstract boolean isAtSpeed(double speed);

    public FlyWheelBase() {
        logFieldsTable.addDouble("current 1", this::getMotor1Current);
        logFieldsTable.addDouble("current 2", this::getMotor2Current);
        logFieldsTable.addDouble("speed", this::getSpeed);
    }

    protected void log(String name, double value){
        logFieldsTable.recordOutput(name, value);
    }

    public void manualController(double speed) {
        motor1.setVoltage(speed*MAX_VOLTAGE);
    }

    public void resetPID() {
        voltCalculator.resetPID();
    }

    public void stop() {
        motor1.setVoltage(0);
    }
}