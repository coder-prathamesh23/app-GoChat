package com.example.gochat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {

    private lateinit var edtUserName: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnSignup: Button

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_sign_up)
        mAuth=FirebaseAuth.getInstance()
        edtUserName=findViewById(R.id.ETUserName)
        edtEmail=findViewById(R.id.ETEmail)
        edtPassword=findViewById(R.id.ETPassword)
        btnSignup=findViewById(R.id.btnSignup)

        btnSignup.setOnClickListener{
            val name=edtUserName.text.toString()
            val email=edtEmail.text.toString()
            val password=edtPassword.text.toString()

            signUP(name,email,password)
        }
    }
    private fun signUP(name:String,email:String,password:String)
    {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful)
                {
                    addUserToDatabase(name,email,mAuth.currentUser?.uid!!)
                    val intent=Intent(this@SignUpActivity,MainActivity::class.java)
                    finish()
                    startActivity(intent)
                }
                else
                {
                    Toast.makeText(this@SignUpActivity,"Some Error Occurred",Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun addUserToDatabase(name:String,email:String,uid:String)
    {
        mDbRef= FirebaseDatabase.getInstance().getReference()
        mDbRef.child("user").child(uid).setValue(User(name, email, uid))

    }
}