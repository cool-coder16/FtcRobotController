package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.mechanisms.MecanumBenchServo;

@Autonomous
public class RedAutoClose extends LinearOpMode {
    MecanumBenchServo drive = new MecanumBenchServo();
    boolean aimed, ran = false;
    int counts = 0;

    public void runOpMode(){
        // Init code
        drive.init(hardwareMap, 1);
        drive.setFlywheel(1450);
        waitForStart();
        while (opModeIsActive()){
            // Loop code
            if (!ran) {
                drive.drive(-0.5, 0, 0); // Move back
                sleep(700);
                drive.drive(0, 0, 0); // Stop Moving
                /*
                aimed = false;
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
                            drive.stopTurret();
                            aimed = true;
                        }

                        counts += 1;
                    } else {
                        drive.stopTurret();
                        aimed = true;
                    }
                }
                 */
                sleep(300);
                drive.pushBallUp(); // Shoot 3 Balls
                sleep(2200);
                drive.stopBallUp(); // Stop shooting
                drive.drive(0, 0, 0.5); // Turn
                drive.setFlywheel(0); // Stop flywheel
                sleep(300);
                drive.drive(0.5, 0, 0); // Move forward
                sleep(600);
                drive.drive(0, 0, 0); // Stop Moving, intake
                drive.turretCounterClockwise(0.3); // Turn turret
                sleep(400);
                drive.stopTurret(); // Stop turret
                drive.setFlywheel(1450); // Start flywheel
                drive.drive(-0.5, 0, 0); // Move backwards
                sleep(600);
                drive.drive(0, 0, 0); // Stop moving
                sleep(100);
                aimed = false;
                counts = 0;
                /*
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
                        aimed = true;
                    }
                }
                 */
                drive.pushBallUp(); // Shoot 3 balls
                sleep(2200);
                drive.stopBallUp(); // Stop shooting
                drive.setFlywheel(0); // Stop flywheel
                ran = true; // Prevent re-run
            }
        }
    }
}
