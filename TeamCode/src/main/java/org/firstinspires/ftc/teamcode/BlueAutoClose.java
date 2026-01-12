package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.mechanisms.MecanumBenchServo;

@Autonomous
public class BlueAutoClose extends LinearOpMode {
    MecanumBenchServo drive = new MecanumBenchServo();
    boolean aimed, ran = false;
    int counts;

    public void runOpMode(){
        // Init code
        drive.init(hardwareMap, 0);
        drive.setFlywheel(1550);
        waitForStart();
        while (opModeIsActive()){
            // Loop code
            if (!ran) {
                drive.drive(-1.0, 0, 0); // Move back
                sleep(350);
                drive.drive(0, 0, 0); // Stop Moving
                while(!aimed && counts <= 30){
                    LLResult llResult = drive.limelight.getLatestResult();
                    if (llResult != null && llResult.isValid()) {
                        Pose3D botPose = llResult.getBotpose();
                        double tx = llResult.getTx();
                        if (tx < -1){
                            double error = tx + 1;
                            double power = Math.max(-error / 50, 0.05);
                            drive.turretCounterClockwise(power);
                        } else if (tx > 1) {
                            double error = tx - 1;
                            double power = Math.max(error / 50, 0.05);
                            drive.turretClockwise(power);
                        } else {
                            aimed = true;
                            drive.stopTurret();
                        }

                        counts += 1;
                    } else {
                        drive.stopTurret();
                    }
                }
                drive.pushBallUp(); // Shoot 3 Balls
                sleep(2000);
                drive.stopBallUp(); // Stop shooting
                drive.drive(0, 0, -1); // Turn
                drive.setFlywheel(0); // Stop flywheel
                sleep(300);
                drive.drive(1, 0, 0); // Move forward
                sleep(300);
                drive.drive(0, 0, 0); // Stop Moving, intake
                drive.turretClockwise(0.3); // Turn turret
                sleep(500);
                drive.stopTurret(); // Stop turret
                drive.setFlywheel(1550); // Start flywheel
                drive.drive(-1, 0, 0); // Move backwards
                sleep(300);
                drive.drive(0, 0, 0); // Stop moving
                sleep(100);
                while(!aimed && counts <= 30){
                    LLResult llResult = drive.limelight.getLatestResult();
                    if (llResult != null && llResult.isValid()) {
                        Pose3D botPose = llResult.getBotpose();
                        double tx = llResult.getTx();
                        if (tx < -1){
                            double error = tx + 1;
                            double power = Math.max(-error / 50, 0.05);
                            drive.turretCounterClockwise(power);
                        } else if (tx > 1) {
                            double error = tx - 1;
                            double power = Math.max(error / 50, 0.05);
                            drive.turretClockwise(power);
                        } else {
                            aimed = true;
                            drive.stopTurret();
                        }

                        counts += 1;
                    } else {
                        drive.stopTurret();
                    }
                }
                drive.pushBallUp(); // Shoot 3 balls
                sleep(2000);
                drive.stopBallUp(); // Stop shooting
                drive.setFlywheel(0); // Stop flywheel
                ran = true; // Prevent re-run
            }
        }
    }
}
