package com.example.chatingapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.chatingapp.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    private lateinit var loginbinding: ActivityLoginBinding
    private lateinit var mAuth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginbinding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginbinding.root)

        mAuth = FirebaseAuth.getInstance()

        loginbinding.apply {
            sinbutton.setOnClickListener{
                startActivity(Intent(this@Login,Signup::class.java))
            }
            loginbutton.setOnClickListener{
                val phone=editTextPhone.text.toString()
                val pass=editTextPassword.text.toString()
                login(phone,pass)
            }
        }
        
    }

    private fun login(phone: String, pass: String) {

        mAuth.signInWithEmailAndPassword(phone, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this@Login,MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this@Login, "Use does not exist", Toast.LENGTH_SHORT).show()
                }
            }
    }
}