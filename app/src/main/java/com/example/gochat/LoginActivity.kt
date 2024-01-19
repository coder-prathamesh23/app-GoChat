package com.example.gochat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var mAuth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_login)
        mAuth=FirebaseAuth.getInstance()
        edtEmail=findViewById(R.id.ETEmail)
        edtPassword=findViewById(R.id.ETPassword)
        btnLogin=findViewById(R.id.btnLogin)

        val btnSignup=findViewById<Button>(R.id.btnSignup)
        btnSignup.setOnClickListener{
            val intent=Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }
        btnLogin.setOnClickListener{
            val email=edtEmail.text.toString()
            val password=edtPassword.text.toString()

            login(email,password)
        }

    }
    private fun login(email:String,password:String)
    {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful)
                {
                    val intent=Intent(this@LoginActivity,MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else
                {
                    Toast.makeText(this@LoginActivity,"Check Email and Password And Try Again",Toast.LENGTH_SHORT).show()
                }
            }
    }
}