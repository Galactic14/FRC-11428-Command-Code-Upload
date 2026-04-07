// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.autoCommands.DriveForwardTimed;
import frc.robot.commands.autoCommands.MoveBackAndShoot;
import frc.robot.commands.basicFunctions.DriveWithJoysticks;
import frc.robot.commands.basicFunctions.IntakeBall;
import frc.robot.commands.basicFunctions.ShootBall;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {

// The robot's subsystems and commands are defined here...

  // Drivetrain declare
  final DriveTrain driveTrain;
  private final DriveWithJoysticks driveWithJoystick;
  private final DriveForwardTimed driveForwardTimed;
  public static XboxController m_driver;
  public static XboxController m_operator;


  private final Shooter shooter;
  private final ShootBall shootBall;


  SendableChooser<Command> m_chooser = new SendableChooser<>();



  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() 
  {
    driveTrain = new DriveTrain();
    driveWithJoystick = new DriveWithJoysticks(driveTrain);
    driveWithJoystick.addRequirements(driveTrain);
    driveTrain.setDefaultCommand(driveWithJoystick);

    driveForwardTimed = new DriveForwardTimed(driveTrain);
    driveForwardTimed.addRequirements(driveTrain);

    m_driver = new XboxController(Constants.DRIVER_PORT);
    m_operator = new XboxController(Constants.OPERATOR_PORT);

    shooter = new Shooter();
    shootBall = new ShootBall(shooter, m_operator);
    shootBall.addRequirements(shooter);

  
      
    m_chooser.setDefaultOption("Move Back and Shoot", new MoveBackAndShoot(driveTrain, shooter));
    m_chooser.addOption("Do Nothing", new InstantCommand());

    SmartDashboard.putData(m_chooser);



    // Configure the trigger bindings
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    Trigger shootTrigger = new Trigger(() -> m_operator.getRightTriggerAxis() > 0);
    shootTrigger.whileTrue(new ShootBall(shooter, m_operator));

    Trigger intakTrigger = new Trigger(() -> m_operator.getLeftTriggerAxis() > 0); 
    intakTrigger.whileTrue(new IntakeBall(shooter, m_operator));
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`

    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return m_chooser.getSelected();
  }
}
