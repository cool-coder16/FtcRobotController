/// This is the drive code. It gives functions to control the various parts of the robot, such as
/// * The flywheel
/// * The intake
/// * The pushers
/// * Speed
/// * Mecanum wheels
/// This code gets combined with the main code, which uses the functions with the gamepad.

package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class MecanumBenchServo {
    public DcMotor front_right_motor, front_left_motor, back_right_motor, back_left_motor, intake_motor, flywheel, turret; // Initializes the motors
    public CRServo upPusher; // Initializes the servo
    public double intakeSpeed = 1; // Sets the variable speed of the intake to 1
    public double turretSpeed = 0.5; //CHANGEABLE: Change this to change the speed that the turret moves - Needs calibration

    public void init(HardwareMap hwMap){
        front_right_motor = hwMap.get(DcMotor.class, "frontright"); // Assigns motor to the one in the configuration called "frontright"
        front_left_motor = hwMap.get(DcMotor.class, "frontleft"); // Assigns motor to the one in the configuration called "frontleft"
        back_right_motor = hwMap.get(DcMotor.class, "backright"); // Assigns motor to the one in the configuration called "backright"
        back_left_motor = hwMap.get(DcMotor.class, "backleft"); // Assigns motor to the one in the configuration called "backleft"
        flywheel = hwMap.get(DcMotor.class, "flywheel"); // Assigns motor to the one in the configuration called "flywheel"
        intake_motor = hwMap.get(DcMotor.class, "intake"); // Assigns motor to the one in the configuration called "intake"
        upPusher = hwMap.get(CRServo.class, "upPush"); // Assigns servo to the one in the configuration called "upPush"
        turret = hwMap.get(DcMotor.class, "turret"); // Assigns motor to the one in the configuration called "turret"

        front_right_motor.setDirection(DcMotorSimple.Direction.REVERSE); // Auto-reverses the drive motor
        back_left_motor.setDirection(DcMotorSimple.Direction.REVERSE); // Auto-reverses the drive motor
        turret.setDirection(DcMotorSimple.Direction.REVERSE); //CHANGEABLE: IF THE TURRET MOVES BACKWARD, add // to the start


        front_left_motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER); // Sets the mode, required for it to work. It means that it has an encoder.
        back_left_motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        front_right_motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        back_right_motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        intake_motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        flywheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        turret.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        intake_motor.setPower(intakeSpeed); // Starts the intake motor to the variable intakeSpeed
    }

    public void drive(double forward, double strafe, double rotate){
        double frontLeftPower = forward + strafe + rotate; // Equations to figure out individual motor speeds: Thanks to Brogan M. Pratt:
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

    public void turretClockwise(){
        turret.setPower(turretSpeed); // Moves the turret
    }

    public void turretCounterClockwise(){
        turret.setPower(-turretSpeed); // Opposite direction
    }

    public void stopTurret(){
        turret.setPower(0); // stops it
    }

    public void pushBallUp(){
        upPusher.setPower(1.0); // Push the ball up, forever until stopped
    }

    public void stopBallUp(){
        upPusher.setPower(0.0); // stops the above
    }

    public void pushBallDown(){
        upPusher.setPower(-1.0); // pushes ball down
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

    public void setFlywheel(double power){
        flywheel.setPower(power); // Allows you to change the flywheel power.
    }
}
