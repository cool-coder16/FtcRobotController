package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.FinalBench;

@Disabled
@TeleOp(group = "Test Code")
public class TurretAngleTest extends LinearOpMode {
    FinalBench drive = new FinalBench();

    public void runOpMode(){
        drive.init(hardwareMap, 1);

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.dpad_right){
                drive.turretClockwise(0.5);
            } else if (gamepad1.dpad_left){
                drive.turretCounterClockwise(0.5);
            } else {
                drive.stopTurret();
            }

            telemetry.addData("Turret Ticks", drive.turret.getCurrentPosition());
            telemetry.update();
        }
    }
}
