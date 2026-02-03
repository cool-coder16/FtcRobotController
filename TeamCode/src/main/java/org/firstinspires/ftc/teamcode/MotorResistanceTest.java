package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.mechanisms.FinalBench;

@TeleOp(group = "Test Code")
public class MotorResistanceTest extends LinearOpMode {
    FinalBench drive = new FinalBench();

    public void runOpMode(){
        drive.init(hardwareMap, 1);

        waitForStart();
        drive.intake();
        while (opModeIsActive()){
            telemetry.addData("Current", drive.intake_motor.getCurrent(CurrentUnit.AMPS));
            telemetry.update();
        }
    }
}
