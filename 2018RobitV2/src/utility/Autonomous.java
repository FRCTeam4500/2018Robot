package utility;

import java.io.File;
import java.io.IOException;

import org.usfirst.frc.team4500.robot.RobotMap;
import org.usfirst.frc.team4500.robot.subsystems.SwerveDrive;

import edu.wpi.first.wpilibj.Preferences;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.SwerveModifier;

/**
 * Pathfinder motion profiling manager
 */
public class Autonomous {
	private int temp = 0;
	
	private SwerveDrive swerve;
	private SwerveModifier.Mode mode = SwerveModifier.Mode.SWERVE_DEFAULT;
	private Trajectory trajectory;
	private SwerveModifier modifier;
	
	//private EncoderFollower flFollower;
	//private EncoderFollower frFollower;
	private EncoderFollower blFollower;
	private EncoderFollower brFollower;
	
	private int[][] deltaP = new int[4][2];
	private boolean displayed = false;
	
	public Autonomous(SwerveDrive swerve) {
		this.swerve = swerve;
		blFollower.configurePIDVA(.5, 0, 0, .28, .99);
		brFollower.configurePIDVA(.5, 0, 0, .28, .99);
		//Preferences prefs = Preferences.getInstance();
		//config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.05, prefs.getDouble("V", 0), prefs.getDouble("A", 0), 60.0);
	}
	
	public void loadTrajectory(File f) {
		//trajectory = Pathfinder.generate(points, config);
		//Preferences prefs = Preferences.getInstance();
		trajectory = Pathfinder.readFromCSV(f);
		
		SwerveModifier modifier = new SwerveModifier(trajectory);
		modifier.modify(RobotMap.wheelBaseWidth, RobotMap.wheelBaseDepth, mode);

//		modifier = new SwerveModifier(trajectory).modify(RobotMap.wheelBaseWidth, RobotMap.wheelBaseDepth, SwerveModifier.Mode.SWERVE_DEFAULT);
//		
//		flFollower = new EncoderFollower(modifier.getFrontLeftTrajectory());
//		flFollower.configureEncoder(swerve.getBR().getDrivePosition(), RobotMap.ticksPerRotation, RobotMap.wheelDiameter);
//		flFollower.configurePIDVA(prefs.getDouble("P", 0), prefs.getDouble("I", 0), prefs.getDouble("I", 0), 1 / prefs.getDouble("V", 0), prefs.getDouble("A", 0));
//		
//		frFollower = new EncoderFollower(modifier.getFrontRightTrajectory()); 
//		frFollower.configureEncoder(swerve.getBR().getDrivePosition(), RobotMap.ticksPerRotation, RobotMap.wheelDiameter);
//		frFollower.configurePIDVA(prefs.getDouble("P", 0), prefs.getDouble("I", 0), prefs.getDouble("I", 0), 1 / prefs.getDouble("V", 0), prefs.getDouble("A", 0));
//		
		blFollower = new EncoderFollower(modifier.getBackLeftTrajectory());
		blFollower.configureEncoder(swerve.getBL().getDrivePosition(), RobotMap.ticksPerRotation, RobotMap.wheelDiameter);
		
		brFollower = new EncoderFollower(modifier.getBackRightTrajectory()); 
		brFollower.configureEncoder(swerve.getBR().getDrivePosition(), RobotMap.ticksPerRotation, RobotMap.wheelDiameter);
		
		deltaP[3][0] = swerve.getBL().getDrivePosition();
		deltaP[4][0] = swerve.getBR().getDrivePosition();
		displayed = false;
	}
	
	public void drive() {
//		double flOutput = flFollower.calculate(swerve.getBR().getDrivePosition());
//		double flHeading = Pathfinder.boundHalfDegrees(Pathfinder.r2d(flFollower.getHeading()));    // Bound to -180..180 degrees
		
//		
//		double frOutput = frFollower.calculate(swerve.getBR().getDrivePosition());
//		double frHeading = Pathfinder.boundHalfDegrees(Pathfinder.r2d(frFollower.getHeading()));    // Bound to -180..180 degrees
		
//		
		double blOutput = blFollower.calculate(swerve.getBL().getDrivePosition()) + 3.56;
		double blHeading = Pathfinder.boundHalfDegrees(Pathfinder.r2d(blFollower.getHeading()));    // Bound to -180..180 degrees
		
		if (!blFollower.isFinished()) {
			swerve.getBL().drive(blOutput/12, blHeading);
			swerve.getFL().drive(blOutput/12, blHeading);	
		}
		
		double brOutput = brFollower.calculate(swerve.getBR().getDrivePosition()) + 3.56;
		double brHeading = Pathfinder.boundHalfDegrees(Pathfinder.r2d(brFollower.getHeading()));    // Bound to -180..180 degrees
		
		if (!brFollower.isFinished()) {
			swerve.getBR().drive(brOutput/12, brHeading);
			swerve.getFR().drive(brOutput/12, brHeading);	
		}
		
		
		if (blFollower.isFinished() && brFollower.isFinished() && !displayed) {
			deltaP[3][1] = swerve.getBL().getDrivePosition();
			deltaP[4][1] = swerve.getBR().getDrivePosition();
			
			System.out.println("BL Dist: " + distTraveled(deltaP[3]));
			System.out.println("BR Dist: " + distTraveled(deltaP[4]));
			
			displayed = true;
		} else if (!displayed){
//			if(temp % 20 == 0) {
//				System.out.format("%-10s%-10s%-10s%-10s\n", 
//						"blOut", "blHead", "brOut", "brHead");
//			}
//			temp++;
//			System.out.format("%-10.2f%-10.2f%-10.2f%-10.2f\n", 
//					blOutput/12, blHeading, brOutput/12, brHeading);
		}
	}
	
	private double distTraveled(int[] deltaP) {
		int dist = Math.abs(deltaP[1] - deltaP[0]);
		double mDist = 0.000049*dist;
		return mDist;
	}

}


//public class Autonomous {
//	// Wheel base (S) = 1.84
//	// Wheel base (F) = 2.26
//	private int temp = 0;
//	
//	private SwerveDrive swerve;
//	//private Trajectory.Config config;
//	private Trajectory trajectory;
//	private SwerveModifier modifier;
//	
//	private EncoderFollower flFollower;
//	private EncoderFollower frFollower;
//	private EncoderFollower blFollower;
//	private EncoderFollower brFollower;
//	
//	public Autonomous(SwerveDrive swerve) {
//		this.swerve = swerve;
//		//Preferences prefs = Preferences.getInstance();
//		//config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.05, prefs.getDouble("V", 0), prefs.getDouble("A", 0), 60.0);
//	}
//	
//	public void loadTrajectory(File f) {
//		//trajectory = Pathfinder.generate(points, config);
//		Preferences prefs = Preferences.getInstance();
//		trajectory = Pathfinder.readFromCSV(f);
//		
//		modifier = new SwerveModifier(trajectory).modify(RobotMap.wheelBaseWidth, RobotMap.wheelBaseDepth, SwerveModifier.Mode.SWERVE_DEFAULT);
//		
//		flFollower = new EncoderFollower(modifier.getFrontLeftTrajectory());
//		flFollower.configureEncoder(swerve.getBR().getDrivePosition(), RobotMap.ticksPerRotation, RobotMap.wheelDiameter);
//		flFollower.configurePIDVA(prefs.getDouble("P", 0), prefs.getDouble("I", 0), prefs.getDouble("I", 0), 1 / prefs.getDouble("V", 0), prefs.getDouble("A", 0));
//		
//		frFollower = new EncoderFollower(modifier.getFrontRightTrajectory()); 
//		frFollower.configureEncoder(swerve.getBR().getDrivePosition(), RobotMap.ticksPerRotation, RobotMap.wheelDiameter);
//		frFollower.configurePIDVA(prefs.getDouble("P", 0), prefs.getDouble("I", 0), prefs.getDouble("I", 0), 1 / prefs.getDouble("V", 0), prefs.getDouble("A", 0));
//		
//		blFollower = new EncoderFollower(modifier.getBackLeftTrajectory());
//		blFollower.configureEncoder(swerve.getBR().getDrivePosition(), RobotMap.ticksPerRotation, RobotMap.wheelDiameter);
//		blFollower.configurePIDVA(prefs.getDouble("P", 0), prefs.getDouble("I", 0), prefs.getDouble("I", 0), 1 / prefs.getDouble("V", 0), prefs.getDouble("A", 0));
//		
//		brFollower = new EncoderFollower(modifier.getBackRightTrajectory()); 
//		brFollower.configureEncoder(swerve.getBR().getDrivePosition(), RobotMap.ticksPerRotation, RobotMap.wheelDiameter);
//		brFollower.configurePIDVA(prefs.getDouble("P", 0), prefs.getDouble("I", 0), prefs.getDouble("I", 0), 1 / prefs.getDouble("V", 0), prefs.getDouble("A", 0));
//	}
//	
//	public void drive() {
//		// TODO: Velocity is 0
//		double flOutput = flFollower.calculate(swerve.getBR().getDrivePosition());
//		double flHeading = Pathfinder.boundHalfDegrees(Pathfinder.r2d(flFollower.getHeading()));    // Bound to -180..180 degrees
//		swerve.getFL().drive(flOutput, flHeading);
//		
//		double frOutput = frFollower.calculate(swerve.getBR().getDrivePosition());
//		double frHeading = Pathfinder.boundHalfDegrees(Pathfinder.r2d(frFollower.getHeading()));    // Bound to -180..180 degrees
//		swerve.getFR().drive(frOutput, frHeading);
//		
//		double blOutput = blFollower.calculate(swerve.getBR().getDrivePosition());
//		double blHeading = Pathfinder.boundHalfDegrees(Pathfinder.r2d(blFollower.getHeading()));    // Bound to -180..180 degrees
//		swerve.getBL().drive(blOutput, blHeading);
//		
//		double brOutput = brFollower.calculate(swerve.getBR().getDrivePosition());
//		double brHeading = Pathfinder.boundHalfDegrees(Pathfinder.r2d(brFollower.getHeading()));    // Bound to -180..180 degrees
//		swerve.getBR().drive(brOutput, brHeading);
//		
//		if(temp % 20 == 0) {
//			System.out.format("%-10s%-10s%-10s%-10s%-10s%-10s%-10s%-10s\n", 
//					"flOut", "flHead", "frOut", "frHead", "blOut", "blHead", "brOut", "brHead");
////			System.out.format("%-7s\t", "flOut");
////			System.out.format("%-7s\t", "flHead");
////			System.out.format("%-7s\t", "frOut");
////			System.out.format("%-7s\t", "frHead");
////			System.out.format("%-7s\t", "blOut");
////			System.out.format("%-7s\t", "blHead");
////			System.out.format("%-7s\t", "brOut");
////			System.out.format("%-7s\t", "brHead");
////			System.out.format("\n");
//		}
//		temp++;
//		System.out.format("%-10.2f%-10.2f%-10.2f%-10.2f%-10.2f%-10.2f%-10.2f%-10.2f\n", 
//				flOutput, flHeading, frOutput, frHeading, blOutput, blHeading, brOutput, brHeading);
////		System.out.format("%-9.2f\t", flOutput);
////		System.out.format("%-9.2f\t", flHeading);
////		System.out.format("%-9.2f\t", frOutput);
////		System.out.format("%-9.2f\t", frHeading);
////		System.out.format("%-9.2f\t", blOutput);
////		System.out.format("%-9.2f\t", blHeading);
////		System.out.format("%-9.2f\t", brOutput);
////		System.out.format("%-9.2f\t", brHeading);
////		System.out.format("\n");
//	}
//
//}
