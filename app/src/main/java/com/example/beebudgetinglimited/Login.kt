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
import com.example.palworld.UserviewModel
import kotlinx.coroutines.launch

class Login : AppCompatActivity() {

    private lateinit var viewModel: UserviewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        viewModel = ViewModelProvider(this)[UserviewModel::class.java]
        findViewById<Button>(R.id.btnSigned).setOnClickListener {
            val username =findViewById<EditText>(R.id.etSEmailAddress).text.toString()
            val password = findViewById<EditText>(R.id.etSPassword).text.toString()

            lifecycleScope.launch {
                val user = viewModel.loginUser(username,password)

                if(user != null){
                    val intent = Intent(this@Login, HomeScreen::class.java)
                    intent.putExtra("FullName",user.fullname)
                    startActivity(intent)
                    finish()

                }else {
                    Toast.makeText(this@Login, "invalid credinations", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}