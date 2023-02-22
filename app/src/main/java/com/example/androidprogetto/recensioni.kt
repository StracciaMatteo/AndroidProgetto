package com.example.androidprogetto

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class recensioni : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recensioni)
        val db = Firebase.firestore


        /*FUNZIONE PER PASSARE AD UN'ALTRA ACTIVITY TRAMITE CLICK SUL BOTTONE*/
        val floatAdd = findViewById<FloatingActionButton>(R.id.addRecensione)
        floatAdd.setOnClickListener {
            val intent = Intent(this, caricamentorecensioni::class.java)
            startActivity(intent)
        }


        }
    }
