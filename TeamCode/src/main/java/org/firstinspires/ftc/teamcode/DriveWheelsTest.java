package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.FinalBench;

@TeleOp
public class DriveWheelsTest extends LinearOpMode {
    FinalBench drive = new FinalBench();

    int runMode = 0;
    double power = 0.5;

    public void runOpMode(){
        drive.init(hardwareMap, 0);
        waitForStart();
        while (opModeIsActive()){
            if (gamepad1.bWasPressed()){
                runMode = 1;
            }

            if (gamepad1.xWasPressed()){
                runMode = 2;
            }

            if (gamepad1.yWasPressed()){
                runMode = 0;
            }

            if (runMode == 1){
                drive.front_right_motor.setPower(power);
                drive.back_right_motor.setPower(power);
                drive.back_left_motor.setPower(power);
                drive.front_left_motor.setPower(power);
                telemetry.addData("Front_Right_Power", drive.front_right_motor.getVelocity());
                telemetry.addData("Back_Right_Power", drive.back_right_motor.getVelocity());
                telemetry.addData("Front_Left_Power", drive.front_left_motor.getVelocity());
                telemetry.addData("Back_Left_Power", drive.back_left_motor.getVelocity());
            } else if (runMode == 2) {
                drive.front_right_motor.setPower(-power);
                drive.back_right_motor.setPower(-power);
                drive.back_left_motor.setPower(-power);
                drive.front_left_motor.setPower(-power);
                telemetry.addData("Front_Right_Power", drive.front_right_motor.getVelocity());
                telemetry.addData("Back_Right_Power", drive.back_right_motor.getVelocity());
                telemetry.addData("Front_Left_Power", drive.front_left_motor.getVelocity());
                telemetry.addData("Back_Left_Power", drive.back_left_motor.getVelocity());
            } else {
                drive.front_right_motor.setPower(0);
                drive.back_right_motor.setPower(0);
                drive.back_left_motor.setPower(0);
                drive.front_left_motor.setPower(0);
                telemetry.addData("Front_Right_Power", drive.front_right_motor.getVelocity());
                telemetry.addData("Back_Right_Power", drive.back_right_motor.getVelocity());
                telemetry.addData("Front_Left_Power", drive.front_left_motor.getVelocity());
                telemetry.addData("Back_Left_Power", drive.back_left_motor.getVelocity());
            }
            telemetry.update();
        }
    }
}
