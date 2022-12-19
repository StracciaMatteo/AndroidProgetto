package com.example.androidprogetto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar

class visualizza_offerte : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visualizza_offerte)
        setSupportActionBar(findViewById<Toolbar>(R.id.toolbarmenu))
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.tmenu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean{
        when (item.itemId) {
            R.id.ordina -> {
                val intent = Intent(this, selzione_prodotti::class.java)
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
            R.id.azioniUtente -> {
                val intent = Intent(this, login::class.java)
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