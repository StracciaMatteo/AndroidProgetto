package com.example.androidprogetto


import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RatingBar
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class recensioni : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recensioni)
        val user = Firebase.auth.currentUser
        val db = Firebase.firestore



        //PER PASSARE AD UN'ALTRA ACTIVITY TRAMITE CLICK SUL BOTTONE
        val floatAdd = findViewById<FloatingActionButton>(R.id.addRecensione)
        floatAdd.setOnClickListener {
            val intent = Intent(this, caricamentorecensioni::class.java)
            startActivity(intent)
        }

        //PER PASSARE I DATI DAL DB ALL'ACTIVITY (ma funziona con un solo documento e non con tutti gli altri)
        val nomecognome = findViewById<TextView>(R.id.textVNomeCogn)
        val titolo = findViewById<TextView>(R.id.textVTitoloRec)
        val testo = findViewById<TextView>(R.id.textVTestoRec)
        val valutazione = findViewById<RatingBar>(R.id.ratingBRec)

        val docRef = db.collection("Recensioni").document("Recensione1")
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                    nomecognome.setText("Matteo Straccia")
                    titolo.setText(document["Titolo"].toString())
                    testo.setText(document["Testo"].toString())
                    valutazione.setRating(document["Valutazione"].toString().toFloat())
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }





        }
    }
