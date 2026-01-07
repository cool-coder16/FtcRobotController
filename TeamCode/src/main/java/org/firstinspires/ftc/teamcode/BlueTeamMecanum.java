package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.configuration.annotations.DigitalIoDeviceType;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.mechanisms.MecanumBenchServo;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

@Deprecated
@Disabled
@TeleOp
public class BlueTeamMecanum extends OpMode {
    MecanumBenchServo drive = new MecanumBenchServo();
    AprilTagWebcam aprilTag = new AprilTagWebcam();
    double forward, strafe, rotate, goalAngle;

    double power;
    boolean servosOn = false;
    AprilTagDetection goal;

    @Override
    public void init() {
        drive.init(hardwareMap, 1);
        aprilTag.init(hardwareMap, telemetry);
        power = 0.65;
        goalAngle = 45;
        goal = aprilTag.getTagById(20);
    }

    @Override
    public void loop() {
        if (0.5<=gamepad1.left_stick_y ||-0.5>=gamepad1.left_stick_y){
            forward = -gamepad1.left_stick_y;
        } else {
            forward = 0;
        }
        if (0.5<=gamepad1.left_stick_x ||-0.5>=gamepad1.left_stick_x){
            strafe = gamepad1.left_stick_x;
        } else {
            strafe = 0;
        }
        if (0.5<=gamepad1.right_stick_x ||-0.5>=gamepad1.right_stick_x){
            rotate = gamepad1.right_stick_x;
        } else {
            rotate = 0;
        }



        drive.drive(forward, strafe, rotate);
        drive.setFlywheel(power);

        aprilTag.updateWebcam();

        if (gamepad1.aWasPressed()){
            power += 0.05;
        }

        if (gamepad1.xWasPressed()){
            power -= 0.05;
        }

        if (gamepad1.dpad_down){
            drive.outtake();
        }

        if (gamepad1.dpad_up){
            drive.intake();
        }

        if (gamepad1.dpad_left || gamepad1.dpad_right){
            drive.stoptake();
        }

        if (gamepad1.right_bumper){
            power = 0.65;
        }

        if (gamepad1.left_bumper){
            power = 0;
        }

//        if (gamepad1.left_trigger > 0.5){
//            drive.turnToDirection(goalAngle);
//        }

        telemetry.addData("Rotate", rotate);
        telemetry.addData("Flywheel Speed", power);
        telemetry.addData("April Tag", goal);
        aprilTag.displayWebcamTelemetry(goal);
    }
}
