package frc.robot.subsystems.flywheel;

public class Const {
    public static class IDs {
        public static final int FLYWHEEL_MOTOR1_ID = 20;
        public static final int FLYWHEEL_MOTOR2_ID = 21;
    }

    public static final double SPEED_TOLERENCE = 120;

    public static class CurrentLimits {
        public static final double STATOR_CURRENT_LIMIT = 90;
        public static final double SUPPLY_CURRENT_LIMIT = 60;
        public static final double SUPPLY_CURRENT_LOWER_LIMIT = 40;
        public static final double SUPPLY_CURRENT_LOWER_TIME = 2;
    }


}
