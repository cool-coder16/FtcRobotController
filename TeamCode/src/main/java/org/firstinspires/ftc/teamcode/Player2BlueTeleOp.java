package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.MecanumBenchServo;

@TeleOp
public class Player2BlueTeleOp extends LinearOpMode {
    MecanumBenchServo drive = new MecanumBenchServo();
    double forward, strafe, rotate, goalAngle;
    double power;
    boolean shooting = false;
    String instructions =
            "Gamepad 1:\n" +
            "* Drive: Joysticks\n" +
            "* Intake: Dpad: up for in, down for out, side for off\n\n" +
            "Gamepad 2:\n" +
            "* Shooting: Right Trigger to toggle\n" +
            "* Flywheel Speed: a + 0.05, x - 0.05, Right Trigger = 0.065, Left Trigger = 0\n" +
            "* Push Ball: b for up, y for down.";

    @Override
    public void runOpMode(){
        drive.init(hardwareMap);
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

            if (gamepad2.bWasPressed()){
                drive.pushBallUp();
                sleep(700);
                drive.stopBallUp();
            }

            if (gamepad2.yWasPressed()){
                drive.pushBallDown();
                sleep(700);
                drive.stopBallUp();
            }

            if (gamepad2.aWasPressed()){
                power += 0.05;
            }

            if (gamepad2.xWasPressed()){
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

            if (gamepad2.right_bumper){
                power = 0.65;
            }

            if (gamepad2.left_bumper) {
                power = 0;
            }

            if (gamepad2.right_trigger > 0.5){
                shooting = !shooting;
            }

            if (shooting){
                drive.setFlywheel(power);
            } else {
                drive.setFlywheel(0);
            }

            telemetry.addLine(instructions);
            telemetry.addData("Flywheel On", shooting);
            telemetry.addData("Flywheel Speed", power);
            telemetry.update();
        }
    }
}
