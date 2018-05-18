package org.usfirst.frc.team4500.robot.commands;

import org.usfirst.frc.team4500.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.ConditionalCommand;

/**
 *
 */
public class Shooter_ShootConditional extends ConditionalCommand {

    public Shooter_ShootConditional(Command onTrue, Command onFalse) {
    	super(onTrue, onFalse);
    	requires(Robot.shooter);
    }
    
    @Override
	protected boolean condition() {
		return Robot.oi.getSlider() < 0.5;
	}
}
