package com.example.androidprogetto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class visualizzaprofilo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visualizza_profilo)

        val user = Firebase.auth.currentUser
        lateinit var name: String
        lateinit var email: String
        user?.let {
            for (profile in it.providerData) {
                // Id of the provider (ex: google.com)
                val providerId = profile.providerId

                // UID specific to the provider
                val uid = profile.uid

                // Name, email address, and profile photo Url
                name = profile.displayName.toString()
                email = profile.email.toString()
                //val photoUrl = profile.photoUrl

            }
        }
        val textName = findViewById<TextView>(R.id.textName)
        textName.setText(name)

        val textMail = findViewById<TextView>(R.id.textMail)
        textMail.setText(email)

        /*val textName = findViewById<TextView>(R.id.textName)
        textName.setText(name)
        val textMail = findViewById<TextView>(R.id.textMail)
        textMail.setText(email)*/

    }
}