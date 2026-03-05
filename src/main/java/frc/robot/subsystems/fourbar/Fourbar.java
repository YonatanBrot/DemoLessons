package frc.robot.subsystems.fourbar;

import static frc.robot.subsystems.fourbar.FourbarConstants.*;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.subsystems.fourbar.io.FourbarIO;
import frc.robot.subsystems.fourbar.io.FourbarIOSim;
import frc.robot.subsystems.fourbar.io.FourbarIOSparkMax;
import team2679.atlantiskit.helpers.RotationalSensorHelper;
import team2679.atlantiskit.logfields.LogFieldsTable;
import team2679.atlantiskit.tunables.Tunable;
import team2679.atlantiskit.tunables.TunableBuilder;
import team2679.atlantiskit.tunables.TunablesManager;
import team2679.atlantiskit.tunables.extensions.TunableArmFeedforward;
import team2679.atlantiskit.tunables.extensions.TunableTrapezoidProfile;

public class Fourbar extends SubsystemBase implements Tunable {
    private final PIDController pid = new PIDController(KP, KI, KD);
    private final TunableArmFeedforward feedforward = new TunableArmFeedforward(KS, KG, KV);
    private final TunableTrapezoidProfile trapezoidProfile = new TunableTrapezoidProfile(
            new TrapezoidProfile.Constraints(MAX_VELOCITY_DEG_PER_SEC, MAX_ACCELERATION_DEG_PER_SEC));
    private final LogFieldsTable fieldsTable = new LogFieldsTable(getName());
    private final FourbarIO io = Robot.isReal() ? new FourbarIOSparkMax(fieldsTable) : new FourbarIOSim(fieldsTable);
    private final RotationalSensorHelper sensorHelper;

    private double minAngle = MIN_ANGLE;
    private double maxAngle = MAX_ANGLE;

    private final Debouncer isStuckDebouncer = new Debouncer(STUCK_DEBOUNCE_SEC);

    private double desiredVoltage = 0;
    private boolean calibrated = false;

    public Fourbar() {
        sensorHelper = new RotationalSensorHelper(MAX_ANGLE);
        TunablesManager.add(getName(), (Tunable) this);
    }

    public void resetPID() {
        pid.reset();
    }

    @Override
    public void periodic() {
        sensorHelper.update(io.angleRotations.getAsDouble());
        fieldsTable.recordOutput("Desired Voltage", desiredVoltage);
        fieldsTable.recordOutput("isStuck", isStuck());
        fieldsTable.recordOutput("isCalibrated", isCalibrated());
        fieldsTable.recordOutput("angle", getAngleDegrees());
        fieldsTable.recordOutput("velocity", getVelocity());
        fieldsTable.recordOutput("Current command",
                getCurrentCommand() != null ? getCurrentCommand().getName() : "None");
        if (!calibrated && isStuck()) {
            sensorHelper.resetAngle(MIN_ANGLE);
            calibrated = true;
        }
    }

    public double getCurrent() {
        return io.current.getAsDouble();
    }

    public double getAngleDegrees() {
        return sensorHelper.getAngle();
    }

    public double getVelocity() {
        return sensorHelper.getVelocity();
    }

    public void setVoltage(double voltage) {
        if (((getAngleDegrees() > maxAngle && voltage > 0)
                || (getAngleDegrees() < minAngle && voltage < 0)) && isCalibrated()) {
            voltage = 0.0;
        }
        voltage = MathUtil.clamp(voltage, -MAX_VOLTAGE, MAX_VOLTAGE);
        desiredVoltage = voltage;
        io.setVolt(voltage);
    }

    public void stop() {
        desiredVoltage = 0;
        io.setVolt(0);
    }

    public TrapezoidProfile.State calculateTrapezoidProfile(double time, TrapezoidProfile.State initialState,
            TrapezoidProfile.State desiredState) {
        return trapezoidProfile.calculate(time, initialState, desiredState);
    }

    public double calculateFeedforward(double desiredAngleDegrees, double desiredSpeedDegPerSec, boolean usePID) {
        if (desiredAngleDegrees < minAngle || desiredAngleDegrees > maxAngle)
            return 0.0;
        if (isAtAngle(desiredAngleDegrees))
            return 0.0;
        fieldsTable.recordOutput("Desired angle", desiredAngleDegrees);
        fieldsTable.recordOutput("Desired speed", desiredSpeedDegPerSec);
        double volt = feedforward.calculate(desiredAngleDegrees, desiredSpeedDegPerSec);
        return usePID ? volt + pid.calculate(volt) : volt;
    }

    public boolean isAtAngle(double angle) {
        return Math.abs(getAngleDegrees() - angle) < ANGLE_TOLLERANCE;
    }

    private boolean isStuck() {
        return isStuckDebouncer.calculate(
                Math.abs(desiredVoltage) > 0 && Math.abs(getVelocity()) < STUCK_VELOCITY_THRESHOLD_DEG_PER_SEC);
    }

    public boolean isCalibrated() {
        return calibrated;
    }

    @Override
    public void initTunable(TunableBuilder builder) {
        builder.addChild("Forbar PID", pid);
        builder.addChild("Forbar FeedForward", feedforward);
        builder.addChild("Forbar Trapeziod Profile", trapezoidProfile);
        builder.addChild("Forbar RotationalSensorHelper", sensorHelper);
        builder.addDoubleProperty("Forbar minAngle", () -> minAngle, (newMinAngle) -> {
            minAngle = newMinAngle;
        });
        builder.addDoubleProperty("Forbar maxAngle", () -> maxAngle, (newMaxAngle) -> {
            maxAngle = newMaxAngle;
        });
    }
}
