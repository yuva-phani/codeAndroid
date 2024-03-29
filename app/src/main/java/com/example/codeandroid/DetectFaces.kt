package com.example.codeandroid

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.face.FirebaseVisionFace
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions
import kotlinx.android.synthetic.main.activity_detect_faces.*

class DetectFaces : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detect_faces)
        btn_detect.setOnClickListener {

            camera_view.captureImage { cameraKitView, byteArray ->

                camera_view.onStop()

             //   alertDialog.show()

                var bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray?.size ?: 0)

                bitmap = Bitmap.createScaledBitmap(bitmap, camera_view?.width ?: 0, camera_view?.height ?: 0, false)

                runDetector(bitmap)

            }

            //graphic_overlay.clear()

        }
    }

    override fun onResume() {
         super.onResume()
          camera_view.onResume()

    }

    override fun onPause() {
          super.onPause()
           camera_view.onPause()

    }

    override fun onStart() {
            super.onStart()
            camera_view.onStart()

    }

    override fun onStop() {
           super.onStop()
            camera_view.onStop()

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        camera_view.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }

    private fun runDetector(bitmap: Bitmap) {

        val image = FirebaseVisionImage.fromBitmap(bitmap)

        val options = FirebaseVisionFaceDetectorOptions.Builder()

            .build()

        val detector = FirebaseVision.getInstance()

            .getVisionFaceDetector(options)

        detector.detectInImage(image)

            .addOnSuccessListener { faces ->

               // processFaceResult(faces)

            }.addOnFailureListener {

                it.printStackTrace()

            }

    }

    /*private fun processFaceResult(faces: MutableList<FirebaseVisionFace>) {

        faces.forEach {

            val bounds = it.boundingBox

            val rectOverLay = RectOverlay(graphic_overlay, bounds)

            graphic_overlay.add(rectOverLay)

        }

        alertDialog.dismiss()

    }
*/
}
