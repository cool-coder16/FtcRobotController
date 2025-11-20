package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.mechanisms.MecanumBenchServo;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

@TeleOp
public class BlueTeleOp extends LinearOpMode {
    MecanumBenchServo drive = new MecanumBenchServo();
    AprilTagWebcam aprilTag = new AprilTagWebcam();
    double forward, strafe, rotate, goalAngle;
    double power;
    AprilTagDetection goal = aprilTag.getTagById(20);

    public void autoShootOrient(){
        double distance,yaw,x;

        double forwardPower = 0, strafePower = 0, rotatePower = 0;
        int runs = 0;
        while (runs < 30){
            distance = aprilTag.getWebcamDistance(goal);
            yaw = aprilTag.getWebcamYaw(goal);
            x = aprilTag.getWebcamStrafe(goal);

            if (10.0 < yaw){
                rotatePower = 1.0; // Adjust for correct direction
            } else if (-10.0 > yaw) {
                rotatePower = -1.0; // Same
            }

            if (10.0 < x){
                strafePower = -1.0; // Same
            } else if (-10.0 > x) {
                strafePower = 1.0;
            }

            if (distance > 45){ // INPUT CORRECT DISTANCE HERE AFTER TESTING, and adjust negativity
                forwardPower = -1.0;
            } else if (distance < 35){
                forwardPower = 1.0; // Do all above comments
            }

            drive.drive(forwardPower, strafePower, rotatePower);

            forwardPower = 0;
            strafePower = 0;
            rotatePower = 0;

            runs += 1;
        }

        drive.drive(0, 0, 0);
        drive.shootBall();
    }

    @Override
    public void runOpMode(){
        drive.init(hardwareMap);
        aprilTag.init(hardwareMap, telemetry);
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

            aprilTag.updateWebcam();

            if (gamepad1.bWasPressed()){
                drive.shootBall();
                sleep(700);
                drive.stopShoot();
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

            if (gamepad1.left_bumper){
                power = 0;
            }

            if (gamepad1.left_trigger > 0.5){
                this.autoShootOrient();
            }

            if (gamepad1.right_stick_button){
                drive.push1.setPosition(1.0);
                drive.push1.setPosition(-1.0);
            }

            telemetry.addData("Facing", drive.getDirectionFacing(AngleUnit.DEGREES));
            telemetry.addData("Rotate", rotate);
            telemetry.addData("Flywheel Speed", power);
            aprilTag.displayWebcamTelemetry(goal);
            telemetry.update();
        }
    }
}
