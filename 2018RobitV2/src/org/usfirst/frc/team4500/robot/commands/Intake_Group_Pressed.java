package org.usfirst.frc.team4500.robot.commands;

import org.usfirst.frc.team4500.robot.Robot;
import org.usfirst.frc.team4500.robot.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;
import utility.IntakePosition;

/**
 *
 */
public class Intake_Group_Pressed extends CommandGroup {

    public Intake_Group_Pressed(double lSpeed, double rSpeed) {
    	requires(Robot.intake);
        
        addSequential(new Intake_SetPosition(RobotMap.INTAKE_DOWNPOSITION));
        addSequential(new Intake_ClawSetMotor(lSpeed, rSpeed));
        addSequential(new Intake_ClawClose());
    	
    }
}
