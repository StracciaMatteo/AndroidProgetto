package com.example.androidprogetto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.StringRes
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.firebase.uidemo.auth.SignedInActivity
import com.firebase.uidemo.auth.SignedInActivity.Companion.createIntent
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth

class registrazione : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrazione)

        /*private*/
        fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
            val response = result.idpResponse
            if (result.resultCode == RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                // ...
            }
            else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }

        }

        // See: https://developer.android.com/training/basics/intents/result

        val signInLauncher = registerForActivityResult(
            FirebaseAuthUIActivityResultContract())
            { res -> onSignInResult(res) }      //ORIGINALE ::: { res -> this.onSignInResult(res) }

        // Choose authentication providers
        val provider = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build())



// Create and launch sign-in intent

        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(provider)
            .build()
        signInLauncher.launch(signInIntent)

        

        val actionCodeSettings = ActionCodeSettings.newBuilder()
            .setAndroidPackageName( /* yourPackageName= */
                "...",  /* installIfNotAvailable= */
                true,  /* minimumVersion= */
                null)
            .setHandleCodeInApp(true) // This must be set to true
            .setUrl("https://androidprogetto.page.link/76UZ") // This URL needs to be whitelisted
            .build()

        val providers = listOf(
            AuthUI.IdpConfig.EmailBuilder()
                .enableEmailLinkSignIn()
                .setActionCodeSettings(actionCodeSettings)
                .build()
        )


        if (AuthUI.canHandleIntent(intent)) {
            val extras = intent.extras ?: return
            val link = extras.getString("email_link_sign_in")
            if (link != null) {
                val signInIntent = AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setEmailLink(link)
                    .setAvailableProviders(providers)
                    .setLogo(R.drawable.logo) // Set logo drawable
                    .setTheme(R.style.Theme_AndroidProgetto_NoActionBar) //Set theme
                    .build()
                signInLauncher.launch(signInIntent)
            }
        }


        fun signOut() {
            AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener { task: Task<Void?> ->
                    if (task.isSuccessful) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        true
                        finish()
                    } else {
                        Log.w(SignedInActivity.TAG, "signOut:failure", task.exception)
                        //showSnackbar(android.R.string.sign_out_failed)
                    }
                }
        }




        fun signOutNostra(){
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {} }

        /* PER SETTARE LA SCHERMATA, abbiamo aggiunto delle voci qui, sopra*/
        /*
        val signInIntentTheme = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setLogo(R.drawable.logo) // Set logo drawable
            .setTheme(R.style.Theme_AndroidProgetto_NoActionBar) // Set theme
            .build()
        signInLauncher.launch(signInIntentTheme)*/

    }
}