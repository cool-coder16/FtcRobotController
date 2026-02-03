package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.FinalBench;

@Disabled
@TeleOp(group = "Test Code")
public class TransitionTest extends LinearOpMode {
    FinalBench drive = new FinalBench();

    double startTransition = Double.POSITIVE_INFINITY;
    double secondLength = 0.150;

    public void runOpMode(){
        drive.init(hardwareMap, 1);


        waitForStart();
        resetRuntime();
        drive.intake();

        while (opModeIsActive()){
            if (gamepad1.bWasPressed() && startTransition == Double.POSITIVE_INFINITY){
                startTransition = getRuntime();
                drive.setUpPush(0.5);
            }

            if (getRuntime() - startTransition >= secondLength){
                startTransition = Double.POSITIVE_INFINITY;
                drive.stopBallUp();
            }
        }
    }
}
