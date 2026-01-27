package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.FinalBench;

@Disabled
@TeleOp(group = "Test Code")
public class FrontBackTest extends LinearOpMode {
    FinalBench drive = new FinalBench();

    public void runOpMode(){
        drive.init(hardwareMap, 1);
        waitForStart();
        while (opModeIsActive()){
            if (gamepad1.xWasPressed()){
                drive.setDriveMotors(0.2, 0, 0);
                sleep(2000);
                drive.setDriveMotors(0, 0, 0);
            }

            if (gamepad1.yWasPressed()){
                drive.setDriveMotors(-0.2, 0, 0);
                sleep(2000);
                drive.setDriveMotors(0, 0, 0);
            }

            telemetry.addLine("X for FORWARD, Y for BACK");
            telemetry.update();
        }
    }
}
