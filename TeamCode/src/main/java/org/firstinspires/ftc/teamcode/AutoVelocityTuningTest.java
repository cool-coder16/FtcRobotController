package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.mechanisms.FinalBench;


@TeleOp(group = "Test Code")
public class AutoVelocityTuningTest extends LinearOpMode {
    FinalBench drive = new FinalBench();
    double velocity = 1000;

    double[] stepSizes = {50, 25, 10, 1};
    int stepIndex = 0;

    public void runOpMode(){
        drive.init(hardwareMap, 0);

        waitForStart();

        while (opModeIsActive()){

            drive.intake();
            drive.setFlywheel(velocity);

            if (gamepad1.aWasPressed()){
                velocity += stepSizes[stepIndex];
            }

            if (gamepad1.yWasPressed()){
                velocity -= stepSizes[stepIndex];
            }

            if (gamepad1.b){
                drive.pushBallUpStrong();
            } else {
                drive.stopBallUp();
            }

            if (gamepad1.xWasPressed()){
                stepIndex = (stepIndex + 1) % stepSizes.length;
            }

            LLResult llResult = drive.limelight.getLatestResult();
            if (llResult != null && llResult.isValid()) {
                Pose3D botPose = llResult.getBotpose();
                double tx = llResult.getTx() + 2;
                double ta = llResult.getTa();
                telemetry.addLine("TARGET DETECTED");
                telemetry.addData("tx", tx);
                telemetry.addData("Target Area", llResult.getTa());
                telemetry.addData("Total Velocity", velocity);
                telemetry.addLine("----------------------------");
            }

            telemetry.update();
        }
    }
}
