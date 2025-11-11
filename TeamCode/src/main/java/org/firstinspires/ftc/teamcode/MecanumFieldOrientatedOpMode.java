package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
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
        forward = -gamepad1.left_stick_y;
        rotate = gamepad1.right_stick_x;
        strafe = gamepad1.left_stick_x;

        drive.drive(forward, strafe, rotate);
        drive.setFlywheel(power);

        if (gamepad1.b){
            drive.shootBall();}

        if (gamepad1.a){
            power += 0.05;
        }

        if (gamepad1.x){
            power -= 0.05;
        }

        if (gamepad1.dpad_down){
            drive.outtake();
        }

        if (gamepad1.dpad_up){
            drive.intake();
        }

        if (gamepad1.dpad_left || gamepad1.dpad_right){
            drive.stoptake();
        }

        if (gamepad1.right_bumper){
            power = 0.55;
        }

        if (gamepad1.left_bumper){
            power = 0;
        }

        telemetry.addData("Facing", drive.getDirectionFacing(AngleUnit.DEGREES));
    }
}
