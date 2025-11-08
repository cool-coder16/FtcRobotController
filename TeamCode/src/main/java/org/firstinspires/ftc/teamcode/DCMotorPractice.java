package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.TestMotor;

@Disabled
@TeleOp
public class DCMotorPractice extends OpMode {
    TestMotor bench = new TestMotor();

    @Override
    public void init() {
        bench.init(hardwareMap);
    }

    @Override
    public void loop() {
        bench.setMotorSpeed(0.5);
        telemetry.addData("Motor Revs", bench.getMotorRevs());
    }
}
