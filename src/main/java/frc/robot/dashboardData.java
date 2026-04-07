// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.DriveTrain;

/** Add your docs here. */
public class dashboardData 
{
    private static DifferentialDrive driveBase = new DifferentialDrive(DriveTrain.leftFront, DriveTrain.rightFront);
    static Field2d field = new Field2d();
    private static double matchTime = DriverStation.getMatchTime();
    


        public static void matchTime()
        {
            matchTime = DriverStation.getMatchTime();
            SmartDashboard.putNumber("Match Time", matchTime); 
        }
    
        public static void field()
        {
            
            SmartDashboard.putData(field);
        }
    
        public static void driveBase()
        {
            double left = DriveTrain.leftFront.get();
            double right = DriveTrain.rightFront.get();
            driveBase.tankDrive(left, right);

            SmartDashboard.putData(driveBase);
        }

    public static void perodoic() {
        field();
        matchTime();
        driveBase();
    }

    


}
