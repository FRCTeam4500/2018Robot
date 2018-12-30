package utility;

import java.io.IOException;

import org.usfirst.frc.team4500.robot.RobotMap;
import org.usfirst.frc.team4500.robot.subsystems.SwerveDrive;

import edu.wpi.first.wpilibj.Preferences;
import robohound_trajectory.RoboHound_Trajectory;
import robohound_trajectory.following.EncoderFollower;
import robohound_trajectory.generating.Trajectory;

/**
 * Pathfinder motion profiling manager
 */
public class Autonomous2 {
	
	private int temp = 0;
	private SwerveDrive swerve;
	private Trajectory trajectory;
	private EncoderFollower flFollower;
	private EncoderFollower frFollower;
	private EncoderFollower blFollower;
	private EncoderFollower brFollower;
	
	private int start;
	private int end;
	
	public Autonomous2(SwerveDrive swerve) {
		this.swerve = swerve;
	}
	
	public void loadTrajectory(String path) throws IOException {
		Preferences prefs = Preferences.getInstance();
		trajectory = RoboHound_Trajectory.readFromCSV(path);
		
		flFollower = new EncoderFollower(trajectory);
		flFollower.configurePIDVA(prefs.getDouble("P", 0), 0, 0, RobotMap.flKv, RobotMap.flKa);
		flFollower.configureEncoder(swerve.getFL().getDrivePosition(), RobotMap.ticksPerRotation, RobotMap.wheelDiameter);
		
		frFollower = new EncoderFollower(trajectory);
		frFollower.configurePIDVA(prefs.getDouble("P", 0), 0, 0, RobotMap.frKv, RobotMap.frKa);
		frFollower.configureEncoder(swerve.getFR().getDrivePosition(), RobotMap.ticksPerRotation, RobotMap.wheelDiameter);
		
		blFollower = new EncoderFollower(trajectory);
		blFollower.configurePIDVA(prefs.getDouble("P", 0), 0, 0, RobotMap.blKv, RobotMap.blKa);
		blFollower.configureEncoder(swerve.getBL().getDrivePosition(), RobotMap.ticksPerRotation, RobotMap.wheelDiameter);
		
		brFollower = new EncoderFollower(trajectory);
		brFollower.configurePIDVA(prefs.getDouble("P", 0), 0, 0, RobotMap.brKv, RobotMap.brKa);
		brFollower.configureEncoder(swerve.getBR().getDrivePosition(), RobotMap.ticksPerRotation, RobotMap.wheelDiameter);
	}
	
	public void drive() {
		double flOutput = flFollower.calculate(swerve.getFL().getDrivePosition());
		double flHeading = flFollower.getHeading();
		
		double frOutput = frFollower.calculate(swerve.getFR().getDrivePosition());
		double frHeading = frFollower.getHeading();
		
		double blOutput = blFollower.calculate(swerve.getBL().getDrivePosition());
		double blHeading = blFollower.getHeading();
		
		double brOutput = brFollower.calculate(swerve.getBR().getDrivePosition());
		double brHeading = brFollower.getHeading();
		
		swerve.getFL().drive(flOutput, flHeading);
		swerve.getFR().drive(frOutput, frHeading);
		swerve.getBL().drive(blOutput, blHeading);
		swerve.getBR().drive(brOutput, brHeading);
		
//		if (!follower.isFinished()) {
//			if(temp % 20 == 0) {
//				System.out.format("%-10s%-10s\n", 
//						"out", "head");
//			}
//			temp++;
//			System.out.format("%-10.2f%-10.2f\n", 
//					output, heading);
//		}
//		if (follower.isFinished() && temp > 1) {
//			end = swerve.getBR().getDrivePosition();
//			int dist = Math.abs(end-start);
//			double mDist = 0.000049*dist;
//			System.out.println("Distance (ticks): " + dist);
//			System.out.println("Distance (m): " + mDist);
//			System.out.println();
//			temp = 0;
//		}
	}
}