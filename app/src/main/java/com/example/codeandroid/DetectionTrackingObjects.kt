package com.example.codeandroid


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata
import com.google.firebase.ml.vision.objects.FirebaseVisionObject
import com.google.firebase.ml.vision.objects.FirebaseVisionObjectDetectorOptions
import com.otaliastudios.cameraview.frame.Frame

import kotlinx.android.synthetic.main.activity_detection_tracking_objects.*


class DetectionTrackingObjects : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detection_tracking_objects)
        
        cameraView.setLifecycleOwner(this)

        cameraView.addFrameProcessor {
            extractDataFromFrame(it) { result ->
                tvDetectedItem.text = result
            }
           
        }

    }


    private fun extractDataFromFrame(frame: Frame, callback: (String) -> Unit) {

        val options = FirebaseVisionObjectDetectorOptions.Builder()
            .setDetectorMode(FirebaseVisionObjectDetectorOptions.STREAM_MODE)
            .enableMultipleObjects()  //Add this if you want to detect multiple objects at once
            .enableClassification()  // Add this if you want to classify the detected objects into categories
            .build()

        val objectDetector = FirebaseVision.getInstance().getOnDeviceObjectDetector(options)

        objectDetector.processImage(getVisionImageFromFrame(frame))
            .addOnSuccessListener {
                it.forEach { obj ->
                    val id = obj.trackingId       // A number that identifies the object across images
                    val bounds = obj.boundingBox  // The object's position in the image

                    // If classification was enabled:
                    val category = obj.classificationCategory
                    val confidence = obj.classificationConfidence
                    Log.e("TAG","Check >>>")
                }
                           }
            .addOnFailureListener {
                callback("Unable to detect an object")
            }
    }

    

    private fun getVisionImageFromFrame(frame : Frame) : FirebaseVisionImage {
        //ByteArray for the captured frame
        val data = frame.data

        //Metadata that gives more information on the image that is to be converted to FirebaseVisionImage
        val imageMetaData = FirebaseVisionImageMetadata.Builder()
            .setFormat(FirebaseVisionImageMetadata.IMAGE_FORMAT_NV21)
            .setRotation(FirebaseVisionImageMetadata.ROTATION_90)
            .setHeight(frame.size.height)
            .setWidth(frame.size.width)
            .build()

        val image = FirebaseVisionImage.fromByteArray(data, imageMetaData)

        return image
    }

}


