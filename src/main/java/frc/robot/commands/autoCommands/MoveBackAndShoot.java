// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autoCommands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.RepeatCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Shooter;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class MoveBackAndShoot extends Command {
DriveTrain driveTrain;
Shooter shooter;
Timer timer;
final double MOVE_SPEED = -0.5;
final double SHOOT_SPEED = 1.0;

  /** Creates a new MoveBackAndShoot. */
  public MoveBackAndShoot(DriveTrain dt, Shooter st) {
    // Use addRequirements() here to declare subsystem dependencies.
    driveTrain = dt;
    shooter = st;
    addRequirements(driveTrain);
    addRequirements(shooter);
    timer = new Timer();
  
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() 
  {
    timer.reset();
    timer.start();
    new ParallelDeadlineGroup(
      new WaitCommand(1),
      new RunCommand(() -> driveTrain.drive(MOVE_SPEED), driveTrain)
    ).finallyDo((interrupted) -> driveTrain.stop());

    
    new RunCommand(() -> shooter.shootBall(SHOOT_SPEED), shooter);
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) 
  {
    driveTrain.stop();
    shooter.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
