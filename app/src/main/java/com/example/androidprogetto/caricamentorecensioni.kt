package com.example.androidprogetto

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.TextView
import com.google.firebase.firestore.AggregateSource
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

class caricamentorecensioni : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_caricamento_recensioni)
        val db = Firebase.firestore
        val textProva = findViewById<TextView>(R.id.textViewProvaNumero)



        //FUNZIONE CHE CI PERMETTE DI SALVARE LA RECENSIONE SUL DB CON NUMERAZIONE PROGRESSIVA
        fun salvaRecensione() {
            val titolo = findViewById<EditText>(R.id.inputTitolo).getText().toString()

            val testo = findViewById<EditText>(R.id.inputTestoRec).getText().toString()

            val valutazione = findViewById<RatingBar>(R.id.ratingBarRec).getRating().toInt()

            val formato =DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
            val dataorario = LocalDateTime.now().format(formato)

            val recensioniKot = hashMapOf(
                "Titolo" to titolo,
                "Testo" to testo,
                "Valutazione" to valutazione,
                "Data e ora" to dataorario
            )

            val recensioniRef = db.collection("Recensioni")
            val recensioniCount = recensioniRef.count()
            recensioniCount.get(AggregateSource.SERVER).addOnCompleteListener { task ->

            if (task.isSuccessful) {
                val snapshot = task.result
                textProva.setText("count: ${snapshot.count}")

            // Add a new document with a generated ID
            db.collection("Recensioni")
                .document("Recensione" + (snapshot.count.toInt()+1))
                .set(recensioniKot)
                //.add(recensioniKot)
                .addOnSuccessListener { documentReference ->
                    Log.d(
                        ContentValues.TAG,
                        "Doc added"
                        //"DocumentSnapshot added with ID: ${documentReference.id}"
                    )
                }
                .addOnFailureListener { e ->
                    Log.w(ContentValues.TAG, "Error adding document", e)
                }
            }

            else {
                task.exception?.message?.let {
                    print(it)
                }
            }

        }}

        /*PER PASSARE AD UN'ALTRA ACTIVITY TRAMITE CLICK SUL BOTTONE E SALVARE LA RECENSIONE SUL DB*/
        val buttonClick = findViewById<Button>(R.id.buttonSalvaRec)
        buttonClick.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            salvaRecensione()
        }

}}

