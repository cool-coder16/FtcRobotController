package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class MecanumBench {
    private DcMotor front_right_motor, front_left_motor, back_right_motor, back_left_motor, shooting_flywheel;
    private IMU imu;

    public void init(HardwareMap hwMap){
        front_right_motor = hwMap.get(DcMotor.class, "front_right_motor"); // CHANGE THESE TO ACTUAL
        front_left_motor = hwMap.get(DcMotor.class, "front_left_motor"); // CHANGE THESE TO ACTUAL
        back_right_motor = hwMap.get(DcMotor.class, "back_right_motor"); // CHANGE THESE TO ACTUAL
        back_left_motor = hwMap.get(DcMotor.class, "back_left_motor"); // CHANGE THESE TO ACTUAL
        shooting_flywheel = hwMap.get(DcMotor.class, "Flywheel");


        front_left_motor.setDirection(DcMotorSimple.Direction.REVERSE);
        back_left_motor.setDirection(DcMotorSimple.Direction.REVERSE);

        front_left_motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        back_left_motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        front_right_motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        back_right_motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        imu = hwMap.get(IMU.class, "imu"); // NAME THIS

        RevHubOrientationOnRobot RevOrientation = new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                RevHubOrientationOnRobot.UsbFacingDirection.UP);

        imu.initialize(new IMU.Parameters(RevOrientation));
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

    public void shootBall(double power){
        shooting_flywheel.setPower(power);
    }
}
