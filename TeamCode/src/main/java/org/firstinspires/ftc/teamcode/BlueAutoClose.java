package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.mechanisms.FinalBench;

@Autonomous(group = "Autonomous")
public class BlueAutoClose extends LinearOpMode {
    FinalBench drive = new FinalBench();

    boolean ran = false;

    public void strafe(double power, long duration){
        drive.setDriveMotors(0, power, 0);
        sleep(duration);
        drive.setDriveMotors(0, 0, 0);
        sleep(100);
    }

    public void driveForward(double power, long duration){
        drive.setDriveMotors(power, 0, 0);
        sleep(duration);
        drive.setDriveMotors(0, 0, 0);
        sleep(100);
    }

    public void driveBackward(double power, long duration){
        drive.setDriveMotors(-power, 0, 0);
        sleep(duration);
        drive.setDriveMotors(0, 0, 0);
        sleep(100);
    }

    public double taToDistance(double ta){
        double scale = 30665.95; //TODO(vivaan): tune this constant
        return Math.sqrt(scale / ta);  // Returns CM
    }

    public double calculatePower(double error){
        double minPowerErrorThreshold = 25;  // In cm
        double maxPowerErrorThreshold = 100;  // In cm
        double minPower = 0.2;  // Motor power
        double maxPower = 0.75;  // Motor power

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

    public void driveToTargetDistance(double targetDistance){
        int invalidCountsInARow = 0;
        boolean sawAprilTag = false;

        while (invalidCountsInARow < 10) {
            LLResult llResult = drive.limelight.getLatestResult();
            if (llResult == null || !llResult.isValid()) {
                if (!sawAprilTag) {
                    /// Until we see the april tag for the first time, we assume that
                    /// we are in the start position for the close auto, at the gate.
                    /// When we see it, we stop moving back at default, and instead assume
                    /// something went wrong.
                    drive.setDriveMotors(-0.6, 0, 0);
                } else {
                    invalidCountsInARow++;
                    sleep(100);;
                }
                continue;
            }
            invalidCountsInARow = 0;

            sawAprilTag = true;

            // Distances are in cm
            double currentDistance = taToDistance(llResult.getTa());
            double tolerance = 3;  // In cm

            double error = Math.abs(currentDistance - targetDistance);  // Always positive
            if (error < tolerance) {
                drive.setFlywheel(drive.calculatePower(llResult.getTa()) - 70);
                break;
            }

            boolean driveForward;
            if (currentDistance > targetDistance){
                driveForward = true;
            } else {
                driveForward = false;
            }

            double power = calculatePower(error);

            telemetry.addData("Distance(cm)", currentDistance);
            telemetry.addData("Power", power);
            telemetry.addData("Error", error);
            telemetry.addData("Invalids", invalidCountsInARow);
            telemetry.update();

            if (driveForward) {
                drive.setDriveMotors(power, 0, 0);
            } else if (!driveForward) {
                drive.setDriveMotors(-power, 0, 0);
            }
        }

        drive.setDriveMotors(0, 0, 0);
        sleep(500);
    }

    public void doubleDriveBackward(double power1, double power2, long duration1, long duration2){
        drive.setDriveMotors(-power1, 0, 0);
        sleep(duration1);
        drive.setDriveMotors(-power2, 0, 0);
        sleep(duration2);
        drive.setDriveMotors(0, 0, 0);
        sleep(100);
    }

    public void doubleDriveForward(double power1, double power2, long duration1, long duration2, boolean transitionOn){
        drive.setDriveMotors(power1, 0, 0);
        sleep(duration1);
        if (transitionOn){
            drive.setUpPush(0.2);
        }
        drive.setDriveMotors(power2, 0, 0);
        sleep(duration2);
        drive.setDriveMotors(0, 0, 0);
        sleep(100);
        drive.stopBallUp();
    }

    public double calculateRotation(double error){
        double minPowerErrorThreshold = 10;  // In degrees
        double maxPowerErrorThreshold = 45;  // In degrees
        double minPower = 0.15;  // Motor power
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

    public void turnToTargetYaw(double targetYaw){
        int invalidCountsInARow = 0;

        while (invalidCountsInARow < 10) {
            // Distances are in cm
            double currentYaw = drive.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
            double tolerance = 1;  // In degrees

            double error = Math.abs(currentYaw - targetYaw);  // Always positive
            if (error < tolerance) {
                break;
            }

            boolean driveClockwise;
            if (currentYaw > targetYaw){
                driveClockwise = true;
            } else {
                driveClockwise = false;
            }

            double power = calculateRotation(error);

            telemetry.addData("Distance(cm)", currentYaw);
            telemetry.addData("Power", power);
            telemetry.addData("Error", error);
            telemetry.update();

            if (driveClockwise) {
                drive.setDriveMotors(0, 0, power);
            } else if (!driveClockwise) {
                drive.setDriveMotors(0, 0, -power);
            }
        }

        drive.setDriveMotors(0, 0, 0);
    }

    public void shootAllBalls(long shootTime) {
        drive.setUpPush(0.7); // Shoot 3 Balls
        sleep(shootTime);
        drive.stopBallUp(); // Stop shooting
//        drive.setFlywheel(0); // Stop flywheel
        sleep(100);
    }

    public void runOpMode(){
        drive.init(hardwareMap, 0);
        while (opModeInInit()){
            telemetry.addData("Yaw", drive.imu.getRobotYawPitchRollAngles().getYaw());
            telemetry.update();
        }

        waitForStart();
        drive.intake();
        drive.setFlywheel(1500);



        turnToTargetYaw(45);
        driveToTargetDistance(155);
        sleep(200);
        shootAllBalls(1000);

        turnToTargetYaw(90);
        doubleDriveForward(0.4, 0.2, 680, 1670, false);
        sleep(200);
        doubleDriveBackward(0.4, 0.2, 1181, 368);

        turnToTargetYaw(45);
        driveToTargetDistance(160);
        sleep(200);
        shootAllBalls(1000);

        turnToTargetYaw(90);
        strafe(-0.75, 680);
        turnToTargetYaw(90);

        doubleDriveForward(0.4, 0.2, 680, 1670, false);
        sleep(200);
        doubleDriveBackward(0.4, 0.2, 1181, 368);
        strafe(0.75, 720);

        turnToTargetYaw(45);
        driveToTargetDistance(155);
        sleep(200);
        shootAllBalls(1000);

        turnToTargetYaw(90);
        strafe(-0.75, 1000);
        turnToTargetYaw(90);

        doubleDriveForward(0.4, 0.2, 680, 1670, false);
        sleep(200);
    }
}
