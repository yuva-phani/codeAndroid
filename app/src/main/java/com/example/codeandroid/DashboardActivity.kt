package com.example.codeandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        text_recognition.setOnClickListener{
            startActivity(Intent(this,RecognizeText::class.java))
        }
        detect_faces.setOnClickListener{
            startActivity(Intent(this,DetectFaces::class.java))
        }

        label_images.setOnClickListener{
            startActivity(Intent(this,DetectionTrackingObjects::class.java))
        }

    }
}
