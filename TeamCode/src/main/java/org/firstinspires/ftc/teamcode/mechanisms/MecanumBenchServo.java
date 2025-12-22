/// This is the drive code. It gives functions to control the various parts of the robot, such as
/// * The flywheel
/// * The intake
/// * The pushers
/// * Speed
/// * Mecanum wheels
/// This code gets combined with the main code, which uses the functions with the gamepad.

package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class MecanumBenchServo {
    public DcMotor front_right_motor, front_left_motor, back_right_motor, back_left_motor, intake_motor, flywheel;
//    public Servo push1, push2;
    public CRServo upPusher;
    public double intakeSpeed = 1; //0.075
    public double flywheelSpeed = 1;

    public void init(HardwareMap hwMap){
        front_right_motor = hwMap.get(DcMotor.class, "frontright");
        front_left_motor = hwMap.get(DcMotor.class, "frontleft");
        back_right_motor = hwMap.get(DcMotor.class, "backright");
        back_left_motor = hwMap.get(DcMotor.class, "backleft");
        flywheel = hwMap.get(DcMotor.class, "flywheel");
        intake_motor = hwMap.get(DcMotor.class, "intake");
        upPusher = hwMap.get(CRServo.class, "upPush");

        front_right_motor.setDirection(DcMotorSimple.Direction.REVERSE);
        back_left_motor.setDirection(DcMotorSimple.Direction.REVERSE);
        upPusher.setDirection(DcMotorSimple.Direction.FORWARD);
        front_left_motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        back_left_motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        front_right_motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        back_right_motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        intake_motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        flywheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        intake_motor.setPower(intakeSpeed);
    }

    public void drive(double forward, double strafe, double rotate){
        double frontLeftPower = forward + strafe + rotate;
        double backLeftPower = forward - strafe + rotate;
        double frontRightPower = forward - strafe - rotate;
        double backRightPower = forward + strafe - rotate;

        double maxPower = 1.0;
        double maxSpeed = 1.0;

        maxPower = Math.max(maxPower, Math.abs(frontLeftPower));
        maxPower = Math.max(maxPower, Math.abs(frontRightPower));
        maxPower = Math.max(maxPower, Math.abs(backLeftPower));
        maxPower = Math.max(maxPower, Math.abs(backRightPower));

        front_left_motor.setPower(maxSpeed * (frontLeftPower/maxPower));
        back_left_motor.setPower(maxSpeed * (backLeftPower/maxPower));
        front_right_motor.setPower(maxSpeed * (frontRightPower/maxPower));
        back_right_motor.setPower(maxSpeed * (backRightPower/maxPower));

//        shooting_motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void pushBallUp(){
        upPusher.setPower(1.0);
    }

    public void stopBallUp(){
        upPusher.setPower(0.0);
    }

    public void pushBallDown(){
        upPusher.setPower(-1.0);
    }

    public void shootBall(){
//        push1.setPosition(1.0);
//        push2.setPosition(1.0);
    }

    public void stopShoot(){
//        push1.setPosition(-1.0);
//        push2.setPosition(-1.0);
    }

    public void intake(){
        intake_motor.setPower(intakeSpeed);
    }

    public void outtake(){
        intake_motor.setPower(-intakeSpeed);
    }

    public void stoptake(){
        intake_motor.setPower(0);
    }

    public void setFlywheel(double power){
        flywheel.setPower(power);
    }
}
