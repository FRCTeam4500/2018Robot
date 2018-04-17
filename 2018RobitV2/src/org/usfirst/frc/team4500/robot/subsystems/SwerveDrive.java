package org.usfirst.frc.team4500.robot.subsystems;

import org.usfirst.frc.team4500.robot.RobotMap;
import org.usfirst.frc.team4500.robot.commands.Swerve_Drive;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * This class groups the information of each individual module together as one.
 */
public class SwerveDrive extends Subsystem {

	private WheelModule fl, fr, bl, br;
	
	private ADXRS450_Gyro gyro;

    public void initDefaultCommand() {
        setDefaultCommand(new Swerve_Drive());
    }
    
    /**
     * Constructor that takes each of the four modules that make up swerve drive
     * @param fl front left module
     * @param fr front right module
     * @param bl back left module
     * @param br back right module
     */
    public SwerveDrive(WheelModule fl, WheelModule fr, WheelModule bl, WheelModule br) {
    	this.fl = fl;
    	this.fr = fr;
    	this.bl = bl;
    	this.br = br;
    	
    	gyro = new ADXRS450_Gyro();
    }
    
    /**
     * Calculates a vector (contains a magnitude (speed) and heading (angle)) for each of the four wheel modules.
     * Then calls the drive method in the four modules to start the desired movement
     * @param x coordinate of the joystick
     * @param y coordinate of the joystick
     * @param z coordinate of the joystick
     */
    public void calculateVectors(double x, double y, double z) {
    	double L = RobotMap.L;
    	double W = RobotMap.W;
    	double r = Math.sqrt((L * L) + (W * W));
    	y *= -1;
    	
    	double gyro = getGyro()*Math.PI/180;
    	double temp = y*Math.cos(gyro) + x*Math.sin(gyro);
    	x = -y*Math.sin(gyro) + x*Math.cos(gyro);
    	y = temp;
    	
    	/*SmartDashboard.putNumber("x", x);
 	    SmartDashboard.putNumber("y", y);
 	    SmartDashboard.putNumber("z", z);*/
 	    
    	double a = x - z * (L / r) + 0;
    	double b = x + z * (L / r);
    	double c = y - z * (W / r) + 0;
    	double d = y + z * (W / r);
    	
    	double brSpeed = Math.sqrt((a * a) + (c * c));
    	double blSpeed = Math.sqrt((a * a) + (d * d));
    	double frSpeed = Math.sqrt((b * b) + (c * c));
    	double flSpeed = Math.sqrt((b * b) + (d * d));
    	
    	double max = brSpeed;
    	if(brSpeed > max) { max = brSpeed; }
    	if(blSpeed > max) { max = blSpeed; }
    	if(frSpeed > max) { max = frSpeed; }
    	if(flSpeed > max) { max = flSpeed; }
    	
    	if(max > 1) {
    		brSpeed /= max;
    		blSpeed /= max;
    		frSpeed /= max;
    		flSpeed /= max;
    	}
    	
    	double brAngle = (Math.atan2(a, c) * 180/Math.PI);
	    double blAngle = (Math.atan2(a, d) * 180/Math.PI);
	    double frAngle = (Math.atan2(b, c) * 180/Math.PI);
	    double flAngle = (Math.atan2(b, d) * 180/Math.PI);
    	
	    br.drive(brSpeed, brAngle);
	    bl.drive(blSpeed, blAngle);
    	fr.drive(frSpeed, frAngle);
    	fl.drive(flSpeed, flAngle);
    }
    
    public void resetGyro() {
    	gyro.reset();
    }
    
    public double getGyro() {
    	return gyro.getAngle();
    }
}

