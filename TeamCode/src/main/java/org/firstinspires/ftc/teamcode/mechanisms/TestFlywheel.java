package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Disabled
public class TestFlywheel {
    public DcMotor flywheel;

    public void init(HardwareMap hwMap){
        flywheel = hwMap.get(DcMotor.class, "flywheel");

        flywheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void forwards(double speed){
        flywheel.setPower(Math.abs(speed));
    }

    public void backwards(double speed){
        flywheel.setPower(-1 * Math.abs(speed));
    }

    public void stopWheel(){
        flywheel.setPower(0);
    }
}
