package com.example.androidprogetto

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.widget.Toolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class resoconto : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resoconto)
        setSupportActionBar(findViewById<Toolbar>(R.id.toolbarmenu))
        val db = Firebase.firestore




        //PER PASSARE I DATI DAL DB ALL'ACTIVITY (ma funziona con un solo documento e non con tutti gli altri)
        val nome = findViewById<TextView>(R.id.NomeP)
        val prezzo = findViewById<TextView>(R.id.PrezzoP)
        val qtà = findViewById<TextView>(R.id.QuantitaP)
        val totale = findViewById<TextView>(R.id.CalcoloTotale)

        val docRef = db.collection("Ordini").document("Ordine1")
        docRef.get()
            .addOnSuccessListener{ document ->
                if (document != null) {
                    Log.d(ContentValues.TAG, "DocumentSnapshot data: ${document.data}")
                    nome.setText(document["Nome"].toString())
                    prezzo.setText(document["Prezzo"].toString())
                    qtà.setText(document["Qtà"].toString())

                    var getPrezzo = prezzo.getText().toString().toInt()
                    var getQta = qtà.getText().toString().toInt()
                    var totaleRes = getPrezzo*getQta
                    totale.setText(totaleRes.toString()+"€")


                } else {
                    Log.d(ContentValues.TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "get failed with ", exception)
            }

        fun updateTotale (){

                totale.setText("0€")

        }

        val buttonCancella = findViewById<ImageButton>(R.id.iBElimina)
        buttonCancella.setOnClickListener {
                    nome.setText("")
                    prezzo.setText("")
                    qtà.setText("")
                    updateTotale()
        }


        val buttonOrdina = findViewById<Button>(R.id.buttonConfOrdine)
        buttonOrdina.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            docRef.delete().addOnSuccessListener {
                Toast.makeText(this, "Ordinato con successo", Toast.LENGTH_LONG)
                    .show()
            }.addOnFailureListener {
                Toast.makeText(this, "Riprovare", Toast.LENGTH_LONG)
                    .show()
            }
        }




        val imageUtente = findViewById<ImageView>(R.id.utente)
        imageUtente.setOnClickListener{
            val popupMenu = PopupMenu(this, it)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId){
                    R.id.accedi->{
                        val intent = Intent(this, login::class.java)
                        startActivity(intent)
                        true
                    }
                    R.id.registrazione->{
                        val intent = Intent(this, registrazione::class.java)
                        startActivity(intent)
                        true
                    }
                    R.id.tuoipreferiti->{
                        val intent = Intent(this, listapreferiti::class.java)
                        startActivity(intent)
                        true}
                    R.id.profilo->{
                        val intent = Intent(this, visualizzaprofilo::class.java)
                        startActivity(intent)
                        true}
                    R.id.esci->{
                        val intent = Intent(this, login::class.java)
                        startActivity(intent)
                        true}

                    else -> false
                }
            }

            popupMenu.inflate(R.menu.pmenu)
            try {
                val fieldMPopup= PopupMenu::class.java.getDeclaredField("mPopup")
                fieldMPopup.isAccessible = true
                val mPopup = fieldMPopup.get(popupMenu)
                mPopup.javaClass
                    .getDeclaredMethod("setForceShowIcon",Boolean::class.java)
                    .invoke(mPopup,true)
            }
            catch (e: Exception){
                Log.e("main", "Errore nel mostrare il menu", e)
            }
            finally {
                popupMenu.show()
            }

        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.tmenu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean{
        when (item.itemId) {
            R.id.ordina -> {
                val intent = Intent(this, scelta_servizio::class.java)
                startActivity(intent)
            }
            R.id.prodotti -> {
                val intent = Intent(this, prodotti::class.java)
                startActivity(intent)
            }
            R.id.calendarioeventi -> {
                val intent = Intent(this, calendario_eventi::class.java)
                startActivity(intent)
            }
            R.id.offerte -> {
                val intent = Intent(this, visualizza_offerte::class.java)
                startActivity(intent)
            }
            R.id.recensioni -> {
                val intent = Intent(this, recensioni::class.java)
                startActivity(intent)
            }

            R.id.impostazioni -> {
                val intent = Intent(this, impostazioni::class.java)
                startActivity(intent)
            }

        }
        return true
    }

}