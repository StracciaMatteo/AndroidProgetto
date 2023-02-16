package com.example.androidprogetto

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.BoringLayout.make
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.make
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import org.checkerframework.common.subtyping.qual.Bottom

class modificaprofilo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modifica_profilo)


     fun salvaModifiche (){
        val user = Firebase.auth.currentUser
        val editTextName = findViewById<EditText>(R.id.inputName)
        val name = editTextName.getText().toString()
        val profileUpdates = userProfileChangeRequest {
            displayName = name
            //photoUri = Uri.parse("https://example.com/jane-q-user/profile.jpg")
        }

         user!!.updateProfile(profileUpdates)
             .addOnCompleteListener { task ->
                 if (task.isSuccessful) {
                     Log.d(TAG, "User profile updated.")
                     Toast.makeText(this, "Dati sono stati aggiornati", Toast.LENGTH_LONG)
                         .show()
                 }
                 else {
                         Toast.makeText(this, "Dati non sono stati aggiornati", Toast.LENGTH_LONG)
                             .show()
                 }
             }
         val intent = Intent(this, MainActivity::class.java)
         startActivity(intent)
     }



        val buttonClick = findViewById<Button>(R.id.buttonSaveUpdate)
        buttonClick.setOnClickListener {
            salvaModifiche()
        }



        //PARTE PER IMPOSTARE TUTTA LA TOOLBAR

        setSupportActionBar(findViewById<Toolbar>(R.id.toolbarmenu))

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
