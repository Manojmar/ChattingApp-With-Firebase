package com.example.chatingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.chatingapp.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Signup : AppCompatActivity() {

    private lateinit var signupBinding: ActivitySignupBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signupBinding= ActivitySignupBinding.inflate(layoutInflater)
        setContentView(signupBinding.root)

        mAuth = FirebaseAuth.getInstance()

        signupBinding.apply {
            signupbutton.setOnClickListener {
                val name=editTextname.text.toString()
                val phone=editTextPhone.text.toString()
                val passs=editTextPassword.text.toString()

                signup(name,phone,passs)
            }
        }
    }

    private fun signup(name:String,phone: String, passs: String) {
        mAuth.createUserWithEmailAndPassword(phone,passs)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    addUserToDatabase(name,phone,mAuth.currentUser?.uid!!)
                    startActivity(Intent(this@Signup,MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this@Signup, "Some Error occurred", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addUserToDatabase(name: String, phone: String, uid: String) {
        mDbRef=FirebaseDatabase.getInstance().getReference()
            mDbRef.child("user").child(uid).setValue(User(name,phone,uid))
        }
    }

