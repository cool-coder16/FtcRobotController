package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.MecanumBenchServo;

@Disabled
@TeleOp
public class Blue2Player extends LinearOpMode{
        MecanumBenchServo drive = new MecanumBenchServo();
        double forward, strafe, rotate, goalAngle;
        double power;

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
                drive.setFlywheel(power);

                if (gamepad2.bWasPressed()){
                    drive.shootBall();
                    sleep(700);
                    drive.stopShoot();
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

                if (gamepad2.left_bumper){
                    power = 0;
                }

                telemetry.addData("Rotate", rotate);
                telemetry.addData("Flywheel Speed", power);
                telemetry.addLine("Gamepad 1 for driving and intake/outtake");
                telemetry.addLine("Gamepad 2 for shooting power");
                telemetry.update();
            }
        }

    @Disabled
    @TeleOp
    public static class Player2TeleOp extends LinearOpMode {
        MecanumBenchServo drive = new MecanumBenchServo();
        double forward, strafe, rotate, goalAngle;
        double power;
        boolean shooting = false;
        String instructions = "Gamepad 1:\n" +
                              "* Driving: Joysticks\n" +
                              "* Intake: Dpad, Up for in, Down for out, Side for stop\n\n" +
                              "Gamepad 2:\n" +
                              "* Flywheel: Right Trigger\n" +
                              "* Ball Push: b for up, y for down\n" +
                              "* Flywheel speed: a + 0.05, x - 0.05\n" +
                              "* Reset speed: Right Bumper 0.65, Left Bumper 0";

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
                telemetry.addData("Flywheel Speed", power);
                telemetry.update();
            }
        }
    }
}

