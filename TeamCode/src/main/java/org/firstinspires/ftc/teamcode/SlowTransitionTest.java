package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.FinalBench;

@TeleOp(group = "Test Code")
public class SlowTransitionTest extends LinearOpMode {
    FinalBench drive = new FinalBench();

    public void runOpMode(){
        drive.init(hardwareMap, 1);
        waitForStart();
        drive.intake();

        while (opModeIsActive()){
            if (gamepad1.b){
                drive.setUpPush(1);
            } else {
                drive.stopBallUp();
            }
        }
    }
}
