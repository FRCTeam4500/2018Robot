package utility;

import org.usfirst.frc.team4500.robot.Robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Debugger {
	
	/*=====================
   	 * angle motor methods
   	 *=====================*/
	
	public static void angleErrorDebug() {
		// {fl, br, bl, br}
		int[] values = Robot.swerve.getAngleError();
		SmartDashboard.putNumber("fl angle error", values[0]);
		SmartDashboard.putNumber("fr angle error", values[1]);
		SmartDashboard.putNumber("bl angle error", values[2]);
		SmartDashboard.putNumber("br angle error", values[3]);
	}
	
	public static void anglePositionDebug() {
		// {fl, br, bl, br}
		int[] values = Robot.swerve.getAnglePosition();
		SmartDashboard.putNumber("fl angle position", values[0]);
		SmartDashboard.putNumber("fr angle position", values[1]);
		SmartDashboard.putNumber("bl angle position", values[2]);
		SmartDashboard.putNumber("br angle position", values[3]);
	}
	
	public static void anglePulseWidthDebug() {
		int[] values = Robot.swerve.getPulseWidthPosition();
		SmartDashboard.putNumber("fl pulseWidthPosition", values[0]);
		SmartDashboard.putNumber("fr pulseWidthPosition", values[1]);
		SmartDashboard.putNumber("bl pulseWidthPosition", values[2]);
		SmartDashboard.putNumber("br pulseWidthPosition", values[3]);
	}
	
	public static void angleQuadratureDebug() {
		int[] values = Robot.swerve.getQuadPosition();
		SmartDashboard.putNumber("fl quadraturePosition", values[0]);
		SmartDashboard.putNumber("fr quadraturePosition", values[1]);
		SmartDashboard.putNumber("bl quadraturePosition", values[2]);
		SmartDashboard.putNumber("br quadraturePosition", values[3]);
	}
	
	/*=====================
   	 * shooter methods
   	 *=====================*/
	
	public static void shooterDebug() {
		SmartDashboard.putNumber("shooter velocity", Robot.shooter.getVelocity());
		SmartDashboard.putNumber("shooter error", Robot.shooter.getVelocityError());
	}
	
	/*=====================
   	 * intake methods
   	 *=====================*/
	
	public static void intakeDebug() {
		SmartDashboard.putNumber("intake error", Robot.intake.getError());
		SmartDashboard.putNumber("intake position", Robot.intake.getPosition());
	}
	
	/*=====================
   	 * joystick methods
   	 *=====================*/
	
	public static void xyzDebug() {
		SmartDashboard.putNumber("x", Robot.oi.getX());
		SmartDashboard.putNumber("y", Robot.oi.getY());
		SmartDashboard.putNumber("z", Robot.oi.getZ());
		
		SmartDashboard.putNumber("slider", Robot.oi.getSlider());
	}
}
