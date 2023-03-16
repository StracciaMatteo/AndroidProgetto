package com.example.androidprogetto

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.firebase.firestore.AggregateSource
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class selzione_prodotti : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selzione_prodotti)
        setSupportActionBar(findViewById<Toolbar>(R.id.toolbarmenu))
        val db = Firebase.firestore

        //BOTTONE PER PASSARE ALL'ACTIVITY DETTAGLIO PRODOTTO
        val buttonClick = findViewById<Button>(R.id.buttonDettagli)
        buttonClick.setOnClickListener {
            val intent = Intent(this, dettaglioprodotto::class.java)
            startActivity(intent)
        }

        //FUNZIONE CHE CARICA L'OIRDINE SUL DB
        fun caricaOrdine(){

            val nome = findViewById<TextView>(R.id.tVNome1).getText().toString()
            val prezzo = findViewById<TextView>(R.id.tVPrezzo1).getText().toString()
            val qtà = findViewById<TextView>(R.id.numProd1).getText().toString()

            val ordine = hashMapOf(
                "Nome" to nome,
                "Prezzo" to prezzo,
                "Qtà" to qtà
            )


            val ordiniRef = db.collection("Ordini")
            val ordiniCount = ordiniRef.count()
            ordiniCount.get(AggregateSource.SERVER).addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    val snapshot = task.result

                    // Add a new document with a generated ID
                    db.collection("Ordini")
                        .document("Ordine" + (snapshot.count.toInt()+1))
                        .set(ordine)
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

            }

        }

        //BOTTONE PER PASSARE ALL'ACTIVITY TRANSIZIONE RESOCONTO
        val buttonOrd = findViewById<Button>(R.id.buttonOrdina)
        buttonOrd.setOnClickListener {
            val intent = Intent(this, transizione_resoconto::class.java)
            startActivity(intent)
            caricaOrdine()
        }


        // SEZIONE CHE GESTISCE L'AUMENTO E IL DECREMENTO DEL CONTATORE DEI PRODOTTI
        val numero = findViewById<TextView>(R.id.numProd1)
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





