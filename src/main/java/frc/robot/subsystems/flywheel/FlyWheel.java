package frc.robot.subsystems.flywheel;

//בקלאס הזה אתם לא צריכים להשתמש:
import frc.robot.Demo_helpers.FlyWheelBase;

//בכל הקלאסים האלה אתם צריכים להשתמש, לפחות פעם אחת. אם לא השתמשתם בהם, יש לכם טעות:
import com.ctre.phoenix6.hardware.TalonFX;
/* זה המנוע
:יש לנו 2 דרכים לשלוט ברובוט
setVoltage הראשונה היא לתת לו וולטים ישירות, בעזרת
השנייה היא לגרום לו להעתיק את התנועות של מנוע אחר
 של מנוע לעקוב אחריו וכיוון (זהה או הפוך) ID שמקבל ,Follower כדי לעשות את זה צריך ליצור אובייקט
MotorAlignmentValue את הכיוון מביאים מהקבועים בקלאס */
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.signals.MotorAlignmentValue;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
//זה אובייקט שאיתו עושים קונפיגורציה למנוע
//current אתם צריכים ליצור אחד כזה, ואז לערוך אותו כך שיכיל את המגבלות 
//כל המגבלות מופיעות בקובץ הקבועים, ולחלקם לא צריך רק להכיל אלא 
//trueל enabled גם לשנות את המשתנה שקובע האם הם 

//ומשם תמשיכו לבד ,getConfigurator() לאחר שסיימתם ליצור את האובייקט - תקחו את אובייקט המנוע שלכם, תקראו לפונקציה

import team2679.atlantiskit.logfields.LogFieldsTable;
/* זה האובייקט של הלוגים
(flywheel) כאשר יוצרים אחד, צריך לתת לו שם - כאן זה יהיה השם של המערכת 
יש לו 2 פונקציות שתשתמשו בהם:

שמוסיפה ערך כלשהו פעם אחת recordOutpu(), הראשונה נקראת  
בלי משתנים - string לערך. חשוב שהם יהיה רק (string) היא מקבלת את הערך ושם

להוסיף את הערך כל הזמן logsהפונקציה הזאת תגיד ל .(יש את סוג האובייקט Type כאשר במקום) addType הפונקציה השנייה נקראת
לכן היא לא מקבל ערך אחד, אלא פונקציה מסוימת שמחזירה ערך מהסוג הזה
כמו שהסברתי supplier כדי להעביר פונקציה - ניצור */

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