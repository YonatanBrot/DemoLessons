package frc.robot.subsystems.fourbar.io;

import com.revrobotics.PersistMode;
import com.revrobotics.REVLibError;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import frc.robot.RobotMap;
import frc.robot.subsystems.fourbar.FourbarConstants;
import frc.robot.utils.AlertsFactory;
import team2679.atlantiskit.logfields.LogFieldsTable;
import team2679.atlantiskit.periodicalerts.PeriodicAlertsGroup;

public class FourbarIOSparkMax extends FourbarIO {

    private SparkMax motor = new SparkMax(RobotMap.CANBUS.FOURBAR_ID, MotorType.kBrushless);
    private SparkMaxConfig motorConfig = new SparkMaxConfig();

    public FourbarIOSparkMax(LogFieldsTable fields) {
        super(fields);

        motorConfig.smartCurrentLimit(FourbarConstants.CURRENT_LIMIT);
        motorConfig.idleMode(IdleMode.kCoast);
        REVLibError motorConfigError = motor.configure(motorConfig, ResetMode.kNoResetSafeParameters,
                PersistMode.kNoPersistParameters);
        AlertsFactory.revMotor(PeriodicAlertsGroup.defaultInstance.getSubGroup("Fourbar"),
                () -> motorConfigError, motor::getWarnings, motor::getFaults, "motor");
        
        motor.getEncoder().setPosition(0);
    }

    protected double getAngleRotations() {
        return motor.getEncoder().getPosition();
    }

    protected double getCurrent() {
        return motor.getOutputCurrent();
    }

    public void setVolt(double volt) {
        motor.setVoltage(volt);
    }
}
