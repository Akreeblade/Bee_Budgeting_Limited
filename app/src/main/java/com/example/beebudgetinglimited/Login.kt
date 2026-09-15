package com.example.beebudgetinglimited

import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class Login : AppCompatActivity() {

    private lateinit var viewModel: UserViewModel

    private lateinit var tvRedirectSignUp: TextView

    lateinit var etUsername: EditText

    private lateinit var etPass: EditText

    lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //instance of userviewmodel
        viewModel = ViewModelProvider(this)[UserViewModel::class.java]

        tvRedirectSignUp = findViewById(R.id.tvRedirectSignUp)
        btnLogin = findViewById(R.id.btnLogin)
        etUsername = findViewById(R.id.etUsername)
        etPass = findViewById(R.id.etPassword)

        //redirects to sign up screen
        tvRedirectSignUp.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
            // using finish() to end the activity
            finish()
        }

        //findViewById<Button>(R.id.btnLogin).setOnClickListener {
        //    //takes the users input
        //    val username =findViewById<EditText>(R.id.etUsername).text.toString()
        //    val password = findViewById<EditText>(R.id.etPassword).text.toString()
        //    //calls the viewmodel method to check details via a coroutine
        //    lifecycleScope.launch {
        //        val user = viewModel.loginUser(username,password)
        //        //procedures to follow depending on what result you get
        //        if(user != null){
        //            val intent = Intent(this@Login, HomeScreen::class.java)
        //            intent.putExtra("FullName",user.fullName)
        //            startActivity(intent)
        //            //finish()
//
        //        } else {
        //            Toast.makeText(this@Login, "Invalid credentials", Toast.LENGTH_SHORT).show()
        //        }
        //    }
        //}
        btnLogin.setOnClickListener {

            val username = etUsername.text.toString()
            val password = etPass.text.toString()

            lifecycleScope.launch {

                val usernameExists = viewModel.isUsernameTaken(username)

                Toast.makeText(
                    this@Login,
                    "Username exists: $usernameExists",
                    Toast.LENGTH_LONG
                ).show()

                if (usernameExists) {

                    val user = viewModel.loginUser(username, password)

                    if (user != null) {
                        val intent = Intent(this@Login, HomeScreen::class.java)
                        intent.putExtra("FullName", user.fullName)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            this@Login,
                            "Username exists, but password is wrong",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                } else {

                    Toast.makeText(
                        this@Login,
                        "Username does not exist: $username",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}