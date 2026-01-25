package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.mechanisms.FinalBench;

@Disabled
@Autonomous
public class CalibrationSquareWithTurn extends LinearOpMode {
    FinalBench drive = new FinalBench();

    long forwardTime = 1000;
    long turnTime = 1000;
    double forwardPower = 0.3;
    double turnPower = 0.3;

    public void runOpMode(){
        drive.init(hardwareMap, 1);
        waitForStart();
        for(int i = 0; i < 4; i++) {
            drive.drive(forwardPower, 0, 0);
            sleep(forwardTime);
            drive.drive(0, 0, turnPower);
            sleep(turnTime);
        }
        drive.drive(0, 0, 0);
    }
}
