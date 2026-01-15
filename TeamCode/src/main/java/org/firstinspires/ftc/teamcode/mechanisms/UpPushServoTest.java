package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Disabled
@Deprecated
public class UpPushServoTest {
    public CRServo upPusher;

    public void init(HardwareMap hwMap){
        upPusher = hwMap.get(CRServo.class, "upPush");

    }

    public void runForward(){
        upPusher.setPower(1);
    }

    public void runBackward(){
        upPusher.setPower(-1);
    }

    public void stopServo(){
        upPusher.setPower(0);
    }
}
