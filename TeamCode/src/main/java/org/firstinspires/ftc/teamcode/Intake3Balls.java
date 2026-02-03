package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.FinalBench;

@TeleOp(group = "Test Code")
public class Intake3Balls extends LinearOpMode {
    FinalBench drive = new FinalBench();

    public void driveForward(double power, long duration){
        drive.setDriveMotors(power, 0, 0);
        sleep(duration);
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

    public void driveBackward(double power, long duration){
        drive.setDriveMotors(-power, 0, 0);
        sleep(duration);
        drive.setDriveMotors(0, 0, 0);
        sleep(100);
    }

    public void doubleDriveBackward(double power1, double power2, long duration1, long duration2){
        drive.setDriveMotors(-power1, 0, 0);
        sleep(duration1);
        drive.setDriveMotors(-power2, 0, 0);
        sleep(duration2);
        drive.setDriveMotors(0, 0, 0);
        sleep(100);
    }

    public void runOpMode(){
        drive.init(hardwareMap, 1);


        waitForStart();
        resetRuntime();
        drive.intake();

        doubleDriveForward(0.4, 0.2, 680, 1652, true);
        sleep(200);
        doubleDriveBackward(0.4, 0.2, 1181, 350);


    }
}
