package com.example.androidprogetto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById<Toolbar>(R.id.toolbarmenu))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.tmenu, menu)
        return true
    }

    override fun onOptionsItemSelected(item:MenuItem): Boolean{
        when (item.itemId) {
            R.id.ordina -> {
                println("Ordina")
            }
            R.id.prodotti -> {
                println("Prodotti")
            }
            R.id.calendarioeventi -> {
                println("Calendario Eventi")
            }
            R.id.offerte -> {
                println("Offerte")
            }
            R.id.recensioni -> {
                println("Recensioni")
            }
            R.id.azioniUtente -> {}

        }
        return true
    }

}
