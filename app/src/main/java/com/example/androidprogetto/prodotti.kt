package com.example.androidprogetto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.PopupMenu
import com.google.android.material.floatingactionbutton.FloatingActionButton

class prodotti : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prodotti)

        val buttonClick = findViewById<Button>(R.id.buttonDettagli)
        buttonClick.setOnClickListener {
            val intent = Intent(this, dettaglioprodotto::class.java)
            startActivity(intent)
        }

        val buttonClick2 = findViewById<Button>(R.id.buttonDettagli2)
        buttonClick2.setOnClickListener {
            val intent = Intent(this, dettaglioprodotto::class.java)
            startActivity(intent)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
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

