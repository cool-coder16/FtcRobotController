package org.firstinspires.ftc.teamcode;

import android.util.Size;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.ArrayList;
import java.util.List;

public class AprilTagWebcam{
    private AprilTagProcessor aprilTagProcessor;
    private VisionPortal visionPortal;
    private Telemetry telemetry;
    private List<AprilTagDetection> detectedTags = new ArrayList<>();

    public void init(HardwareMap hwMap, Telemetry telemetry){
        this.telemetry = telemetry;

        aprilTagProcessor = new AprilTagProcessor.Builder()
                .setDrawTagID(true)
                .setDrawTagOutline(true)
                .setDrawAxes(true)
                .setDrawCubeProjection(true)
                .setOutputUnits(DistanceUnit.CM, AngleUnit.DEGREES)
                .build();

        VisionPortal.Builder builder = new VisionPortal.Builder()
                .setCamera(hwMap.get(WebcamName.class, "Webcam 1"))
                .setCameraResolution(new Size(640, 480))
                .addProcessor(aprilTagProcessor)
                .enableLiveView(true)
                .setLiveViewContainerId(VisionPortal.DEFAULT_VIEW_CONTAINER_ID);

        visionPortal = builder.build();
    }


    public void updateWebcam(){
        detectedTags = aprilTagProcessor.getDetections();
    }

    public List<AprilTagDetection> getDetectedTags(){
        return detectedTags;
    }

    public AprilTagDetection getTagById(int id){
        for (AprilTagDetection detection : detectedTags){
            if (detection.id == id){
                return detection;
            }
        }

        return null;
    }

    public void stop(){
        if (visionPortal != null){
            visionPortal.close();
        }
    }

    public void displayWebcamTelemetry(AprilTagDetection detectedId){
        if (detectedId == null){
            telemetry.addData("April Tag", "None");
            return;
        }

        if (detectedId.metadata != null) {
            telemetry.addLine(String.format("\n==== (ID %d) %s", detectedId.id, detectedId.metadata.name));
            telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)", detectedId.ftcPose.x, detectedId.ftcPose.y, detectedId.ftcPose.z));
            telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)", detectedId.ftcPose.pitch, detectedId.ftcPose.roll, detectedId.ftcPose.yaw));
            telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (inch, deg, deg)", detectedId.ftcPose.range, detectedId.ftcPose.bearing, detectedId.ftcPose.elevation));
        } else {
            telemetry.addLine(String.format("\n==== (ID %d) Unknown", detectedId.id));
            telemetry.addLine(String.format("Center %6.0f %6.0f   (pixels)", detectedId.center.x, detectedId.center.y));
        }
    }

    public double getWebcamYaw(AprilTagDetection detectedId){
        if (detectedId == null){return 0.0; }
        if (detectedId.metadata != null){
            return detectedId.ftcPose.yaw;
        } else {
            return 0.0;
        }
    }

    public double getWebcamDistance(AprilTagDetection detectedId){
        if (detectedId == null){return 40.0; }
        if (detectedId.metadata != null){
            return detectedId.ftcPose.y;
        } else {
            return 40.0;
        }
    }

    public double getWebcamStrafe(AprilTagDetection detectedId){
        if (detectedId == null){return 0.0; }
        if (detectedId.metadata != null){
            return detectedId.ftcPose.x;
        } else {
            return 0.0;
        }
    }
    }
