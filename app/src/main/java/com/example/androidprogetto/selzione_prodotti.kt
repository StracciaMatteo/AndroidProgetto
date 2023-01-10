package com.example.androidprogetto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import androidx.appcompat.widget.Toolbar

class selzione_prodotti : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selzione_prodotti)
        setSupportActionBar(findViewById<Toolbar>(R.id.toolbarmenu))


        val buttonClick = findViewById<Button>(R.id.buttonDettagli)
        buttonClick.setOnClickListener {
            val intent = Intent(this, dettaglioprodotto::class.java)
            startActivity(intent)
        }

        val switchClick = findViewById<Switch>(R.id.switchProdotti)
        switchClick.setOnClickListener {
            val intent = Intent(this, FirstFragment::class.java)
            startActivity(intent)
        }
    }

}


