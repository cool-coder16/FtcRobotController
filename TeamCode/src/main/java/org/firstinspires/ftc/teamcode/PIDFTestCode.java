package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

@Disabled
@TeleOp
public class PIDFTestCode extends LinearOpMode {
    DcMotorEx dcMotor;
    DcMotor servo;
    DcMotor intake;

    public double P = 110;
    public double F = 13.05;

    public double highVelocity = 2000;
    public double lowVelocity = 1000;
    public double curTargetVelocity = highVelocity;

    public double[] stepSizes = {10.0, 1.0, 0.1, 0.01, 0.001, 0.0001};
    int stepIndex = 0;

    public void runOpMode(){
        dcMotor = hardwareMap.get(DcMotorEx.class, "flywheel");
        dcMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        dcMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        servo = hardwareMap.get(DcMotor.class, "upPush");
        servo.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        servo.setDirection(DcMotorSimple.Direction.REVERSE);

        intake = hardwareMap.get(DcMotor.class, "intake");
        intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        PIDFCoefficients pidfCoefficients = new PIDFCoefficients(P, 0, 0, F);
        dcMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);
        waitForStart();
        while (opModeIsActive()){
            if (gamepad1.yWasPressed()){
                if (curTargetVelocity == highVelocity){
                    curTargetVelocity = lowVelocity;
                } else {
                    curTargetVelocity = highVelocity;
                }
            }

            if (gamepad1.bWasPressed()){
                stepIndex = (stepIndex + 1) % stepSizes.length;
            }

            if (gamepad1.a){
                servo.setPower(1);
            } else {
                servo.setPower(0);
            }

            if (gamepad1.right_bumper){
                intake.setPower(1.0);
            }

            if (gamepad1.left_bumper){
                intake.setPower(0.0);
            }

            if (gamepad1.dpadLeftWasPressed()){
                F -= stepSizes[stepIndex];
            }

            if (gamepad1.dpadRightWasPressed()){
                F += stepSizes[stepIndex];
            }

            if (gamepad1.dpadDownWasPressed()){
                P -= stepSizes[stepIndex];
            }

            if (gamepad1.dpadUpWasPressed()){
                P += stepSizes[stepIndex];
            }

            pidfCoefficients = new PIDFCoefficients(P, 0, 0, F);
            dcMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);
            dcMotor.setVelocity(curTargetVelocity);

            double curVelocity = dcMotor.getVelocity();
            double error = curTargetVelocity - curVelocity;

            telemetry.addData("Target Velocity", "%.2f (Y Button)", curTargetVelocity);
            telemetry.addData("Current Velocity", "%.2f", curVelocity);
            telemetry.addData("Error", "%.2f", error);
            telemetry.addLine("----------");
            telemetry.addData("P", "%.4f (D-Pad U/D)", P);
            telemetry.addData("F", "%.4f (D-Pad L/R)", F);
            telemetry.addData("Step Size", "%.4f (B Button)", stepSizes[stepIndex]);
            telemetry.update();
        }
    }
}
