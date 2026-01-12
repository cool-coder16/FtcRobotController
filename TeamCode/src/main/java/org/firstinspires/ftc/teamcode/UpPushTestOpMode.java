package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.UpPushServoTest;

@Disabled
@TeleOp
public class UpPushTestOpMode extends LinearOpMode {
    UpPushServoTest drive = new UpPushServoTest();

    public void runOpMode(){
        drive.init(hardwareMap);

        waitForStart();
        while (opModeIsActive()){
            if (gamepad1.b) {
                drive.runForward();
            } else if (gamepad1.x){
                drive.runBackward();
            } else {
                drive.stopServo();
            }
        }
    }
}
