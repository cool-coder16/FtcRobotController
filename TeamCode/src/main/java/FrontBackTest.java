import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.mechanisms.FinalBench;

@Autonomous
public class FrontBackTest extends LinearOpMode {
    FinalBench drive = new FinalBench();

    public void runOpMode(){
        drive.init(hardwareMap, 1);
        waitForStart();
        while (opModeIsActive()){
            if (gamepad1.xWasPressed()){
                drive.drive(0.2, 0, 0);
                sleep(2000);
                drive.drive(0, 0, 0);
            }

            if (gamepad1.yWasPressed()){
                drive.drive(-0.2, 0, 0);
                sleep(2000);
                drive.drive(0, 0, 0);
            }

            telemetry.addLine("X for FORWARD, Y for BACK");
            telemetry.update();
        }
    }
}
