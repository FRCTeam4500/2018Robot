package org.usfirst.frc.team4500.robot.subsystems;

import org.usfirst.frc.team4500.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Contains the two motors for each individual module. These are then grouped together in the SwerveDrive class
 */
public class WheelModule extends Subsystem {

	private TalonSRX angleMotor;
	private TalonSRX speedMotor;
	
	private String id;
	
	/**
	 * Constructor that initializes the two motors and configures them with the desired information
	 * @param anglePort port for the angle motor
	 * @param speedPort port for the speed motor
	 * @param id of the module (to identify which one is which through the code)
	 * @param inverted should the angle motor be inverted
	 */
	public WheelModule(int anglePort, int speedPort, String id, boolean inverted) {
		this.id = id;
		
		angleMotor = new TalonSRX(anglePort);
		speedMotor = new TalonSRX(speedPort);
		
		int absolutePosition = angleMotor.getSelectedSensorPosition(0);
		angleMotor.setSelectedSensorPosition(absolutePosition, 0, RobotMap.TIMEOUT);
		angleMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, RobotMap.TIMEOUT);
		
		angleMotor.config_kP(0, 0, RobotMap.TIMEOUT); 
		angleMotor.config_kI(0, 0, RobotMap.TIMEOUT); 
		angleMotor.config_kD(0, 0, RobotMap.TIMEOUT);
		angleMotor.config_kF(0, 0, RobotMap.TIMEOUT);
		angleMotor.config_IntegralZone(0, 0, RobotMap.TIMEOUT);
		angleMotor.configMotionCruiseVelocity(0, RobotMap.TIMEOUT);
		angleMotor.configMotionAcceleration(0, RobotMap.TIMEOUT);
		angleMotor.setInverted(inverted);
	}
	
    public void initDefaultCommand() {
        //setDefaultCommand(new MySpecialCommand());
    }
    
    /**
     * Called through the calculateVector method in the SwerveDrive class. Signals the two motors to start moving
     * @param speed of the module
     * @param angle of the module
     */
    public void drive(double speed, double angle) {
		//angle = adjustAngle(angle);
		//angle *= RobotMap.COUNTPERDEG;
    	
		speedMotor.set(ControlMode.PercentOutput, speed, RobotMap.TIMEOUT);
		angleMotor.set(ControlMode.Position, angle, RobotMap.TIMEOUT);
	}
}

