package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.mechanisms.FinalBench;

@Autonomous
public class RedAutoFar extends LinearOpMode {
    FinalBench drive = new FinalBench();

    public void driveForward(double power, long duration){
        drive.drive(power, 0, 0);
        sleep(duration);
        drive.drive(0, 0, 0);
        sleep(100);
    }

    public void driveBackward(double power, long duration){
        drive.drive(-power, 0, 0);
        sleep(duration);
        drive.drive(0, 0, 0);
        sleep(100);
    }

    public void turn(double power, long duration){
        drive.drive(0, 0, power);
        sleep(duration);
        drive.drive(0, 0, 0);
        sleep(100);
    }

    public void strafe(double power, long duration){
        drive.drive(0, power, 0);
        sleep(duration);
        drive.drive(0, 0, 0);
        sleep(100);
    }

    public void autoAim(int loops, double modifier){
        int ALLOWED_ERROR = 2;
        double flywheelSpeed = 0;
        int counts = 0;
        boolean aimed = false;
        while(!aimed && counts <= loops){
            LLResult llResult = drive.limelight.getLatestResult();
            if (llResult != null && llResult.isValid()) {
                Pose3D botPose = llResult.getBotpose();
                double tx = llResult.getTx() + modifier;
                double ta = llResult.getTa();
                telemetry.addData("tx", tx);
                telemetry.update();
                if (tx < -ALLOWED_ERROR){
                    double error = Math.abs(tx);
                    double power = Math.max(error / 40.0, 0.08);
                    drive.turretCounterClockwise(power);
                } else if (tx > ALLOWED_ERROR) {
                    double error = Math.abs(tx);
                    double power = Math.max(error / 40.0, 0.08);
                    drive.turretClockwise(power);
                } else {
                    drive.stopTurret();
                    aimed = true;
                    telemetry.addData("tx", tx);
                    telemetry.update();
                }

                counts += 1;

                flywheelSpeed = 25.12398 * Math.pow(ta, 4) - 178.76699 * Math.pow(ta, 3) + 516.00924 * Math.pow(ta, 2)- 820.32747 * ta + 2006.96368;
                flywheelSpeed -= 25;
            } else {
                drive.stopTurret();
//                aimed = true;
                telemetry.addLine("INVALID LIMELIGHT RESULT");
                telemetry.update();
            }
            sleep(30);
        }

        drive.stopTurret();
        drive.setFlywheel(flywheelSpeed);
        sleep(150);
    }

    public void getTx(){
        LLResult llResult = drive.limelight.getLatestResult();
        if (llResult != null && llResult.isValid()) {
            Pose3D botPose = llResult.getBotpose();
            double tx = llResult.getTx();
            telemetry.addData("tx", tx);
            telemetry.update();
        } else {
            telemetry.addLine("TARGET NOT FOUND");
            telemetry.update();
        }
    }

    public void shootAllBalls(long shootTime){
        drive.pushBallUp(); // Shoot 3 Balls
        sleep(shootTime);
        drive.stopBallUp(); // Stop shooting
        drive.setFlywheel(0); // Stop flywheel
        sleep(100);
    }

    public void runOpMode(){
        drive.init(hardwareMap, 1);
        while (opModeInInit()){
            getTx();
        }
        waitForStart();

        drive.intake();

        autoAim(1000, 2);
        sleep(1000);

        shootAllBalls(2000);

        strafe(0.5, 1200);

    }
}
