package org.firstinspires.ftc.teamcode.mechanisms;

import android.util.Size;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@Disabled
@Deprecated
public class MainBench {
    private DcMotor front_right_motor, front_left_motor, back_right_motor, back_left_motor, shooting_motor, intake_motor;
    private CRServo push1, push2;
    private IMU imu;

    public void init(HardwareMap hwMap){
        front_right_motor = hwMap.get(DcMotor.class, "front_right_motor");
        front_left_motor = hwMap.get(DcMotor.class, "front_left_motor");
        back_right_motor = hwMap.get(DcMotor.class, "back_right_motor");
        back_left_motor = hwMap.get(DcMotor.class, "back_left_motor");
        shooting_motor = hwMap.get(DcMotor.class, "shooting_flywheel");
        intake_motor = hwMap.get(DcMotor.class, "intake");


        push1 = hwMap.get(CRServo.class, "push1");
        push2 = hwMap.get(CRServo.class, "push2");

        front_right_motor.setDirection(DcMotorSimple.Direction.REVERSE);
        back_left_motor.setDirection(DcMotorSimple.Direction.REVERSE);
        push1.setDirection(DcMotorSimple.Direction.REVERSE);
        shooting_motor.setDirection(DcMotorSimple.Direction.REVERSE);

        front_left_motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        back_left_motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        front_right_motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        back_right_motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        shooting_motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        intake_motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        imu = hwMap.get(IMU.class, "imu"); // NAME THIS

        RevHubOrientationOnRobot RevOrientation = new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD);

        imu.initialize(new IMU.Parameters(RevOrientation));

        intake_motor.setPower(1.0);
        shooting_motor.setPower(-1.0);
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

        shooting_motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        shooting_motor.setPower(0.55);
    }

    public void driveFieldOriented(double forward, double strafe, double rotate){
        double theta = Math.atan2(forward, strafe);
        double r = Math.hypot(strafe, forward);

        theta = AngleUnit.normalizeRadians(theta -
                imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS));

        double newForward = r * Math.sin(theta);
        double newStrafe = r * Math.cos(theta);

        this.drive(newForward, newStrafe, rotate);
    }

    public void shootBall(){
        push1.setPower(1.0);
        push2.setPower(1.0);
    }

    public void stopShoot(){
        push1.setPower(-1.0);
        push2.setPower(-1.0);
    }

    public void intake(){
        intake_motor.setPower(1);
    }

    public void outtake(){
        intake_motor.setPower(-1);
    }

    public void stoptake(){
        intake_motor.setPower(0);
    }

    public double getDirectionFacing(AngleUnit unit){
        return imu.getRobotYawPitchRollAngles().getYaw(unit);
    }

    public void setFlywheel(double power){
        shooting_motor.setPower(power);
    }

    public void turnToDirection(double wantedAngle){
        // Needs to turn the robot until it is facing that angle. Used for pre-shoot auto orientation
        double currentAngle = this.getDirectionFacing(AngleUnit.DEGREES);
        int runs = 0;
        while (currentAngle > wantedAngle + 5 || currentAngle < wantedAngle - 5){
            if (currentAngle < wantedAngle){
                this.drive(0.0, 0.0, 0.5);
            } else {
                this.drive(0.0, 0, -0.5);
            }
            runs += 1;
            currentAngle = this.getDirectionFacing(AngleUnit.DEGREES);
            if (runs > 30){
                return;
            }
            this.drive(0, 0, 0);
        }
        this.drive(0, 0, 0);
    }

    public double getServoSpeed(){
        return push1.getPower();
    }
}
