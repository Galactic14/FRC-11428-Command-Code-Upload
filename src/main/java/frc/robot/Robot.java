// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim.KitbotGearing;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim.KitbotMotor;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim.KitbotWheelSize;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.DriveTrain;

/**
 * The methods in this class are called automatically corresponding to each mode, as described in
 * the TimedRobot documentation. If you change the name of this class or the package after creating
 * this project, you must also update the Main.java file in the project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;
  private DifferentialDrivetrainSim m_driveSim;
  // Deadzone threshold — eliminates idle joystick drift
  private static final double DEADZONE = 0.15;


  private final RobotContainer m_robotContainer;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  public Robot() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();


    dashboardData.perodoic();

  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      CommandScheduler.getInstance().schedule(m_autonomousCommand);
    }
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {}

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {
    m_driveSim = DifferentialDrivetrainSim.createKitbotSim(
      KitbotMotor.kDoubleNEOPerSide, // 2 NEOs per side matches your 4-motor drive
      KitbotGearing.k10p71, // Adjust to match your actual gearing
      KitbotWheelSize.kSixInch, // Adjust to match your actual wheel size
      null);
  }

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() 
  {
    if (isDisabled()) {
            // Zero out inputs when disabled to prevent phantom drift
            m_driveSim.setInputs(0.0, 0.0);
        } else {
            // Right side is inverted in hardware config (inverted(true) above).
            // DifferentialDrivetrainSim expects raw undirected voltage on both sides,
            // so we negate the right side here to cancel out the hardware inversion.
            // Without this negation, left=-0.158 and right=+0.158 causes endless spinning.
            double leftInput = applyDeadzone(DriveTrain.leftFront.get()) * 12.0;
            double rightInput = applyDeadzone(DriveTrain.rightFront.get()) * 12.0; // negate to un-invert
            m_driveSim.setInputs(leftInput, rightInput);
        }

        // Step the physics model forward one robot loop (20ms)
        m_driveSim.update(0.02);

        // getPose() is the correct WPILib 2026 call — replaces old getXPositionMeters()
        // etc.
        Pose2d simPose = m_driveSim.getPose();

        // Push pose to Field2d — AdvantageScope reads this from NetworkTables
        dashboardData.field.setRobotPose(simPose);

        // Telemetry — visible in AdvantageScope left panel under Sim/
        SmartDashboard.putNumber("Sim/X", simPose.getX());
        SmartDashboard.putNumber("Sim/Y", simPose.getY());
        SmartDashboard.putNumber("Sim/HeadingDeg", simPose.getRotation().getDegrees());
        SmartDashboard.putNumber("Sim/LeftVelocity", m_driveSim.getLeftVelocityMetersPerSecond());
        SmartDashboard.putNumber("Sim/RightVelocity", m_driveSim.getRightVelocityMetersPerSecond());
  }
  private double applyDeadzone(double value) {
        if (Math.abs(value) < DEADZONE) {
            return 0.0;
        }
        return value;
    }
}
