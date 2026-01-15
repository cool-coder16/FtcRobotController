package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.mechanisms.FinalBench;

@Autonomous
public class CalibrationSquareNoTurn extends LinearOpMode {
    FinalBench drive = new FinalBench();

    long time = 1500;
    double power = 0.3;

    public void runOpMode(){
        drive.init(hardwareMap, 0);
        waitForStart();
        drive.drive(power, 0, 0);
        sleep(time);
        drive.drive(0, power, 0);
        sleep(time);
        drive.drive(-power, 0, 0);
        sleep(time);
        drive.drive(0, -power, 0);
        sleep(time);
        drive.drive(0, 0, 0);
    }
}
