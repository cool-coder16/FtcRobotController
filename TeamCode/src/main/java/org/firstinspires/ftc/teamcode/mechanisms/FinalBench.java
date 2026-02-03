/// This is the drive code. It gives functions to control the various parts of the robot, such as
/// * The flywheel
/// * The intake
/// * The pushers
/// * Speed
/// * Mecanum wheels
/// This code gets combined with the main code, which uses the functions with the gamepad.

package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.opencv.core.Mat;

public class FinalBench {
    public DcMotorEx front_right_motor, front_left_motor, back_right_motor, back_left_motor, flywheel, turret;
    public DcMotorEx intake_motor, upPush; // Initializes the motors

    public Limelight3A limelight;

    public IMU imu;
    public double intakeSpeed = 1; // Sets the variable speed of the intake to 1
    public double turretSpeed = 0.5; //CHANGEABLE: Change this to change the speed that the turret moves - Needs calibration

    public void init(HardwareMap hwMap, int pipeline){
        // MOTOR + SERVO Initialization
        front_right_motor = hwMap.get(DcMotorEx.class, "frontright"); // Assigns motor to the one in the configuration called "frontright"
        front_left_motor = hwMap.get(DcMotorEx.class, "frontleft"); // Assigns motor to the one in the configuration called "frontleft"
        back_right_motor = hwMap.get(DcMotorEx.class, "backright"); // Assigns motor to the one in the configuration called "backright"
        back_left_motor = hwMap.get(DcMotorEx.class, "backleft"); // Assigns motor to the one in the configuration called "backleft"
        flywheel = hwMap.get(DcMotorEx.class, "flywheel"); // Assigns motor to the one in the configuration called "flywheel"
        intake_motor = hwMap.get(DcMotorEx.class, "intake"); // Assigns motor to the one in the configuration called "intake"
        upPush = hwMap.get(DcMotorEx.class, "upPush"); // Assigns motor to the one in the configuration called "upPush"
        turret = hwMap.get(DcMotorEx.class, "turret"); // Assigns motor to the one in the configuration called "turret"
        imu = hwMap.get(IMU.class, "IMU");

        // Reverses
        front_right_motor.setDirection(DcMotorSimple.Direction.REVERSE); // Auto-reverses the drive motor
        back_left_motor.setDirection(DcMotorSimple.Direction.REVERSE); // Auto-reverses the drive motor
        flywheel.setDirection(DcMotorSimple.Direction.REVERSE);
        upPush.setDirection(DcMotorSimple.Direction.REVERSE);

        // Motor SetModes
        front_left_motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER); // Sets the mode, required for it to work. It means that it has an encoder.
        back_left_motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        front_right_motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        back_right_motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intake_motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        flywheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        turret.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        turret.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        upPush.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);



        // Motor ZeroModes
        front_left_motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        front_right_motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        back_left_motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        back_right_motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        turret.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //PF (PIDF)
        PIDFCoefficients pidfCoefficients = new PIDFCoefficients(89, 0, 0, 12.35);
        flywheel.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);

        //Start Intake
//        intake_motor.setPower(intakeSpeed); // Starts the intake motor to the variable intakeSpeed

        // LIMELIGHT
        limelight = hwMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(pipeline);
        limelight.start();

        // IMU
        IMU.Parameters parameters = new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                        RevHubOrientationOnRobot.UsbFacingDirection.FORWARD
                )
        );

        imu.initialize(parameters);
        imu.resetYaw();
    }

    public void setDriveMotors(double forward, double strafe, double rotate){
        double frontLeftPower = forward + strafe + rotate; // Equations to figure out individual motor speeds: Thanks to Brogan M. Pratt's Mecanum Drive video
        double backLeftPower = forward - strafe + rotate;
        double frontRightPower = forward - strafe - rotate;
        double backRightPower = forward + strafe - rotate;

        double maxPower = 1.0; // Used for limiting the power
        double maxSpeed = 1.0; // Used to scale them all to the same

        maxPower = Math.max(maxPower, Math.abs(frontLeftPower)); // Limits
        maxPower = Math.max(maxPower, Math.abs(frontRightPower)); // Limits
        maxPower = Math.max(maxPower, Math.abs(backLeftPower)); // Limits
        maxPower = Math.max(maxPower, Math.abs(backRightPower)); // Limits

        front_left_motor.setPower(maxSpeed * (frontLeftPower/maxPower)); // Scales
        back_left_motor.setPower(maxSpeed * (backLeftPower/maxPower)); // Scales
        front_right_motor.setPower(maxSpeed * (frontRightPower/maxPower)); // Scales
        back_right_motor.setPower(maxSpeed * (backRightPower/maxPower)); // Scales
    }

    public void turretClockwise(double speed){
        turret.setPower(speed); // Moves the turret
    }

    public void turretCounterClockwise(double speed){
        turret.setPower(-speed); // Opposite direction
    }

    public void stopTurret(){
        turret.setPower(0); // stops it
    }

    public void pushBallUpStrong(){
        upPush.setPower(0.5); // Push the ball up, forever until stopped
    }

    public void pushBallUpWeak(){
        upPush.setPower(0.5);
    }

    public void setUpPush(double power){
        upPush.setPower(power);
    }

    public void stopBallUp(){
        upPush.setPower(0.0); // stops the above
    }

    public void pushBallDown(){
        upPush.setPower(-0.8); // pushes ball down
    }

    public void intake(){
        intake_motor.setPower(intakeSpeed); // For the dpad controls, gamepad 1
    }

    public void outtake(){
        intake_motor.setPower(-intakeSpeed); // For the dpad controls, gamepad 1
    }

    public void stoptake(){
        intake_motor.setPower(0); // For the dpad controls, gamepad 1
    }

    public void setFlywheel(double velocity){
        flywheel.setVelocity(velocity); // Allows you to change the flywheel power.
    }

    public double calculatePower(double ta){
        return 91.14247 * Math.pow(ta, 4) - 587.4294 * Math.pow(ta , 3) + 1310.65542 * Math.pow(ta, 2) - 1298.56367 * ta + 1879.80641;
    }
}
