package org.usfirst.frc.team4500.robot.commands;

import org.usfirst.frc.team4500.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Test extends CommandGroup {

    public Test() {
       requires(Robot.intake);
       addSequential(new WaitCommand(5));
    }
    
    @Override
    protected void initialize() {
    	SmartDashboard.putString("Init", "yes");
    }
    
    @Override
    protected void end() {
    	SmartDashboard.putString("End", "yes");
    }
    
    
}
