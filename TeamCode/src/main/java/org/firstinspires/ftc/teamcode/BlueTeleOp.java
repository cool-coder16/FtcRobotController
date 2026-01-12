package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.MecanumBenchServo;

@Disabled
@TeleOp
public class BlueTeleOp extends LinearOpMode {
    MecanumBenchServo drive = new MecanumBenchServo();
    double forward, strafe, rotate, goalAngle;
    double power;
    boolean shooting = false;

    @Override
    public void runOpMode(){
        drive.init(hardwareMap, 1);
        power = 0.65;
        goalAngle = 45;

        waitForStart();

        while (opModeIsActive()){
            if (0.5<=-gamepad1.left_stick_y ||-0.5>=-gamepad1.left_stick_y){
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

            if (gamepad1.bWasPressed()){
                drive.pushBallUp();
                sleep(700);
                drive.stopBallUp();
            }

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

            if (gamepad1.left_bumper) {
                power = 0;
            }

//            telemetry.addData("Flywheel Speed", power);
            telemetry.update();
        }
    }
}
