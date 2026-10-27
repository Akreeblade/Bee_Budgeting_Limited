package com.example.beebudgetinglimited

import android.content.Intent
import androidx.activity.enableEdgeToEdge
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton

class BarryCareScreen : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            setContentView(R.layout.activity_barry_care_screen)

            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        findViewById<ImageButton>(R.id.Homebtn).setOnClickListener {
            val intent = Intent(this@BarryCareScreen, HomeScreen::class.java)
            startActivity(intent)
            finish()
        }
        findViewById<ImageButton>(R.id.Barrybtn).setOnClickListener {
            val intent = Intent(this@BarryCareScreen, BarryCareScreen::class.java)
            startActivity(intent)
            finish()
        }
        findViewById<ImageButton>(R.id.BarryDressUp).setOnClickListener {
            val intent = Intent(this@BarryCareScreen, BarryDressUp::class.java)
            startActivity(intent)
            finish()
        }
        findViewById<ImageButton>(R.id.ProfileBtn).setOnClickListener {
            val intent = Intent(this@BarryCareScreen, ProfileScreen::class.java)
            startActivity(intent)
            finish()
        }
        findViewById<ImageButton>(R.id.Settingsbtn).setOnClickListener {
            val intent = Intent(this@BarryCareScreen, SettingsScreen::class.java)
            startActivity(intent)
            finish()
        }
        findViewById<ImageButton>(R.id.Analyticsbtn).setOnClickListener {
            val intent = Intent(this@BarryCareScreen, Analytics::class.java)
            startActivity(intent)
            finish()
        }
        findViewById<MaterialButton>(R.id.Addbtn).setOnClickListener {
            val intent = Intent(this@BarryCareScreen, LoggingScreen::class.java)
            startActivity(intent)
            finish()
        }
            //Barry outfit code

            // finds the image view
            val barryImageView = findViewById<ImageView>(R.id.BarryImage)
            // Checks if outfit img was passed
            val outfitResId = intent.getIntExtra("OUTFIT_RES_ID", -1)

            // display outfit if selected
            if (outfitResId != -1) {
                barryImageView.setImageResource(outfitResId)
            }
    }
}