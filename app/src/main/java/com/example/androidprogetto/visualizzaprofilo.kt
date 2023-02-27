package com.example.androidprogetto

import android.content.Intent
import android.graphics.drawable.Drawable
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.net.URI

class visualizzaprofilo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visualizza_profilo)

        val user = Firebase.auth.currentUser
        lateinit var name: String
        lateinit var email: String
        //val photoUrl : Uri? = user?.photoUrl

        user?.let {
            for (profile in it.providerData) {
                // Id of the provider (ex: google.com)
                val providerId = profile.providerId

                // UID specific to the provider
                val uid = profile.uid

                // Name, email address, and profile photo Url
                name = profile.displayName.toString()
                email = profile.email.toString()
                /*__________________________________________________

                val avatarStgRef = firebase.storage().ref("Usuarios/" + user.uid + "/avatar.jpg")
                // imgFile the Photo File.
                avatarStgRef.put(imgFile).then(function(snapshot){
                    snapshot.ref.getDownloadURL().then(function(url){  // Now I can use url
                        user.updateProfile({
                                photoURL: url       // <- URL from uploaded photo.
                        }).then(function(){
                            firebase.database().ref("Usuarios/" + user.uid).set({
                                "photoUri": url   // <- URL from uploaded photo.
                            })
                        })
                    })
                })
                //__________________________________________________*/

            }
        }
        val textName = findViewById<TextView>(R.id.textName)
        val textMail = findViewById<TextView>(R.id.textMail)
        val profileImage = findViewById<ImageView>(R.id.imageViewProfile)
        val foto =  getResources().getDrawable(R.drawable.userchef, null)
        //val foto = Uri.parse("https://lh3.googleusercontent.com/a/AGNmyxZM6CZshd-3LbKbEUvXZPg9Cg4aRUUNgXmZ-xlC_A=s96-c-rg-br100")
        if (user != null) {
            textName.setText(user.displayName)
            textMail.setText(email)
            //profileImage.setImageURI(foto)
            profileImage.setImageDrawable(foto)
            //profileImage.setImageURI(user.photoUrl) //NON FUNZIONANTE-> LA FOTO NON VIENE PRESA, PERO LA IMAGEVIEW CAMBIA (SPARISCE)
        }

        /*val textName = findViewById<TextView>(R.id.textName)
        textName.setText(name)
        val textMail = findViewById<TextView>(R.id.textMail)
        textMail.setText(email)*/

        val buttonClick = findViewById<Button>(R.id.buttonModifica)
        buttonClick.setOnClickListener {
            val intent = Intent(this, modificaprofilo::class.java)
            startActivity(intent)
        }

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
