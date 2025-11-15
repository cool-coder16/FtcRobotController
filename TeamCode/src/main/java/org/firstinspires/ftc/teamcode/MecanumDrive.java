package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.mechanisms.MainBench;
import org.firstinspires.ftc.teamcode.mechanisms.MecanumBenchServo;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

@TeleOp
public class MecanumDrive extends OpMode {
    MecanumBenchServo drive = new MecanumBenchServo();
    AprilTagWebcam aprilTag = new AprilTagWebcam();
    double forward, strafe, rotate, power, goalAngle;
    int team = -1; // Blue is 0, Red is 1
    boolean servosOn = false;
    AprilTagDetection goal;

    @Override
    public void init() {
        drive.init(hardwareMap);
        aprilTag.init(hardwareMap, telemetry);
        power = 0.55;
    }

    @Override
    public void init_loop() {
        if (gamepad1.right_bumper){
            team = 0;
        } else if (gamepad1.left_bumper){
            team = 1;
        }

        telemetry.addData("Team", "Right Bumper for Blue, Left Bumper for Red");
        telemetry.addData("0", "blue");
        telemetry.addData("1", "red");
        telemetry.addData("-1", "Choose");
        telemetry.addData("Team", team);
    }

    @Override
    public void loop() {
        if (team == -1){
            throw new RuntimeException("Choose Team");
        } else if (team == 0){
            goalAngle = 45;
            goal = aprilTag.getTagById(20);
        } else if (team == 1){
            goalAngle = -45;
            goal = aprilTag.getTagById(24);
        }
        forward = -gamepad1.left_stick_y;
        rotate = gamepad1.right_stick_x;
        strafe = gamepad1.left_stick_x;

        drive.drive(forward, strafe, rotate);
        drive.setFlywheel(power);

        aprilTag.updateWebcam();

        if (gamepad1.b){
            if (!servosOn) {
                drive.shootBall();
                servosOn = true;
            }
        }

        if (gamepad1.right_trigger > 0.5){
            if (servosOn){
                drive.stopShoot();
                servosOn = false;
            }
        }

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

//        if (gamepad1.left_trigger > 0.5){
//            drive.turnToDirection(goalAngle);
//        }

        telemetry.addData("Facing", drive.getDirectionFacing(AngleUnit.DEGREES));
        telemetry.addData("Rotate", rotate);
        telemetry.addData("Servo Speed", drive.getServoPosition());
        telemetry.addData("April Tag", goal);
        aprilTag.displayWebcamTelemetry(goal);
    }
}
