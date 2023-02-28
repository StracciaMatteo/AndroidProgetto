package com.example.androidprogetto

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text

class dettaglioprodotto : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dettaglio_prodotto)
        setSupportActionBar(findViewById<Toolbar>(R.id.toolbarmenu))
        val user = Firebase.auth.currentUser
        val db = Firebase.firestore

        //CODICE PER POPOLARE LA VIEW DA FIRESTORE
        val nomeP = findViewById<TextView>(R.id.NomeProdotto)
        val ingredienti = findViewById<TextView>(R.id.contenutoIngredienti)
        val allergeni = findViewById<TextView>(R.id.contenutoAllergeni)
        val prezzo = findViewById<TextView>(R.id.prezzo)
        val docRef = db.collection("Cibo").document("Frittura di pesce")

        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                    nomeP.setText(document.toString())
                    ingredienti.setText(document["IG1"].toString())
                    allergeni.setText(document["AG1"].toString())
                    prezzo.setText(document["Prezzo"].toString())
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
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