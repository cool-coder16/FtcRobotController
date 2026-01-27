package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.mechanisms.FinalBench;

@Disabled
@Autonomous
public class RotateToYaw extends LinearOpMode {
    //IMPORTANT: Right(counterclockwise) is +degrees
    FinalBench drive = new FinalBench();

    boolean ran = false;

    public double calculateRotation(double error){
        double minPowerErrorThreshold = 15;  // In degrees
        double maxPowerErrorThreshold = 45;  // In degrees
        double minPower = 0.2;  // Motor power
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

    public void runOpMode(){
        drive.init(hardwareMap, 1);

        waitForStart();

        while (opModeIsActive()) {
            if (!ran) {
                turnToTargetYaw(-45);
            }
            ran = true;
        }
    }
}
