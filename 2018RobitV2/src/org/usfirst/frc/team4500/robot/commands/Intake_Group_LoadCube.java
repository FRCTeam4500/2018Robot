package org.usfirst.frc.team4500.robot.commands;

import org.usfirst.frc.team4500.robot.Robot;
import org.usfirst.frc.team4500.robot.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import utility.IntakePosition;

/**
 *
 */
public class Intake_Group_LoadCube extends CommandGroup {

    public Intake_Group_LoadCube() {
        requires(Robot.shooter);
        requires(Robot.intake);
        
        addSequential(new Shooter_ShooterRaise());
        addSequential(new Intake_SetPosition(RobotMap.INTAKE_SCALEPOSITION));
        addSequential(new WaitCommand(0.5));
        addSequential(new Intake_ClawOpen());
        addSequential(new WaitCommand(1));
        addSequential(new Intake_ClawClose());
    }
}
