package com.example.androidprogetto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.appcompat.widget.Toolbar



class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById<Toolbar>(R.id.toolbarmenu))

        val button: Button = findViewById(R.id.buttonHomePromo)
        button.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v:View?){

                Intent paginaPromo = new Intent(MainActivity.this,visualizza_offerte.class)

            }
        }
        )
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
