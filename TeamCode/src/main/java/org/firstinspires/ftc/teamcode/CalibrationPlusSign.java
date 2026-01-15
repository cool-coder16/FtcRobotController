package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.mechanisms.FinalBench;

@Autonomous
public class CalibrationPlusSign extends LinearOpMode {
    FinalBench drive = new FinalBench();

    long distanceTime = 1000;
    double forwardPower = 0.3;
    double strafePower = 0.3;

    public void runOpMode() {
        drive.init(hardwareMap, 1);
        waitForStart();

        drive.drive(forwardPower, 0, 0);
        sleep(distanceTime / 2);
        drive.drive(-forwardPower, 0, 0);
        sleep(distanceTime);
        drive.drive(forwardPower, 0, 0);
        sleep(distanceTime / 2);
        drive.drive(0, strafePower, 0);
        sleep(distanceTime / 2);
        drive.drive(0, -strafePower, 0);
        sleep(distanceTime);
        drive.drive(0, strafePower, 0);
        sleep(distanceTime / 2);
        drive.drive(0, 0, 0);
    }
}
