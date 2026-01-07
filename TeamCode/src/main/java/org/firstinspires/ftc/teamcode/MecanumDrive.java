package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.mechanisms.MecanumBenchServo;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

@Disabled
@TeleOp
public class MecanumDrive extends OpMode {
    MecanumBenchServo drive = new MecanumBenchServo();
    AprilTagWebcam aprilTag = new AprilTagWebcam();
    double forward, strafe, rotate, goalAngle;

    double power;
    int team = -1; // Blue is 0, Red is 1
    boolean servosOn = false;
    AprilTagDetection goal;

    @Override
    public void init() {
        drive.init(hardwareMap, 1);
        aprilTag.init(hardwareMap, telemetry);
        power = 0.65;
    }

    @Override
    public void init_loop() {
        if (gamepad1.right_bumper){
            team = 0;
        } else if (gamepad1.left_bumper){
            team = 1;
        }

        telemetry.addData("Team", "Right Bumper for Blue, Left Bumper for Red");
        telemetry.addData("0", "blue");
        telemetry.addData("1", "red");
        telemetry.addData("-1", "Choose");
        telemetry.addData("Team", team);
    }

    @Override
    public void loop() {
        if (team == -1){
            throw new RuntimeException("Choose Team");
        } else if (team == 0){
            goalAngle = 45;
            goal = aprilTag.getTagById(20);
        } else if (team == 1){
            goalAngle = -45;
            goal = aprilTag.getTagById(24);
        }

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
