package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.Mecanum_Test;

@TeleOp
public class MecanumFieldOrientatedOpMode extends OpMode {
    Mecanum_Test drive = new Mecanum_Test();

    double forward, strafe, rotate, power;

    @Override
    public void init() {
        drive.init(hardwareMap);
        power = 0.55;
    }

    @Override
    public void loop() {
        forward = gamepad1.left_stick_y;
        strafe = gamepad1.left_stick_x;
        rotate = gamepad1.right_stick_x;

        drive.driveFieldOriented(forward, strafe, rotate);

        if (gamepad1.b){
            drive.shootBall(power);
        }

        if (gamepad1.a){
            power += 0.05;
        }

        if (gamepad1.x){
            power -= 0.05;
        }
    }
}
