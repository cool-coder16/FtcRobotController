package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Disabled
@TeleOp
public class VariableExamples extends OpMode {
    @Override
    public void init() {
        int teamNumber = 32647;
        double motorSpeed = 0.55;
        boolean clawClosed = true;
        String teamName = "RoboSpark";
        int motorAngle = 60;

        telemetry.addData("Team Number", teamNumber);
        telemetry.addData("Motor Speed", motorSpeed);
        telemetry.addData("Claw Closed", clawClosed);
        telemetry.addData("Name", teamName);
        telemetry.addData("Motor Angle", motorAngle);

    }

    @Override
    public void loop() {

    }
}
