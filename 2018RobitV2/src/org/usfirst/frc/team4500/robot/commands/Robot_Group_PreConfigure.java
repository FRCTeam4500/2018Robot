package org.usfirst.frc.team4500.robot.commands;

import org.usfirst.frc.team4500.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class Robot_Group_PreConfigure extends CommandGroup {

    public Robot_Group_PreConfigure() {
        requires(Robot.shooter);
        
        addSequential(new Swerve_Calibrate());
        addSequential(new WaitCommand(3));
        addSequential(new Shooter_LiftLower());
        addSequential(new Shooter_ShooterRaise());
    }
}
