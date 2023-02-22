package com.example.androidprogetto

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class caricamentorecensioni : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_caricamento_recensioni)
        val db = Firebase.firestore

        fun salvaRecensione() {
            val titolo = findViewById<EditText>(R.id.inputTitolo).getText().toString()

            val testo = findViewById<EditText>(R.id.inputTestoRec).getText().toString()

            val valutazione = findViewById<RatingBar>(R.id.ratingBarRec).getRating().toInt()

            val recensioniKot = hashMapOf(
                "Titolo" to titolo,
                "Testo" to testo,
                "Valutazione" to valutazione
            )

            // Add a new document with a generated ID
            db.collection("recensioni2")
                .add(recensioniKot)
                .addOnSuccessListener { documentReference ->
                    Log.d(
                        ContentValues.TAG,
                        "DocumentSnapshot added with ID: ${documentReference.id}"
                    )
                }
                .addOnFailureListener { e ->
                    Log.w(ContentValues.TAG, "Error adding document", e)
                }
        }

        /*FUNZIONE PER PASSARE AD UN'ALTRA ACTIVITY TRAMITE CLICK SUL BOTTONE E SALVARE LA RECENSIONE SUL DB*/
        val buttonClick = findViewById<Button>(R.id.buttonSalvaRec)
        buttonClick.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            salvaRecensione()
        }
    }
}