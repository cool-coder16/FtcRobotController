package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.TestFlywheel;

@Disabled
@Deprecated
@TeleOp
public class FlywheelTestTeleOp extends LinearOpMode {
    TestFlywheel drive = new TestFlywheel();

    public void runOpMode(){
        drive.init(hardwareMap);

        waitForStart();
        while (opModeIsActive()){
            if (gamepad1.b){
                drive.forwards(1);
            } else if (gamepad1.a){
                drive.backwards(1);
            } else {
                drive.stopWheel();
            }
        }
    }
}
