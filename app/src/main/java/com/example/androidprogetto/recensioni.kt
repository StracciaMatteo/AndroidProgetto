package com.example.androidprogetto

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.floatingactionbutton.FloatingActionButton

class recensioni : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recensioni)


        /*FUNZIONE PER PASSARE AD UN'ALTRA ACTIVITY TRAMITE CLICK SUL BOTTONE*/
        val floatAdd = findViewById<FloatingActionButton>(R.id.addRecensione)
        floatAdd.setOnClickListener {
            val intent = Intent(this, caricamentorecensioni::class.java)
            startActivity(intent)
        }

    }
}