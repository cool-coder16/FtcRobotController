package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.FinalBench;

@TeleOp(group = "Test Code")
public class ForwardIntake extends LinearOpMode {
    FinalBench drive = new FinalBench();

    public void runOpMode(){
        drive.init(hardwareMap, 1);

        waitForStart();
        drive.intake();

        while (opModeIsActive()){
            if (gamepad1.dpadDownWasPressed() || gamepad2.dpadDownWasPressed()){
                drive.outtake();
            }

            if (gamepad1.dpadUpWasPressed() || gamepad2.dpadUpWasPressed()){
                drive.intake();
            }

            if (gamepad1.bWasPressed() || gamepad2.bWasPressed()){
                drive.setDriveMotors(0.4, 0, 0);
                sleep(500);
                drive.setDriveMotors(0, 0, 0);
            }
        }
    }
}
