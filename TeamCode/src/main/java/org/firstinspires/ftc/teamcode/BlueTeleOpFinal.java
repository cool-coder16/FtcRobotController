package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.mechanisms.FinalBench;

@TeleOp
public class BlueTeleOpFinal extends LinearOpMode {
    FinalBench drive = new FinalBench(); // Create the variable of the other file, which has all of the actual hardware and functions
    double forward, strafe, rotate; // Initializing variables for driving, changed every loop
    double manual_velocity; // Initialize flywheel velocity
    boolean shooting = false; // A true/false variable for if we are shooting, for flywheel to turn on/off
    boolean rtPressed = false, ltPressed = false, tracking = false; // A way to make it so that the Right-Trigger is only pressed once
    int tracks = 0;
    double velocity;
    String instructions =
            "Gamepad 1 (start + a):\n" +
                    "* Drive: Joysticks\n" +
                    "* Intake: Dpad: up for in, down for out, side for off\n" +
                    "----------------------------\n" +
                    "Gamepad 2 (start + b):\n" +
                    "* Shooting: Right Trigger to toggle\n" +
                    "* Auto-Aim: Left Trigger to toggle\n" +
                    "* Flywheel Velocity: a + 50, x - 50, Right Trigger = 600, Left Trigger = 0\n" +
                    "* Push Ball: b for up, y for down\n" +
                    "* Move Turret: Dpad right + left\n" +
                    "----------------------------"; // The written instructions on the screen, don't change

    @Override
    public void runOpMode() {
        drive.init(hardwareMap, 0); // Puts the hardware devices from the current configuration into the drive, uses Limelight Pipeline 1: red
        manual_velocity = 0;


        waitForStart(); // This is for the LinearOpMode, starts after you press the Start button on the Driver Station
        drive.intake();

        while (opModeIsActive()) { // Loops really fast until you stop the code
            if (0.5 <= -gamepad1.left_stick_y || -0.5 >= -gamepad1.left_stick_y) { // This fixes dead zones, so that we aren't having the wheels move too slow
                forward = -gamepad1.left_stick_y; // use -y, because the y is inverted
            } else {
                forward = 0; // This means that the joystick is not moved enough
            }

            if (0.5 <= gamepad1.left_stick_x || -0.5 >= gamepad1.left_stick_x) {
                strafe = gamepad1.left_stick_x;
            } else {
                strafe = 0;
            }

            if (0.5 <= gamepad1.right_stick_x || -0.5 >= gamepad1.right_stick_x) {
                rotate = gamepad1.right_stick_x;
            } else {
                rotate = 0;
            }

            drive.drive(forward, strafe, rotate); // Uses the drive function, which sends the values to the drive motors

            if (gamepad2.b) { // Checking if b was just pressed
                drive.pushBallUp();
            } else if (gamepad2.y) {
                drive.pushBallDown();
            } else {
                drive.stopBallUp();
            }

            if (gamepad2.aWasPressed()) {
                manual_velocity += 25; // This ups the flywheel speed if a was pressed
            }

            if (gamepad2.xWasPressed()) {
                manual_velocity -= 25; // This lowers the flywheel speed if x was pressed
            }

            if (gamepad1.dpad_down) {
                drive.outtake(); // Helper function
            }

            if (gamepad1.dpad_up) {
                drive.intake(); // Helper function
            }

            if (gamepad1.dpad_left || gamepad1.dpad_right) { // || means or
                drive.stoptake(); // Helper function
            }

            if (gamepad2.right_bumper) {
                manual_velocity = 0; // Sets the speed back to 600
            }

            if (gamepad2.left_bumper) {
                manual_velocity = 0; // Sets the speed to 0
            }

            if (gamepad2.right_trigger > 0.5 && !rtPressed) {
                shooting = !shooting; // If we are shooting, it turns off, otherwise it turns on
                rtPressed = true; // Know we know that the Right Trigger is pressed, so it will only run once
            } else if (gamepad2.right_trigger < 0.5) {
                rtPressed = false; // Resets the print
            }

            if (gamepad2.left_trigger > 0.5 && !ltPressed) {
                tracking = !tracking;
                tracks = 0;
                ltPressed = true;
            } else if (gamepad2.left_trigger < 0.5) {
                ltPressed = false;
            }

            if (shooting) {
                drive.setFlywheel(velocity); // If we are shooting, it turns on the flywheel
            } else {
                drive.setFlywheel(0); // Otherwise, turns it off
            }

            if (gamepad2.dpad_right) {
                drive.turretClockwise(0.5); // Helper function
            } else if (gamepad2.dpad_left) {
                drive.turretCounterClockwise(0.5); // Helper function
            } else {
                drive.stopTurret(); // Helper function
            }

            LLResult llResult = drive.limelight.getLatestResult();
            if (llResult != null && llResult.isValid()) {
                Pose3D botPose = llResult.getBotpose();
                double tx = llResult.getTx() - 2;
                double ta = llResult.getTa();
                telemetry.addLine("TARGET DETECTED");
                telemetry.addData("Target X", llResult.getTx());
                telemetry.addData("Target Y", llResult.getTy());
                telemetry.addData("Target Area", llResult.getTa());
                telemetry.addData("Velocity Modifier", manual_velocity);
                telemetry.addData("Total Velocity", velocity);
                telemetry.addLine("----------------------------");

                velocity = 25.12398 * Math.pow(ta, 4) - 178.76699 * Math.pow(ta, 3) + 516.00924 * Math.pow(ta, 2)- 820.32747 * ta + 2006.96368 + manual_velocity;
                if (ta >= 0.5) {
                    velocity -= 70;
                } else {
                    velocity -= 40;
                }
                // Turret Clockwise subtracts from tx
                /// AUTO AIM
                double allowedErrorDegrees = 0.5;
                double error = 0, power = 0;
                if (tracking) {
                    if (tx > allowedErrorDegrees) {
                        error = Math.abs(tx - allowedErrorDegrees);
                        power = Math.max(error/25, 0.1);
                        drive.turretClockwise(power);
                    } else if (tx < -allowedErrorDegrees) {
                        error = Math.abs(tx + allowedErrorDegrees);
                        power = Math.max(error/25, 0.1);
                        drive.turretCounterClockwise(power);
                    } else {
                        drive.stopTurret();
                    }
                }


                telemetry.addLine(instructions); // This puts the instructions text from earlier on the screen
                telemetry.addData("Flywheel On", shooting); // Tells you if the flywheel is supposed to be on
                telemetry.addData("Flywheel Velocity", velocity); // Tells you the power of the flywheel
                telemetry.addData("Tracking", tracking);
                telemetry.update(); // Displays all the text
            }
        }
    }
}
