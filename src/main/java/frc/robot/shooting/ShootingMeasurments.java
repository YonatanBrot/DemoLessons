package frc.robot.shooting;

import edu.wpi.first.math.geometry.Transform3d;

public class ShootingMeasurments {
    public static final ShootingState[] ALL_MEASURMENTS_HUB = new ShootingState[] {
        new ShootingState(1.5, 2000, 38),
        new ShootingState(2, 2050, 40),
        new ShootingState(2.5, 2150, 43),
        new ShootingState(3, 2200, 45),
        new ShootingState(3.5, 2300, 47.5),
        new ShootingState(4, 2400, 49),
    };
    public static final ShootingState[] ALL_MEASURMENTS_DELIVRY = new ShootingState[]{

    };

    public static final Transform3d ROBOT_TO_MEASURMENT_TRANSFORM = new Transform3d();
}