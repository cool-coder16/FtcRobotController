package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.mechanisms.MecanumBenchServo;

@Disabled
@TeleOp
public class MecanumWheelTest extends LinearOpMode {
    MecanumBenchServo drive = new MecanumBenchServo();

    public void runOpMode(){
        drive.init(hardwareMap);
        drive.intake_motor.setPower(0);
        waitForStart();

        while (opModeIsActive()){
            if (gamepad1.a){
                drive.back_left_motor.setPower(1.0);
            }
            else if (gamepad1.b){
                drive.back_right_motor.setPower(1.0);
            }
            else if (gamepad1.x){
                drive.front_left_motor.setPower(1.0);
            }
            else if (gamepad1.y){
                drive.front_right_motor.setPower(1.0);
            }
            else if (gamepad1.dpad_up) {
                drive.back_left_motor.setPower(1.0);
                drive.back_right_motor.setPower(1.0);
                drive.front_left_motor.setPower(1.0);
                drive.front_right_motor.setPower(1.0);
            } else if (gamepad1.dpad_right) {
                drive.front_left_motor.setPower(1.0);
                drive.front_right_motor.setPower(-1.0);
                drive.back_left_motor.setPower(-1.0);
                drive.back_right_motor.setPower(1.0);
            } else if (gamepad1.dpad_left) {
                drive.front_right_motor.setPower(1.0);
                drive.front_left_motor.setPower(-1.0);
                drive.back_right_motor.setPower(-1.0);
                drive.back_left_motor.setPower(1.0);
            } else if (gamepad1.dpad_down){
                drive.back_left_motor.setPower(-1.0);
                drive.back_right_motor.setPower(-1.0);
                drive.front_left_motor.setPower(-1.0);
                drive.front_right_motor.setPower(-1.0);
            } else {
                drive.back_left_motor.setPower(0);
                drive.back_right_motor.setPower(0);
                drive.front_left_motor.setPower(0);
                drive.front_right_motor.setPower(0);
            }
        }
    }
}
