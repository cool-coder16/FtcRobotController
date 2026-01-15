package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Disabled
public class TestServos {
    private CRServo push1, push2;

    public void init(HardwareMap hwMap){
        push1 = hwMap.get(CRServo.class, "push1");
        push2 = hwMap.get(CRServo.class, "push2");
    }

    public void runServos(double power){
        push1.setPower(-power);
        push2.setPower(power);
    }
}
