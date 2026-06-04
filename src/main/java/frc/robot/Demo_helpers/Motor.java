package frc.robot.Demo_helpers;

import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.MotorAlignmentValue;

public class Motor extends TalonFX{
    public Motor(int id) {
        super(id);
    }

    public void follow(int motorID) {
        this.setControl(new Follower(motorID, MotorAlignmentValue.Aligned));
    }

    public double getCurrent() {
        return this.getStatorCurrent().getValueAsDouble();
    }

    public double getSpeed() {
        return this.getVelocity().getValueAsDouble();
    }
}
