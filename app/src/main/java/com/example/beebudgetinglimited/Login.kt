package com.example.beebudgetinglimited

import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class Login : AppCompatActivity() {

    private lateinit var viewModel: UserViewModel

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

        findViewById<Button>(R.id.btnLogin).setOnClickListener {
            //takes the users input
            val username =findViewById<EditText>(R.id.etSEmailAddress).text.toString()
            val password = findViewById<EditText>(R.id.etSPassword).text.toString()
            //calls the viewmodel method to check details via a coroutine
            lifecycleScope.launch {
                val user = viewModel.loginUser(username,password)
                //procedures to follow depending on what result you get
                if(user != null){
                    val intent = Intent(this@Login, HomeScreen::class.java)
                    intent.putExtra("FullName",user.fullname)
                    startActivity(intent)
                    finish()

                } else {
                    Toast.makeText(this@Login, "Invalid credentials", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}