// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

// REVLib 2026: ResetMode and PersistMode are now top-level com.revrobotics classes
import com.revrobotics.ResetMode;
import com.revrobotics.PersistMode;

import frc.robot.Constants;


public class DriveTrain extends SubsystemBase 
{
  public static SparkMax leftFront;
  public SparkMax leftBack;
  public static SparkMax rightFront;
  public SparkMax rightBack;
  SparkMaxConfig leftFrontConfig = new SparkMaxConfig();
  SparkMaxConfig leftBackConfig = new SparkMaxConfig();
  SparkMaxConfig rightFrontConfig = new SparkMaxConfig();
  SparkMaxConfig rightBackConfig = new SparkMaxConfig();
  
  DifferentialDrive drive;


  /** Creates a new DriveTrain. */
  public DriveTrain() 
  {


    // 1. LEFT FRONT
    leftFront = new SparkMax(Constants.LEFT_FRONT, MotorType.kBrushed);
    leftFrontConfig
                .smartCurrentLimit(40)
                .idleMode(IdleMode.kBrake)
                .inverted(false);
    leftFront.configure(leftFrontConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);


    // 2. LEFT Back
    leftBack = new SparkMax(Constants.LEFT_BACK, MotorType.kBrushed);
    leftBackConfig
                .smartCurrentLimit(40)
                .idleMode(IdleMode.kCoast)
                .follow(leftFront, false);
    leftBack.configure(leftBackConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    // 3. Right FRONT
    rightFront = new SparkMax(Constants.RIGHT_FRONT, MotorType.kBrushed);
    rightFrontConfig
                .smartCurrentLimit(40)
                .idleMode(IdleMode.kBrake)
                .inverted(true);
    rightFront.configure(rightFrontConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    // 4. Right Back
    rightBack = new SparkMax(Constants.RIGHT_BACK, MotorType.kBrushed);
    rightBackConfig
                .smartCurrentLimit(40)
                .idleMode(IdleMode.kCoast)
                .follow(rightFront, false);
    rightBack.configure(rightBackConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    drive = new DifferentialDrive(leftFront, rightFront);
  }

  


  @Override
  public void periodic() 
  {
    // This method will be called once per scheduler run
  }


  public void driveWithJoystucks(XboxController driver, double speed)
  {
    drive.arcadeDrive(moveCurve(driver.getRawAxis(Constants.XBOX_LEFT_Y_AXIS), speed), turnCurve(driver.getRawAxis(Constants.Xbox_RIGHT_X_AXIS), speed));
  }

  public void drive(double speed)
  {
    drive.tankDrive(speed, speed);
  }

  public void stop()
  {
    drive.stopMotor();
  }
  
  public void turn(double leftSpeed, double rightSpeed)
  {
    drive.tankDrive(leftSpeed, rightSpeed);
  }




  //===================================================================================================================================================
  // private methods
  //===================================================================================================================================================


  private double moveCurve(double joystick, double speed) // returns a curve adjusted movement for the joystick, better for fine position control
  {
    if (joystick > 0) 
    {
      return (1 - Constants.MOVE_THRESHOLD) * Math.pow(joystick, Constants.MOVE_CURVE) + Constants.MOVE_THRESHOLD;
    }
    else if (joystick < 0) 
    {
      return (1 - Constants.MOVE_THRESHOLD) * Math.pow(joystick, Constants.MOVE_CURVE) - Constants.MOVE_THRESHOLD;
    }
    return 0;
  }

  private double turnCurve(double joystick, double speed) // returns a curve adjusted turning for the joystick, better for fine position control
  {
    if (joystick > 0) 
    {
      return (1 - Constants.TURN_THRESHOLD) * Math.pow(joystick, Constants.TURN_CURVE) + Constants.TURN_THRESHOLD;
    }
    else if (joystick < 0) 
    {
      return (1 - Constants.TURN_THRESHOLD) * Math.pow(joystick, Constants.TURN_CURVE) - Constants.TURN_THRESHOLD;
    }
    return 0;
  }


}
