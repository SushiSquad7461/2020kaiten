/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.Camera;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.subsystems.chassis.Chassis;
import frc.robot.subsystems.chassis.Drivetrain;
import frc.robot.subsystems.superstructure.Flywheel;

import java.util.List;

public class RobotContainer {

  // create subsystems
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  public static Chassis s_chassis;
  public static Flywheel s_flywheel;

  public static Camera s_camera;

  // create commands
  private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);

  // create joysticks
  public static final XboxController driveController = new XboxController(Constants.DRIVE_CONTROLLER_PORT);
  public static final XboxController operatorController = new XboxController(Constants.OPERATOR_CONTROLLER_PORT);

  public RobotContainer() {
    s_chassis = new Chassis();
    s_flywheel = new Flywheel();
    s_camera = new Camera();

    configureButtonBindings();
  }

  private void configureButtonBindings() {

    // create inline command to shoot/stop shooting
    new JoystickButton(operatorController, XboxController.Button.kX.value)
            .whenPressed(new InstantCommand(s_flywheel::enable))
            .whenReleased(new InstantCommand(s_flywheel::disable));

    new JoystickButton(driveController, XboxController.Button.kX.value)
            .whenPressed(new InstantCommand(s_camera::set_camera2))
            .whenReleased(new InstantCommand(s_camera::set_camera1));

  }

  public static double getTriggerOutput(XboxController controller) {
    return Math.pow(controller.getTriggerAxis(GenericHID.Hand.kRight) - controller.getTriggerAxis(GenericHID.Hand.kLeft), 3);
  }

  public static double getLeftJoystickAxis(XboxController controller) {
    return controller.getX(GenericHID.Hand.kLeft);
  }

  public static double getRightJoystickAxis(XboxController controller) {
    return controller.getX(GenericHID.Hand.kRight);
  }

  public Command getAutonomousCommand() {
    return new InstantCommand();
  }
}
