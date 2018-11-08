package utility;

import java.io.File;
import java.io.IOException;

import org.usfirst.frc.team4500.robot.RobotMap;
import org.usfirst.frc.team4500.robot.subsystems.SwerveDrive;

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
	private Trajectory.Config config;
	private Trajectory trajectory;
	private SwerveModifier modifier;
	
	private EncoderFollower flFollower;
	private EncoderFollower frFollower;
	private EncoderFollower blFollower;
	private EncoderFollower brFollower;
	
	public Autonomous(SwerveDrive swerve) {
		this.swerve = swerve;
		
		config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.05, 1.7, 2.0, 60.0);
	}
	
	public void loadTrajectory(File f) {
		//trajectory = Pathfinder.generate(points, config);
		Trajectory trajectory = Pathfinder.readFromCSV(f);
		
		modifier = new SwerveModifier(trajectory).modify(0.5, 0.5, SwerveModifier.Mode.SWERVE_DEFAULT);
		
		flFollower = new EncoderFollower(modifier.getFrontLeftTrajectory());
		flFollower.configureEncoder(swerve.getFL().getDrivePosition(), RobotMap.ticksPerRotation, RobotMap.wheelDiameter);
		flFollower.configurePIDVA(RobotMap.Pa, RobotMap.Ia, RobotMap.Da, 1 / RobotMap.maxVelocity, RobotMap.maxAcceleration);
		
		frFollower = new EncoderFollower(modifier.getFrontRightTrajectory()); 
		frFollower.configureEncoder(swerve.getFL().getDrivePosition(), RobotMap.ticksPerRotation, RobotMap.wheelDiameter);
		frFollower.configurePIDVA(RobotMap.Pa, RobotMap.Ia, RobotMap.Da, 1 / RobotMap.maxVelocity, RobotMap.maxAcceleration);
		
		blFollower = new EncoderFollower(modifier.getBackLeftTrajectory());
		blFollower.configureEncoder(swerve.getFL().getDrivePosition(), RobotMap.ticksPerRotation, RobotMap.wheelDiameter);
		blFollower.configurePIDVA(RobotMap.Pa, RobotMap.Ia, RobotMap.Da, 1 / RobotMap.maxVelocity, RobotMap.maxAcceleration);
		
		brFollower = new EncoderFollower(modifier.getBackRightTrajectory()); 
		brFollower.configureEncoder(swerve.getFL().getDrivePosition(), RobotMap.ticksPerRotation, RobotMap.wheelDiameter);
		brFollower.configurePIDVA(RobotMap.Pa, RobotMap.Ia, RobotMap.Da, 1 / RobotMap.maxVelocity, RobotMap.maxAcceleration);
	}
	
	public void drive() {
		// TODO: Velocity is 0
		double flOutput = flFollower.calculate(swerve.getFL().getDrivePosition());
		double flHeading = Pathfinder.boundHalfDegrees(Pathfinder.r2d(flFollower.getHeading()));    // Bound to -180..180 degrees
		swerve.getFL().drive(flOutput, flHeading);
		
		double frOutput = frFollower.calculate(swerve.getFL().getDrivePosition());
		double frHeading = Pathfinder.boundHalfDegrees(Pathfinder.r2d(frFollower.getHeading()));    // Bound to -180..180 degrees
		swerve.getFR().drive(frOutput, frHeading);
		
		double blOutput = blFollower.calculate(swerve.getFL().getDrivePosition());
		double blHeading = Pathfinder.boundHalfDegrees(Pathfinder.r2d(blFollower.getHeading()));    // Bound to -180..180 degrees
		swerve.getBL().drive(blOutput, blHeading);
		
		double brOutput = brFollower.calculate(swerve.getFL().getDrivePosition());
		double brHeading = Pathfinder.boundHalfDegrees(Pathfinder.r2d(brFollower.getHeading()));    // Bound to -180..180 degrees
		swerve.getBR().drive(brOutput, brHeading);
		
		if(temp % 20 == 0) {
			System.out.format("%-9s\t", "flOut");
			System.out.format("%-9s\t", "flHead");
			System.out.format("%-9s\t", "frOut");
			System.out.format("%-9s\t", "frHead");
			System.out.format("%-9s\t", "blOut");
			System.out.format("%-9s\t", "blHead");
			System.out.format("%-9s\t", "brOut");
			System.out.format("%-9s\t", "brHead");
			System.out.format("\n");
		}
		temp++;
		System.out.format("%-9.2f\t", flOutput);
		System.out.format("%-9.2f\t", flHeading);
		System.out.format("%-9.2f\t", frOutput);
		System.out.format("%-9.2f\t", frHeading);
		System.out.format("%-9.2f\t", blOutput);
		System.out.format("%-9.2f\t", blHeading);
		System.out.format("%-9.2f\t", brOutput);
		System.out.format("%-9.2f\t", brHeading);
		System.out.format("\n");
	}

}
