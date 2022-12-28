package com.example.androidprogetto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.widget.Toolbar




class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById<Toolbar>(R.id.toolbarmenu))

        /*FUNZIONE PER PASSARE AD UN'ALTRA ACTIVITY TRAMITE CLICK SUL BOTTONE*/
        val buttonClick = findViewById<Button>(R.id.buttonHomePromo)
        buttonClick.setOnClickListener {
        val intent = Intent(this, visualizza_offerte::class.java)
        startActivity(intent)
            }

        }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.tmenu, menu)
        return true
    }


/* da riagguingere la parte del pop up menu sta sulla chat di teams */



}
