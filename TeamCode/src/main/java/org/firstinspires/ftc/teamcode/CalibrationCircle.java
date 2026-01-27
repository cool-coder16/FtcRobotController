package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.mechanisms.FinalBench;

@Disabled
@Autonomous
public class CalibrationCircle extends LinearOpMode {
    FinalBench drive = new FinalBench();

    double power = 0.3;

    public void runOpMode(){
        drive.init(hardwareMap, 0);
        waitForStart();
        while (opModeIsActive()) {
            drive.setDriveMotors(power, 0, power);
        }
    }
}
