// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;


public class Shooter extends SubsystemBase {

  SparkMax shooterLeader;
  SparkMax shooterFollower;
  SparkMaxConfig leaderConfig = new SparkMaxConfig();
  SparkMaxConfig followerConfig = new SparkMaxConfig();


  /** Creates a new Shooter. */
  public Shooter() 
  {
    shooterLeader = new SparkMax(Constants.SHOOTER_LEADER, MotorType.kBrushless);
    leaderConfig
                .smartCurrentLimit(40)
                .idleMode(IdleMode.kCoast);
    shooterLeader.configure(leaderConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    shooterFollower = new SparkMax(Constants.SHOOTER_FOLLOWER, MotorType.kBrushless);
    followerConfig
                .smartCurrentLimit(40)
                .idleMode(IdleMode.kCoast);
    shooterFollower.configure(followerConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

public void shootBall(double speed)
{
  shooterLeader.set(speed * Constants.LEADER_SHOOT_SPEED);
  shooterFollower.set(speed * Constants.FOLLOWER_SHOOT_SPEED);
}

public void intake(double speed)
{
  shooterLeader.set(speed * Constants.LEADER_INTAKE_SPEED);
  shooterFollower.set(-speed * Constants.FOLLOWER_INTAKE_SPEED);
}

public void stop()
{
  shooterFollower.set(0);
  shooterLeader.set(0);
}


}
