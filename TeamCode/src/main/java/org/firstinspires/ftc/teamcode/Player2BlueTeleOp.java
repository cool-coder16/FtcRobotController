package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.mechanisms.MecanumBenchServo;

@TeleOp
public class Player2BlueTeleOp extends LinearOpMode {
    MecanumBenchServo drive = new MecanumBenchServo(); // Create the variable of the other file, which has all of the actual hardware and functions
    double forward, strafe, rotate; // Initializing variables for driving, changed every loop
    double power; // Initialize flywheel power
    boolean shooting = false; // A true/false variable for if we are shooting, for flywheel to turn on/off
    boolean rtPressed = false, ltPressed = false, tracking = false; // A way to make it so that the Right-Trigger is only pressed once
    int tracks = 0;
    String instructions =
            "Gamepad 1 (start + a):\n" +
            "* Drive: Joysticks\n" +
            "* Intake: Dpad: up for in, down for out, side for off\n" +
            "----------------------------\n" +
            "Gamepad 2 (start + b):\n" +
            "* Shooting: Right Trigger to toggle\n" +
            "* Flywheel Speed: a + 0.05, x - 0.05, Right Trigger = 1, Left Trigger = 0\n" +
            "* Push Ball: b for up, y for down\n" +
            "* Move Turret: Dpad right + left\n" +
            "----------------------------"; // The written instructions on the screen, don't change

    /*
    public void autoAim(int duration) {
        LLResult llResult = drive.limelight.getLatestResult();
        if (llResult != null && llResult.isValid()){
            double tx = llResult.getTx();
            int counter = 0;
            telemetry.addLine("Auto Aiming...");
            telemetry.update();
            // Turret Clockwise subtracts from tx
            /// AUTO AIM START
            while (counter < duration){
                if (tx > 4){
                    drive.turretClockwise();
                } else if (tx < -4) {
                    drive.turretCounterClockwise();
                } else {
                    drive.stopTurret();
                    counter = 30;
                }

                counter += 1;
                tx = llResult.getTx();
            }
            drive.stopTurret();
            /// AUTO AIM END
        }
    }
    */

    @Override
    public void runOpMode(){
        drive.init(hardwareMap, 0); // Puts the hardware devices from the current configuration into the drive
        power = 1; //CHANGEABLE: flywheel power set at 1 to start

        waitForStart(); // This is for the LinearOpMode, starts after you press the Start button on the Driver Station

        while (opModeIsActive()){ // Loops really fast until you stop the code
            if (0.5<=-gamepad1.left_stick_y ||-0.5>=-gamepad1.left_stick_y){ // This fixes dead zones, so that we aren't having the wheels move too slow
                forward = -gamepad1.left_stick_y; // use -y, because the y is inverted
            } else {
                forward = 0; // This means that the joystick is not moved enough
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

            drive.drive(forward, strafe, rotate); // Uses the drive function, which sends the values to the drive motors

            if (gamepad2.b){ // Checking if b was just pressed
                drive.pushBallUp();
            } else if (gamepad2.y){
                drive.pushBallDown();
            } else {
                drive.stopBallUp();
            }

            if (gamepad2.aWasPressed()){
                power += 0.05; // This ups the flywheel speed if a was pressed
            }

            if (gamepad2.xWasPressed()){
                power -= 0.05; // This lowers the flywheel speed if x was pressed
            }

            if (gamepad1.dpad_down){
                drive.outtake(); // Helper function
            }

            if (gamepad1.dpad_up){
                drive.intake(); // Helper function
            }

            if (gamepad1.dpad_left || gamepad1.dpad_right){ // || means or
                drive.stoptake(); // Helper function
            }

            if (gamepad2.right_bumper){
                power = 1; // Sets the speed back to 1
            }

            if (gamepad2.left_bumper) {
                power = 0; // Sets the speed to 0
            }

            if (gamepad2.right_trigger > 0.5 && !rtPressed){
                shooting = !shooting; // If we are shooting, it turns off, otherwise it turns on
                rtPressed = true; // Know we know that the Right Trigger is pressed, so it will only run once
            } else if (gamepad2.right_trigger < 0.5) {
                rtPressed = false; // Resets the print
            }

            if (gamepad2.left_trigger > 0.5 && !ltPressed){
                tracking = !tracking;
                tracks = 0;
                ltPressed = true;
            } else if (gamepad2.left_trigger < 0.5){
                ltPressed = false;
            }

            if (shooting){
                drive.setFlywheel(power); // If we are shooting, it turns on the flywheel
            } else {
                drive.setFlywheel(0); // Otherwise, turns it off
            }

            if (gamepad2.dpad_right){
                drive.turretClockwise(0.5); // Helper function
            } else if (gamepad2.dpad_left){
                drive.turretCounterClockwise(0.5); // Helper function
            } else {
                drive.stopTurret(); // Helper function
            }

            LLResult llResult = drive.limelight.getLatestResult();
            if (llResult != null && llResult.isValid()){
                Pose3D botPose = llResult.getBotpose();
                double tx = llResult.getTx();
                telemetry.addLine("TARGET DETECTED");
                telemetry.addData("Target X", llResult.getTx());
                telemetry.addData("Target Y", llResult.getTy());
                telemetry.addData("Target Area", llResult.getTa());
                // Turret Clockwise subtracts from tx
                /// AUTO AIM
                double allowedErrorDegrees = 4;
                double error = 0, power = 0;
                if (tracking && tracks < 100) {
                    tracks = 0;
                    if (tx > allowedErrorDegrees){
                        error = tx - allowedErrorDegrees;
//                        power = Math.max(error/60, 0.1);
                        power = 0.2;
                        drive.turretClockwise(power);
                        tracks += 1;
                    } else if (tx < -allowedErrorDegrees){
                        error = tx + allowedErrorDegrees;
//                        power = Math.max(-error/60, 0.1);
                        power = 0.2;
                        drive.turretCounterClockwise(power);
                        tracks += 1;
                    } else {
                        drive.stopTurret();
                    }
                } else if (tracks >= 30) {
                    tracking = false;
                    tracks = 0;
                }
                /// END AUTO AIM
                if (tracking) {
                    telemetry.addData("Error", error);
                    telemetry.addData("Power", power);
                    telemetry.addData("Tracks", tracks);
                }
                telemetry.addLine("----------------------------");
            }



            telemetry.addLine(instructions); // This puts the instructions text from earlier on the screen
            telemetry.addData("Flywheel On", shooting); // Tells you if the flywheel is supposed to be on
            telemetry.addData("Flywheel Speed", power); // Tells you the power of the flywheel
            telemetry.addData("Tracking", tracking);
            telemetry.update(); // Displays all the text
        }
    }
}
