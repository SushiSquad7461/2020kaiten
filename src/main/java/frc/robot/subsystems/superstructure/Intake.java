/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems.superstructure;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import frc.robot.Constants;


public class   Intake extends SubsystemBase {
    //define variables
    private final WPI_TalonSRX intake;
    private final DoubleSolenoid intakeArm;


    //constructor
    public Intake() {
        //instantiate motors
        intake = new  WPI_TalonSRX(Constants.INTAKE_MAIN);
        //instantiate solenoid
        intakeArm = new DoubleSolenoid(Constants.INTAKE_ARM_FRONT,Constants.INTAKE_ARM_BACK);
        //safety e
        intake.setSafetyEnabled(false);
    }
    public void startEat() {
        intakeArm.set(DoubleSolenoid.Value.kForward);
        intake.set(Constants.INTAKE_SPEED);
    }
    public void stopEat() {
        intakeArm.set(DoubleSolenoid.Value.kReverse);
        intake.set(0);
    }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}