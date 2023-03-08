package com.example.androidprogetto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
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

        // SEZIONE CHE GESTISCE L'AUMENTO E IL DECREMENTO DEL CONTATORE DEI PRODOTTI
        val numero = findViewById<TextView>(R.id.numProd)
        numero.setText("0")
        var counter = 0


        val buttonPlus = findViewById<Button>(R.id.buttonPlus)
        buttonPlus.setOnClickListener {
            counter++
            numero.setText(counter.toString())
        }

        val buttonMinus = findViewById<Button>(R.id.buttonMinus)
        buttonMinus.setOnClickListener {
            if (counter >0){
            counter--
            numero.setText(counter.toString())}

            else {
                Toast.makeText(this, "Non puoi scendere sotto lo zero", Toast.LENGTH_LONG)
                    .show()
            }
        }
        }
    }





