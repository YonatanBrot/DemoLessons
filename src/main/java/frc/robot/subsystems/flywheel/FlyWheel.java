package frc.robot.subsystems.flywheel;

//בקלאסים האלה אתם לא צריכים להשתמש:
import frc.robot.Demo_helpers.FlyWheelBase;

//בכל הקלאסים האלה אתם צריכים להשתמש, לפחות פעם אחת. אם לא השתמשתם בהם, יש לכם טעות:
import com.ctre.phoenix6.hardware.TalonFX;
/* זה המנוע

יש לנו שני דרכים לקבוע כמה המנוע יסתובב:
שיוצרים עם ערך דאבל VoltageOut לתת לו אובייקט 
או
לתת לו מנוע אחר לעקוב אחריו
MotorAllignmentValueוכיוון, אחד מהכיוונים שמוגדרים ב ID שניתן לו Follower בשביל זה ניצור אובייקט 

setControl ניתן אותו לפונקציה (Follower or VoltageOut) לאחר שיש לנו את האובייקט הדרוש */

import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.signals.MotorAlignmentValue;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
//זה אובייקט שאיתו עושים קונפיגורציה למנוע
//current אתם צריכים ליצור אחד כזה, ואז לערוך אותו כך שיכיל את המגבלות 

//ומשם תמשיכו לבד ,getConfigurator() לאחר שסיימתם ליצור את האובייקט - תקחו את אובייקט המנוע שלכם, תקראו לפונקציה

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;

import static frc.robot.subsystems.flywheel.Const.*;
//כאן נמצאים כל מיני ערכים שתצטרכו - תסתכלו על הקובץ הזה
//לכל ערך יש שימוש כלשהו, אם לא השתמשתם בכולם עשיתם טעות

@SuppressWarnings("unused")
public class FlyWheel extends FlyWheelBase{
    





    /* (אחת מהפונקציות שאתם צריכים ליצור) :manual controller הסבר על הפונקציה
    לפעמים דברים לא עובדים, ואנחנו רוצים לשלוט על דברים ידנית עם השלט
    במקרים כאלה, נקבל ערך מהשלט בתחום בין -1 ל1, כאשר 1 אומר להסתובב בכי מהר ו-1 אומר הכי מהר אבל אחורה
    תזכרו שכאשר מנוע מקבל וולטים שליליים הוא מסתובב אחורה */
}