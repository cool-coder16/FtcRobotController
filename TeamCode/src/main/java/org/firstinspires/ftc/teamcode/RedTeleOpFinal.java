package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.mechanisms.FinalBench;

@TeleOp (group = "TeleOp")
public class RedTeleOpFinal extends LinearOpMode {
    FinalBench drive = new FinalBench(); // Create the variable of the other file, which has all of the actual hardware and functions
    double forward, strafe, rotate; // Initializing variables for driving, changed every loop
    double manual_velocity; // Initialize flywheel velocity
    boolean shooting = false; // A true/false variable for if we are shooting, for flywheel to turn on/off
    boolean rtPressed = false, ltPressed = false, tracking = false; // A way to make it so that the Right-Trigger is only pressed once
    int tracks = 0;
    double velocity;
    boolean transitionOn = false;
    double onTransitionTime = Double.POSITIVE_INFINITY;
    boolean yPressed = false;
    boolean transitionButtonPressed = false;

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

    public double calculateTurretPower(double error){
        double minPowerErrorThreshold = 20;  // In degrees
        double maxPowerErrorThreshold = 50;  // In degrees
        double minPower = 0.1;  // Motor power
        double maxPower = 0.5;  // Motor power

        if (error < minPowerErrorThreshold){
            return minPower;
        } else if (error > maxPowerErrorThreshold){
            return maxPower;
        } else {
            double slope = (maxPower - minPower) / (maxPowerErrorThreshold - minPowerErrorThreshold);
            double power = (error - minPowerErrorThreshold) * slope + minPower;
            return power;
        }
    }

    @Override
    public void runOpMode() {
        drive.init(hardwareMap, 1); // Puts the hardware devices from the current configuration into the drive, uses Limelight Pipeline 1: red
        manual_velocity = 0;


        waitForStart(); // This is for the LinearOpMode, starts after you press the Start button on the Driver Station
        resetRuntime();
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

            drive.setDriveMotors(forward, strafe, rotate); // Uses the drive function, which sends the values to the drive motors

//            if (gamepad2.b) { // Checking if b was just pressed
//                drive.pushBallUpStrong();
//            } else

            if (gamepad2.b){
                drive.setUpPush(0.9);
                transitionButtonPressed = true;
            } else if (gamepad2.right_bumper){
                drive.setUpPush(0.75);
                transitionButtonPressed = true;
            } else if (gamepad2.left_bumper){
                drive.setUpPush(0.6);
                transitionButtonPressed = true;
            } else if (!gamepad2.y) {
                transitionButtonPressed = false;
                yPressed = false;
                transitionOn = false;
                onTransitionTime = Double.POSITIVE_INFINITY;
            } else if (gamepad2.y && !yPressed) {
                transitionButtonPressed = false;
                transitionOn = true;
                onTransitionTime = getRuntime();
                yPressed = true;
            } else {
                transitionButtonPressed = false;
                drive.setUpPush(0);
            }

            if (transitionOn){
                transitionButtonPressed = false;
                double timePassed = getRuntime() - onTransitionTime;
                if (timePassed <= 0.2) {
                    drive.setUpPush(0.9);
                } else if (timePassed < 0.8){
                    drive.stopBallUp();
                } else {
                    onTransitionTime = getRuntime();
                }
            } else if (!transitionButtonPressed){
                drive.stopBallUp();
            }

//            if (gamepad2.aWasPressed()) {
//                manual_velocity += 25; // This ups the flywheel speed if a was pressed
//            }
//
//            if (gamepad2.xWasPressed()) {
//                manual_velocity -= 25; // This lowers the flywheel speed if x was pressed
//            }

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
                if (drive.flywheel.getVelocity() - velocity > 200){
                    drive.setFlywheel(0);
                } else if (velocity - drive.flywheel.getVelocity() > 200){
                    drive.setFlywheel(2400);
                } else {
                    drive.setFlywheel(velocity); // If we are shooting, it turns on the flywheel
                }
            } else {
                drive.setFlywheel(0); // Otherwise, turns it off
            }

            if (gamepad2.dpad_right && drive.turret.getCurrentPosition() < 450) {
                drive.turretClockwise(0.5); // Helper function
            } else if (gamepad2.dpad_left && drive.turret.getCurrentPosition() > -450) {
                drive.turretCounterClockwise(0.5); // Helper function
            } else {
                drive.stopTurret(); // Helper function
            }

            LLResult llResult = drive.limelight.getLatestResult();
            if (llResult != null && llResult.isValid()) {
                Pose3D botPose = llResult.getBotpose();
                double tx = llResult.getTx();
                double ta = llResult.getTa();
                telemetry.addLine("TARGET DETECTED");
                telemetry.addData("Target X", llResult.getTx());
                telemetry.addData("Target Y", llResult.getTy());
                telemetry.addData("Target Area", llResult.getTa());
                telemetry.addData("Velocity Modifier", manual_velocity);
                telemetry.addData("Total Velocity", velocity);
                telemetry.addLine("----------------------------");

                velocity = drive.calculatePower(ta) + manual_velocity;
                if (ta >= 0.5) {
                    velocity -= 70;
                } else {
                    velocity -= 40;
                }

                // Turret Clockwise subtracts from tx
                /// AUTO AIM
                double allowedErrorDegrees = 2;
                double error = 0, power = 0;
                if (tracking) {
                    if (tx > allowedErrorDegrees && drive.turret.getCurrentPosition() < 450) { //  && drive.turret.getCurrentPosition() < 450
                        error = Math.abs(tx - allowedErrorDegrees);
                        drive.turretClockwise(calculateTurretPower(error));
                    } else if (tx < -allowedErrorDegrees && drive.turret.getCurrentPosition() > -450) { //  && drive.turret.getCurrentPosition() > -450
                        error = Math.abs(tx + allowedErrorDegrees);
                        drive.turretCounterClockwise(calculateTurretPower(error));
                    } else {
                        drive.stopTurret();
                    }
                }


                telemetry.addLine(instructions); // This puts the instructions text from earlier on the screen
                telemetry.addData("Flywheel On", shooting); // Tells you if the flywheel is supposed to be on
                telemetry.addData("Flywheel Velocity", velocity); // Tells you the power of the flywheel
                telemetry.addData("Tracking", tracking);
                telemetry.update(); // Displays all the text
            } else {
                velocity = 1500;
            }
        }
    }
}
