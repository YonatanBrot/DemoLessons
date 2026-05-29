package frc.robot.Demo_helpers;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.MotorAlignmentValue;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import frc.robot.subsystems.flywheel.Const.CurrentLimits;
import frc.robot.subsystems.flywheel.Const.IDs;
import frc.robot.subsystems.flywheel.Const.kFF;
import frc.robot.subsystems.flywheel.Const.kPID;

import static frc.robot.subsystems.flywheel.Const.*;

//errors they could do anyway:
// - writing public and private
// - types

public class Example extends FlyWheelBase{
    private TalonFX motor1 = new TalonFX(IDs.FLYWHEEL_MOTOR1_ID);
    private TalonFX motor2 = new TalonFX(IDs.FLYWHEEL_MOTOR2_ID);
    private PIDController pid = new PIDController(kPID.KP, kPID.KI, kPID.KD); 
    private SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(kFF.KS, kFF.KV, kFF.KA);
    //בשניהם - לוודאת שהסדר נכון
    public Example() {
        TalonFXConfiguration config = new TalonFXConfiguration();
        config.CurrentLimits.StatorCurrentLimit = CurrentLimits.STATOR_CURRENT_LIMIT;
        config.CurrentLimits.StatorCurrentLimitEnable = true;
        config.CurrentLimits.SupplyCurrentLimit = CurrentLimits.STATOR_CURRENT_LIMIT;
        config.CurrentLimits.SupplyCurrentLimitEnable = true;
        config.CurrentLimits.SupplyCurrentLowerLimit = CurrentLimits.SUPPLY_CURRENT_LOWER_LIMIT;
        config.CurrentLimits.SupplyCurrentLowerTime = CurrentLimits.SUPPLY_CURRENT_LOWER_TIME;
        //A limit for each of the supplied limits, plus 2 enables
        motor1.getConfigurator().apply(config);
        motor2.getConfigurator().apply(config);
        //apply the config to both motors
        motor2.setControl(new Follower(
            IDs.FLYWHEEL_MOTOR1_ID, MotorAlignmentValue.Aligned));
        //כל השורה הנאצית הזאת
    }

    public void setSpeed(double speedRPM) {
        double volt = feedforward.calculate(speedRPM);
        volt += pid.calculate(getSpeed(), speedRPM);
        //PID ואז מוסיפים FF לדאוג שמחשבים 
        volt = MathUtil.clamp(volt, MAX_VOLTAGE, MIN_VOLTAGE);
        //They 100% will forget the clamp
        VoltageOut volts = new VoltageOut(volt);
        //מעצבנים CTRE אובייקט וולט כי
        motor1.setControl(volts);
    }

    public void manualController(double speed){
        motor1.setControl(new VoltageOut(MAX_VOLTAGE*speed));
    }

    public double getMotor1Current() {
        return motor1.getStatorCurrent().getValueAsDouble();
    }

    public double getMotor2Current() {
        return motor2.getStatorCurrent().getValueAsDouble();
    }

    public void resetPID() {
        pid.reset();
    }

    public void stop() {
        motor1.setControl(new VoltageOut(0));
    }

    public double getSpeed() {
        return motor1.getVelocity().getValueAsDouble();
        //this being velocity and not speed can annoy
        //and the get as double will definetly be annoying
    } 

    public boolean isAtSpeed(double speedRPM) {
        double currentSpeed = getSpeed();
        return currentSpeed - SPEED_TOLERENCE <= speedRPM
            && speedRPM <= currentSpeed + SPEED_TOLERENCE;
        //use tolerence, not ==
    }
}
