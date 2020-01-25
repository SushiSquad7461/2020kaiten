/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.Constants;
import frc.robot.subsystems.chassis.Drivetrain;

import java.util.List;

public class Ramsete extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  public Drivetrain s_drivetrain;

  public Ramsete(Drivetrain subsystem) {
      s_drivetrain = subsystem;
  }

  // sets voltage constraint so you dont over accelerate
  DifferentialDriveVoltageConstraint voltageConstraint = new DifferentialDriveVoltageConstraint(new SimpleMotorFeedforward(Constants.Drivetrain.kS,
          Constants.Drivetrain.kV, Constants.Drivetrain.kA), Drivetrain.driveKinematics,
          Constants.Drivetrain.maxVoltage);

  // configures trajectories
  TrajectoryConfig config =
          new TrajectoryConfig(Constants.Drivetrain.maxSpeedMetersPerSecond,
                  Constants.Drivetrain.maxAccelMetersPerSecondSquared)
                  // Add kinematics to ensure max speed is actually obeyed
                  .setKinematics(Drivetrain.driveKinematics)
                  // Apply the voltage constraint
                  .addConstraint(voltageConstraint);

    // creates a new trajectory
  Trajectory exampleTrajectory = TrajectoryGenerator.generateTrajectory(
          // Start at the origin facing the +X direction
          new Pose2d(0, 0, new Rotation2d(0)),
          // Pass through these two interior waypoints, making an 's' curve path
          List.of(
                  new Translation2d(1, 1),
                  new Translation2d(2, -1)
          ),
          // End 3 meters straight ahead of where we started, facing forward
          new Pose2d(3, 0, new Rotation2d(0)),
          // Pass config
          config
  );

  RamseteCommand ramseteCommand = new RamseteCommand(
          exampleTrajectory,
          s_drivetrain::getPose,
          new RamseteController(Constants.Drivetrain.ramseteB, Constants.Drivetrain.ramseteZeta),
          new SimpleMotorFeedforward(Constants.Drivetrain.kS, Constants.Drivetrain.kV, Constants.Drivetrain.kA),
          Drivetrain.driveKinematics,
          s_drivetrain::getWheelSpeeds,
          new PIDController(Constants.Drivetrain.kPVel, 0, 0),
          new PIDController(Constants.Drivetrain.kPVel, 0, 0),

          // return the volts
          s_drivetrain::tankDriveVolts,
          s_drivetrain
  );

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
