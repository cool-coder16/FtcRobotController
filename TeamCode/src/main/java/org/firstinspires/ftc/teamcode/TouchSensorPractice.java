package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.Test_Touch_Sensor;

@Disabled
@TeleOp
public class TouchSensorPractice extends OpMode {
    Test_Touch_Sensor bench = new Test_Touch_Sensor();

    @Override
    public void init() {
        bench.init(hardwareMap);
    }

    @Override
    public void loop() {
        telemetry.addData("Touch Sensor State", bench.getTouchSensorState());
    }
}
