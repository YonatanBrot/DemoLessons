package frc.robot.subsystems.flywheel;

//ignore these:
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Demo_helpers.FlyWheelInterface;

import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.signals.MotorAlignmentValue;

//look at these:
import com.ctre.phoenix6.hardware.TalonFX;
//זה המנוע

//יש לנו שני דרכים לקבוע כמה המנוע יסתובב:
//שיוצרים עם ערך דאבל VoltageOut לתת לו אובייקט 
// או
//לתת לו מנוע אחר לעקוב אחריו
//MotorAllignmentValueוכיוון, אחד מאלה שמוגדרים ב ID שלו ניתן follower בשביל זה ניצור אובייקט 
//setControl ניתן אותו לפונקציה (Follower or VoltageOut) לאחר שיש לנו את האובייקט הדרוש 

import com.ctre.phoenix6.configs.TalonFXConfiguration;
//זה אובייקט שאיתו עושים קונפיגורציה למנוע
// current limits -אתם תצטרכו רק לגשת לתכונה אחת שלו
//משם תמשיכו לבד - getConfiguratorכדי להחיל קונפיגורציה על מנוע קראו לg

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;

import static frc.robot.subsystems.flywheel.Const.*;
//כאן נמצאים כל מיני ערכים שתצטרכו - תסתכלו על הקובץ הזה


@SuppressWarnings("unused")
public class FlyWheel extends SubsystemBase implements FlyWheelInterface{
    
}