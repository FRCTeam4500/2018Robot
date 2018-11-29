/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4500.robot;

import org.usfirst.frc.team4500.robot.commands.Intake_ClawClose;
import org.usfirst.frc.team4500.robot.commands.Intake_ClawOpen;
import org.usfirst.frc.team4500.robot.commands.Intake_Group_LoadCube;
import org.usfirst.frc.team4500.robot.commands.Intake_Group_Pressed;
import org.usfirst.frc.team4500.robot.commands.Intake_Group_Released;
import org.usfirst.frc.team4500.robot.commands.Misc_Physics;
import org.usfirst.frc.team4500.robot.commands.Shooter_Group_FireScale;
import org.usfirst.frc.team4500.robot.commands.Shooter_Group_FireSwitch;
import org.usfirst.frc.team4500.robot.commands.Swerve_GyroReset;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
	Joystick driveStick;
	Joystick shootStick;
	
	Button driveResetGyro;
	Button intakeGrabCube, intakeLoadCube, intakeClawOpen;
	Button shooterScale, shooterSwitch;
	Button physicsStart, physicsStop;
	
	public OI() {
		driveStick = new Joystick(0);
		shootStick = new Joystick(1);
		
		/*
		 * intake buttons
		 */
		
		if (!driveStick.getName().equals("")) {
			driveResetGyro = new JoystickButton(driveStick, 7);
			driveResetGyro.whenPressed(new Swerve_GyroReset());
			
			physicsStart = new JoystickButton(driveStick, 8);
			physicsStart.whenPressed(new Misc_Physics());
			
//			intakeGrabCube = new JoystickButton(driveStick, 1);
//			intakeGrabCube.whenPressed(new Intake_Group_Pressed(0.6, 0.6));
//			intakeGrabCube.whenReleased(new Intake_Group_Released());
//			
//			intakeLoadCube = new JoystickButton(driveStick, 3);
//			intakeLoadCube.whenPressed(new Intake_Group_LoadCube());
//			
//			intakeClawOpen = new JoystickButton(driveStick, 2);
//			intakeClawOpen.whenPressed(new Intake_ClawOpen());
//			intakeClawOpen.whenReleased(new Intake_ClawClose());
//			
//			shooterScale = new JoystickButton(driveStick, 6);
//			shooterScale.whenPressed(new Shooter_Group_FireScale());
//
//			shooterSwitch = new JoystickButton(driveStick, 4);
//			shooterSwitch.whenPressed(new Shooter_Group_FireSwitch());
		}
				
		/*
		 * shooter buttons
		 */
		
		if (!shootStick.getName().equals("")) {
			shooterScale = new JoystickButton(shootStick, 4);
			shooterScale.whenPressed(new Shooter_Group_FireScale());
			
			shooterSwitch = new JoystickButton(shootStick, 5);
			shooterSwitch.whenPressed(new Shooter_Group_FireSwitch());	
		}
	}
	
	public double getX() {
		return Math.abs(driveStick.getX()) > RobotMap.DEADZONE_XY ? driveStick.getX() : 0;
	}
	
	public double getY() {
		return Math.abs(driveStick.getY()) > RobotMap.DEADZONE_XY ? driveStick.getY() : 0;
	}
	
	public double getZ() {
		return Math.abs(driveStick.getZ()) > RobotMap.DEADZONE_Z ? driveStick.getZ() : 0;
	}
	
	public double getSlider() {
		return driveStick.getThrottle();
	}
}
