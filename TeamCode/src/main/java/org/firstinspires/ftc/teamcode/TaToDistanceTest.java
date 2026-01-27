package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.FinalBench;

@TeleOp(group = "Test Code")
public class TaToDistanceTest extends LinearOpMode {
    FinalBench drive = new FinalBench();

    public double distanceToTa(double distance){
        return distance;
    }

    public double taToDistance(double ta){
        double scale = 30665.95; //TODO(vivaan): tune this constant
        double distance = Math.sqrt(scale / ta);
        return distance;  // Returns CM
    }

    public void driveToTargetDistance(double targetDistance){
        double Kp = 1/80;  // Proportional constant, top speed = 0.75/Kp

        int invalidCount = 0;
        while (invalidCount < 10) {
            LLResult llResult = drive.limelight.getLatestResult();
            if (llResult == null || !llResult.isValid()) {
                invalidCount++;
                continue;
            }

            // Distances are in cm
            double currentDistance = taToDistance(llResult.getTa());
            double tolerance = 5;  // In cm

            double error = Math.abs(currentDistance - targetDistance);  // Always positive
            if (error < tolerance) {
                break;
            }

            boolean driveForward;
            if (currentDistance > targetDistance){
                driveForward = true;
            } else {
                driveForward = false;
            }

            double power = Math.max(Math.min(Kp * error, 0.75), 0.05);  // Limit the power in between 0.75 and 0.05

            if (driveForward) {
                drive.setDriveMotors(power, 0, 0);
            } else if (!driveForward) {
                drive.setDriveMotors(-power, 0, 0);
            }
        }

        drive.setDriveMotors(0, 0, 0);
    }

    public void runOpMode(){
        drive.init(hardwareMap, 1);

        waitForStart();

        while (opModeIsActive()){
            LLResult llResult = drive.limelight.getLatestResult();
            if (llResult != null && llResult.isValid()) {
                double ta = llResult.getTa();

                telemetry.addData("ta", ta);
                telemetry.addData("x    Distance", taToDistance(ta));
            }
            telemetry.update();
        }
    }
}
