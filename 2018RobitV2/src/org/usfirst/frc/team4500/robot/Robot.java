/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4500.robot;

import java.io.File;

import org.usfirst.frc.team4500.robot.commands.Robot_Group_PreConfigure;
import org.usfirst.frc.team4500.robot.subsystems.Intake;
import org.usfirst.frc.team4500.robot.subsystems.PneumaticsCompressor;
import org.usfirst.frc.team4500.robot.subsystems.Shooter;
import org.usfirst.frc.team4500.robot.subsystems.SwerveDrive;
import org.usfirst.frc.team4500.robot.subsystems.WheelModule;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import utility.Autonomous;
import utility.Debugger;
import utility.Physics;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
	
	public static WheelModule fl, fr, bl, br;
	public static SwerveDrive swerve;
	public static Shooter shooter;
	public static Intake intake;
	public static PneumaticsCompressor compress;
	public static OI oi;
	
	public static Autonomous auto;
	public static Physics physics;
	public static Preferences prefs;
	public static SendableChooser<String> autoChooser;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		fl = new WheelModule(RobotMap.FLANGLEPORT, RobotMap.FLSPEEDPORT, "fl", false, 3419); 
		fr = new WheelModule(RobotMap.FRANGLEPORT, RobotMap.FRSPEEDPORT, "fr", false, 845);
		bl = new WheelModule(RobotMap.BLANGLEPORT, RobotMap.BLSPEEDPORT, "bl", false, 87); 
		br = new WheelModule(RobotMap.BRANGLEPORT, RobotMap.BRSPEEDPORT, "br", false, 885);
		
		swerve = new SwerveDrive(fl, fr, bl, br);
		
		shooter = new Shooter();
		intake = new Intake();
		
		compress = new PneumaticsCompressor();
		
		auto = new Autonomous(swerve);
		autoChooser = new SendableChooser<String>();
		autoChooser.addDefault("Forward", "forward_2m.csv");
		autoChooser.addObject("Bend", "bend.csv");
		prefs = Preferences.getInstance();
		prefs.putDouble("P", 1);
		prefs.putDouble("I", 0);
		prefs.putDouble("D", 0);
		prefs.putDouble("V", RobotMap.maxVelocity);
		prefs.putDouble("A", RobotMap.maxAcceleration);
		physics = new Physics(swerve);
		SmartDashboard.putData("Auto Profile", autoChooser);
		
		oi = new OI();
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		//Debugger.anglePulseWidthDebug();
		//Debugger.angleQuadratureDebug();
		SmartDashboard.putNumber("Enc", swerve.getBR().getDrivePosition());
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		//m_autonomousCommand = m_chooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		//if (m_autonomousCommand != null) {
		//	m_autonomousCommand.start();
		//}
		System.out.println("autoInit");
		auto.loadTrajectory(new File("/home/lvuser/MotionProfile/" + autoChooser.getSelected()));
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		Debugger.angleErrorDebug();
		auto.drive();
	}

	@Override
	public void teleopInit() {
		Command preconfigure = new Robot_Group_PreConfigure();
		//preconfigure.start();
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		//if (m_autonomousCommand != null) {
		//	m_autonomousCommand.cancel();
		//}
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		SmartDashboard.putNumber("gyro", swerve.getGyro());
		//Debugger.xyzDebug();
		//Debugger.intakeDebug();
		//Debugger.shooterDebug();
		//Debugger.angleErrorDebug();
		//Debugger.anglePositionDebug();
		//Debugger.anglePulseWidthDebug();
		//Debugger.angleQuadratureDebug();
		SmartDashboard.putNumber("Enc", swerve.getBR().getDrivePosition());
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
