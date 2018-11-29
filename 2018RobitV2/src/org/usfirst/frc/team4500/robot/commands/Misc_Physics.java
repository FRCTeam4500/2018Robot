package org.usfirst.frc.team4500.robot.commands;

import java.io.IOException;

import org.usfirst.frc.team4500.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PrintCommand;

/**
 *
 */
public class Misc_Physics extends Command {
	
    public Misc_Physics() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	try {
			Robot.physics.run();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
  
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
