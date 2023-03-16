package com.example.androidprogetto

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class transizione_resoconto : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.transizione_resoconto)

        //BOTTONE PER PASSARE ALL'ACTIVITY RESOCONTO
        val buttonTransizione = findViewById<Button>(R.id.buttonTransizione)
        buttonTransizione.setOnClickListener {
            val intent = Intent(this, resoconto::class.java)
            startActivity(intent)


        }

    }}