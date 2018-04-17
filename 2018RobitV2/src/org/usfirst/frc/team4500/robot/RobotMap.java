/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4500.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	
	/*===================
	 * Ports
	 *===================*/
	
	public final static int FLSPEEDPORT = 0, FLANGLEPORT = 0;
	public final static int FRSPEEDPORT = 0, FRANGLEPORT = 0;
	public final static int BLSPEEDPORT = 0, BLANGLEPORT = 0;
	public final static int BRSPEEDPORT = 0, BRANGLEPORT = 0;
	
	
	/*===================
	 * Configuration
	 *===================*/
	
	// region of the joystick that doesn't register
	public final static double DEADZONE = 0.2;
	
	// Length and width of the drivetrain
	public final static double L = 29.5;
	public final static double W = 29.5;
	public final static int TIMEOUT = 0;
}
