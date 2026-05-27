package frc.robot.subsystems.flywheel;

public class Const {
    public static class kPID {
        public static final double KP = 0.028107;
        public static final double KD = 0.0;
        public static final double KI = 0.0;
    }

    public static class kFF {
        public static final double KS = 0.2654;
        public static final double KV = 0.0018705;
        public static final double KA = 0.0001328;    
    }

    public static class IDs {
        public static final int FLYWHEEL_MOTOR1_ID = 20;
        public static final int FLYWHEEL_MOTOR2_ID = 21;
    }

    public static final double SPEED_TOLERENCE = 120;

    public static final double MAX_VOLTAGE = 12;
    public static final double MIN = 12;

    public static class CurrentLimits {
        public static final double STATOR_CURRENT_LIMIT = 90;
        public static final double SUPPLY_CURRENT_LIMIT = 60;
        public static final double SUPPLY_CURRENT_LOWER_LIMIT = 40;
        public static final double SUPPLY_CURRENT_LOWER_TIME = 2;
    }


}
