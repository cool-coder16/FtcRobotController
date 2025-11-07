package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Disabled
@TeleOp
public class GamepadPractice extends OpMode {
    @Override
    public void init() {

    }

    @Override
    public void loop() {
        double speedForward = -gamepad1.left_stick_y * 0.6;
        double differenceX = gamepad1.left_stick_x - gamepad1.right_stick_x;
        double sumTriggers = gamepad1.right_trigger + gamepad1.left_trigger;

       telemetry.addData("left x", gamepad1.left_stick_x);
       telemetry.addData("left y", gamepad1.left_stick_y);
       telemetry.addData("right x", gamepad1.right_stick_x);
       telemetry.addData("right y", gamepad1.right_stick_y);
       telemetry.addData("a", gamepad1.a);
       telemetry.addData("b", gamepad1.b);
       telemetry.addData("Forward Speed", speedForward);
       telemetry.addData("Difference x", differenceX);
       telemetry.addData("Sum Triggers", sumTriggers);
    }
}
