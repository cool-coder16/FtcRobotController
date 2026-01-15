package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.mechanisms.FinalBench;

@Autonomous
public class RedAutoClose extends LinearOpMode {
    FinalBench drive = new FinalBench();
    boolean aimed, ran = false;
    int counts = 0;

    double flywheel1;
    double flywheel2;

    // VARIABLES
    long back1 = 1650;
    long wait1 = 1000;
    long shoot1 = 2500;
    long turn1 = 600;
    long turnStop1 = 100;
    long intakeForward1 = 2000;
    long pushFirstBall = 200;
    long intakeWait2 = 1000;
    long moveTurret1 = 300;
    long back2 = 800;
    long wait3 = 100;
    long flywheelWait4 = 1000;
    long shoot2 = 2200;


    public void runOpMode() {
        // Init code
        drive.init(hardwareMap, 1);
        waitForStart();
        if (!ran) {
//                drive.turretCounterClockwise(0.3);
//                sleep(100);
//                drive.stopTurret();

            drive.intake();

            drive.drive(-0.5, 0, 0); // Move back
            sleep(back1);

            drive.drive(0, 0, 0); // Stop Moving
            counts = 0;
            aimed = false;
            while(!aimed && counts <= 100){
                LLResult llResult = drive.limelight.getLatestResult();
                if (llResult != null && llResult.isValid()) {
                    Pose3D botPose = llResult.getBotpose();
                    double tx = llResult.getTx() + 2;
                    double ta = llResult.getTa();
                    if (tx < -1){
                        double error = Math.abs(tx + 1);
                        double power = Math.max(error / 50, 0.05);
                        drive.turretCounterClockwise(power);
                    } else if (tx > 1) {
                        double error = Math.abs(tx - 1);
                        double power = Math.max(error / 50, 0.05);
                        drive.turretClockwise(power);
                    } else {
                        drive.stopTurret();
                        aimed = true;
                    }

                    counts += 1;

                    flywheel1 = 25.12398 * Math.pow(ta, 4) - 178.76699 * Math.pow(ta, 3) + 516.00924 * Math.pow(ta, 2)- 820.32747 * ta + 2006.96368;
                } else {
                    drive.stopTurret();
                    aimed = true;
                }
                sleep(20);
            }
            drive.stopTurret();
            drive.setFlywheel(flywheel1);
            sleep(wait1);

            drive.pushBallUp(); // Shoot 3 Balls
            sleep(shoot1);

            drive.stopBallUp(); // Stop shooting
            drive.drive(0, 0, 0.2); // Turn
            drive.setFlywheel(0); // Stop flywheel
            sleep(turn1);

            drive.drive(0, 0, 0);
            sleep(turnStop1);

            drive.drive(0.2, 0, 0); // Move forward
            sleep(intakeForward1);

            drive.pushBallUp();
            sleep(pushFirstBall);

            drive.stopBallUp();
            drive.drive(0, 0, 0); // Stop Moving, intake
            sleep(intakeWait2);

            drive.turretCounterClockwise(0.4); // Turn turret
            sleep(moveTurret1);

            drive.stopTurret(); // Stop turret
            drive.drive(-0.5, 0, 0); // Move backwards
            sleep(back2);

            drive.drive(0, 0, 0); // Stop moving
            sleep(wait3);

            counts = 0;
            aimed = false;
            while(!aimed && counts <= 100){
                LLResult llResult = drive.limelight.getLatestResult();
                if (llResult != null && llResult.isValid()) {
                    Pose3D botPose = llResult.getBotpose();
                    double tx = llResult.getTx() + 2;
                    double ta = llResult.getTa();
                    if (tx < -1){
                        double error = Math.abs(tx + 1);
                        double power = Math.max(error / 50, 0.05);
                        drive.turretCounterClockwise(power);
                    } else if (tx > 1) {
                        double error = Math.abs(tx - 1);
                        double power = Math.max(error / 50, 0.05);
                        drive.turretClockwise(power);
                    } else {
                        drive.stopTurret();
                        aimed = true;
                    }

                    counts += 1;

                    flywheel2 = 25.12398 * Math.pow(ta, 4) - 178.76699 * Math.pow(ta, 3) + 516.00924 * Math.pow(ta, 2)- 820.32747 * ta + 2006.96368;
                } else {
                    drive.stopTurret();
                    aimed = true;
                }
                sleep(20);
            }

            drive.stopTurret();
            drive.setFlywheel(flywheel2); // Start flywheel
            sleep(flywheelWait4);

            drive.pushBallUp(); // Shoot 3 balls
            sleep(shoot2);

            drive.stopBallUp(); // Stop shooting
            drive.setFlywheel(0); // Stop flywheel
            ran = true; // Prevent re-run
        }
    }
}