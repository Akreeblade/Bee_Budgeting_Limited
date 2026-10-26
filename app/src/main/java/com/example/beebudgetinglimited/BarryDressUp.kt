package com.example.beebudgetinglimited

import android.content.Intent
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton

class BarryDressUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_barry_dress_up)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        findViewById<ImageButton>(R.id.Barrybtn).setOnClickListener {
            val intent = Intent(this@BarryDressUp, HomeScreen::class.java)
            startActivity(intent)
            finish()
        }
        findViewById<ImageButton>(R.id.Barrybtn).setOnClickListener {
            val intent = Intent(this@BarryDressUp, BarryCareScreen::class.java)
            startActivity(intent)
            finish()
        }
        findViewById<ImageButton>(R.id.ProfileBtn).setOnClickListener {
            val intent = Intent(this@BarryDressUp, ProfileScreen::class.java)
            startActivity(intent)
            finish()
        }
        findViewById<ImageButton>(R.id.Settingsbtn).setOnClickListener {
            val intent = Intent(this@BarryDressUp, SettingsScreen::class.java)
            startActivity(intent)
            finish()
        }
        findViewById<ImageButton>(R.id.Analyticsbtn).setOnClickListener {
            val intent = Intent(this@BarryDressUp, Analytics::class.java)
            startActivity(intent)
            finish()
        }
        findViewById<MaterialButton>(R.id.Addbtn).setOnClickListener {
            val intent = Intent(this@BarryDressUp, LoggingScreen::class.java)
            startActivity(intent)
            finish()
        }

        // outfit changing
        findViewById<ImageButton>(R.id.Outfit1).setOnClickListener {
            val intent = Intent(this@BarryDressUp, BarryCareScreen::class.java).apply {
                // Pass the drawable resource ID of this specific outfit
                putExtra("OUTFIT_RES_ID", R.drawable.barrybear) // Replace with your actual drawable name
            }
            startActivity(intent)
            finish()
        }
        findViewById<ImageButton>(R.id.Outfit2).setOnClickListener {
            val intent = Intent(this@BarryDressUp, BarryCareScreen::class.java).apply {
                // Pass the drawable resource ID of this specific outfit
                putExtra("OUTFIT_RES_ID", R.drawable.barrybearevil)
            }
            startActivity(intent)
            finish()
        }
        findViewById<ImageButton>(R.id.Outfit3).setOnClickListener {
            val intent = Intent(this@BarryDressUp, BarryCareScreen::class.java).apply {
                // Pass the drawable resource ID of this specific outfit
                putExtra("OUTFIT_RES_ID", R.drawable.barrybearking)
            }
            startActivity(intent)
            finish()
        }
        findViewById<ImageButton>(R.id.Outfit4).setOnClickListener {
            val intent = Intent(this@BarryDressUp, BarryCareScreen::class.java).apply {
                // Pass the drawable resource ID of this specific outfit
                putExtra("OUTFIT_RES_ID", R.drawable.barrybearclown)
            }
            startActivity(intent)
            finish()
        }
        findViewById<ImageButton>(R.id.Outfit5).setOnClickListener {
            val intent = Intent(this@BarryDressUp, BarryCareScreen::class.java).apply {
                // Pass the drawable resource ID of this specific outfit
                putExtra("OUTFIT_RES_ID", R.drawable.barrybearkitty)
            }
            startActivity(intent)
            finish()
        }
        findViewById<ImageButton>(R.id.Outfit6).setOnClickListener {
            val intent = Intent(this@BarryDressUp, BarryCareScreen::class.java).apply {
                // Pass the drawable resource ID of this specific outfit
                putExtra("OUTFIT_RES_ID", R.drawable.barrybeargentleman)
            }
            startActivity(intent)
            finish()
        }
        findViewById<ImageButton>(R.id.Outfit7).setOnClickListener {
            val intent = Intent(this@BarryDressUp, BarryCareScreen::class.java).apply {
                // Pass the drawable resource ID of this specific outfit
                putExtra("OUTFIT_RES_ID", R.drawable.barrybearmessy)
            }
            startActivity(intent)
            finish()
        }
        findViewById<ImageButton>(R.id.Outfit8).setOnClickListener {
            val intent = Intent(this@BarryDressUp, BarryCareScreen::class.java).apply {
                // Pass the drawable resource ID of this specific outfit
                putExtra("OUTFIT_RES_ID", R.drawable.barrybearnature)
            }

            startActivity(intent)
            finish()
        }
        findViewById<ImageButton>(R.id.Outfit9).setOnClickListener {
            val intent = Intent(this@BarryDressUp, BarryCareScreen::class.java).apply {
                // Pass the drawable resource ID of this specific outfit
                putExtra("OUTFIT_RES_ID", R.drawable.barrybearsleepy)
            }
            startActivity(intent)
            finish()
        }


    }
}