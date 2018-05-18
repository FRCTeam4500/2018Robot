package org.usfirst.frc.team4500.robot.commands;

import org.usfirst.frc.team4500.robot.Robot;
import org.usfirst.frc.team4500.robot.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class Shooter_Group_FireScale extends CommandGroup {

    public Shooter_Group_FireScale() {
       requires(Robot.shooter);
       addSequential(new Shooter_ShooterRaise());
       addSequential(new Shooter_ShootConditional(
    		   new Shooter_Shoot(RobotMap.HIGHGOALSPEED, 3), 
    		   new Shooter_Shoot(RobotMap.LOWGOALSPEED, 3)));
       //addSequential(new Shooter_LiftRaise());
       addSequential(new Intake_ClawSetMotor(-0.6, -0.6));
       addSequential(new WaitCommand(1));
       addSequential(new Intake_ClawSetMotor(0, 0));
       //addSequential(new Shooter_LiftLower());
       addSequential(new Shooter_Shoot(0, 1));
       //addSequential(new Shooter_ShooterLower());
       
    }
}
