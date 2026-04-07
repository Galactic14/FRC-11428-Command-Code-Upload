// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }
  // SPARKMAX IDENTIFIERS
  public static final int LEFT_FRONT = 1;
  public static final int RIGHT_FRONT = 3;
  public static final int LEFT_BACK = 2;
  public static final int RIGHT_BACK = 4;
  public static final int SHOOTER_LEADER = 5;
  public static final int SHOOTER_FOLLOWER = 6;


  // Thresholds / Curves
  public static final int MOVE_THRESHOLD = 0;
  public static final double MOVE_CURVE = 3;
  public static final int TURN_THRESHOLD = 0;
  public static final double TURN_CURVE = 3;

  public static final int XBOX_LEFT_Y_AXIS = 1;
  public static final int Xbox_RIGHT_X_AXIS = 4;
  public static final double DRIVE_TIME = 3.0;
  

  // Controllers
  public static final int DRIVER_PORT = 1;
  public static final int OPERATOR_PORT = 0;
  

  // Motor speeds
  public static final double LEADER_SHOOT_SPEED = 1;
  public static final double FOLLOWER_SHOOT_SPEED = 1;
  public static final double LEADER_INTAKE_SPEED = 0.6;
  public static final double FOLLOWER_INTAKE_SPEED = 0.6;

  public static final double AUTONOMOUS_SPEED = 0.5;
  public static final double DRIVE_TRAIN_SPEED = 1;


}
