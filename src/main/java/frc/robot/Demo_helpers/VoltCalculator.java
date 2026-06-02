package frc.robot.Demo_helpers;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import frc.robot.subsystems.flywheel.Const.kFF;
import frc.robot.subsystems.flywheel.Const.kPID;

public class VoltCalculator {
    private PIDController pid = new PIDController(kPID.KP, kPID.KI, kPID.KD);
    private SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(kFF.KS, kFF.KV, kFF.KA);

    public VoltCalculator() {}

    public double calculate(double goal, double currentSpeed) {
        double volts = feedforward.calculate(goal);
        return volts + pid.calculate(currentSpeed, goal);
    }

    public void resetPID() {
        pid.reset();
    }
}
