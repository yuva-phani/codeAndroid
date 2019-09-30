package com.example.codeandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_password_reset.*

class PasswordResetActivity : AppCompatActivity() {
    val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_reset)
        pass_reset.setOnClickListener{
            resetPassword()
            startActivity(Intent(this,MainActivity::class.java))
        }
    }


    private fun resetPassword(){
        if (reset_email.text.toString().isEmpty()) {
            reset_email.error = "Please enter email"
            reset_email.requestFocus()
            return
        }

        auth.sendPasswordResetEmail(reset_email.text.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("", "Email sent.")
                    Toast.makeText(baseContext, "Reset link sent to "+reset_email.text.toString(), Toast.LENGTH_SHORT).show()

                }
            }
        
    }
}
