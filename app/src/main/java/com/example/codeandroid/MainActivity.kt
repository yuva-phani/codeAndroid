package com.example.codeandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    public lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()

        sign_up.setOnClickListener {
            signUpUser()
        }

        login.setOnClickListener{
           // startActivity(Intent(this,LoginActivity::class.java))
            //finish()
            signInUser()
           // startActivity(Intent(this,RecognizeText::class.java))
           // startActivity(Intent(this,DashboardActivity::class.java))
            //finish()

        }
        forgot_passwrd.setOnClickListener{
            startActivity(Intent(this,PasswordResetActivity::class.java))
        }
       
    }

     private fun signUpUser() {
        if (username.text.toString().isEmpty()) {
            username.error = "Please enter email"
            username.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(username.text.toString()).matches()) {
            username.error = "Please enter valid email"
            username.requestFocus()
            return
        }

        if (password.text.toString().isEmpty()) {
            password.error = "Please enter password"
            password.requestFocus()
            return
        }

        auth.createUserWithEmailAndPassword(username.text.toString(), password.text.toString())
            .addOnCompleteListener(this) { task ->
                val user = FirebaseAuth.getInstance().currentUser
                if (task.isSuccessful) {
                    user?.sendEmailVerification()
                        ?.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d(">> ", "Email sent.")
                            }
                        }
                    Toast.makeText(baseContext, "verification mail sent to "+user!!.email,Toast.LENGTH_SHORT).show()
                    //startActivity(Intent(this,LoginActivity::class.java))
                    //finish()
                } else {
                    Toast.makeText(baseContext, "Sign Up failed. Try again after some time.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }



    private fun updateUI(currentUser: FirebaseUser?) {
        if(currentUser?.isEmailVerified!!) {
            val intent = Intent(this, DashboardActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }
        else{
            Toast.makeText(baseContext, "please validate your email !!!!!",
                Toast.LENGTH_LONG).show()

        }
    }


    private fun signInUser() {
        if (username.text.toString().isEmpty()) {
            username.error = "Please enter email"
            username.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(username.text.toString()).matches()) {
            username.error = "Please enter valid email"
            username.requestFocus()
            return
        }

        if (password.text.toString().isEmpty()) {
            password.error = "Please enter password"
            password.requestFocus()
            return
        }
        auth.signInWithEmailAndPassword(username.text.toString(), password.text.toString())
            .addOnCompleteListener(this) { task ->
                println(">>>>>>>>>>>>>>>>>>>>> "+username.text.toString())
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(">> ", "signInWithEmail:success")
                    // val user1 = auth.currentUser
                    val user = FirebaseAuth.getInstance().currentUser
                    user?.let {
                        // Name, email address, and profile photo Url
                        val name = user.displayName
                        val email = user.email
                        val photoUrl = user.photoUrl

                        // Check if user's email is verified
                        val emailVerified = user.isEmailVerified

                        // The user's ID, unique to the Firebase project. Do NOT use this value to
                        // authenticate with your backend server, if you have one. Use
                        // FirebaseUser.getToken() instead.
                        val uid = user.uid
                    }
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("<< ", "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    // updateUI(null)
                }

                // ...
            }


    }





}
