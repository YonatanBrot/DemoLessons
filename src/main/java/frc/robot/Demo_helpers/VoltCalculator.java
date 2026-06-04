package frc.robot.Demo_helpers;

import static frc.robot.Demo_helpers.Const.*;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;

public class VoltCalculator {
    private PIDController pid = new PIDController(KP, KI, KD);
    private SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(KS, KV, KA);

    public VoltCalculator() {}

    public double calculate(double goal, double currentSpeed) {
        double volts = feedforward.calculate(goal) + pid.calculate(currentSpeed, goal);
        volts = MathUtil.clamp(volts, MIN_VOLTAGE, MAX_VOLTAGE);
        FlyWheelBase.log("volt", volts);
        return volts;
    }

    public void resetPID() {
        pid.reset();
    }
}
