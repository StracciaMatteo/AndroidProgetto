package com.example.androidprogetto

//import android.R
import android.annotation.SuppressLint
import android.content.Context
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.AuthUI.IdpConfig
import com.firebase.ui.auth.AuthUI.IdpConfig.*
import com.google.firebase.auth.ActionCodeSettings




@SuppressLint("RestrictedApi")
class ConfigurationUtils private constructor() {
    init {
        throw AssertionError("No instance for you!")
    }

    companion object {
        fun isGoogleMisconfigured(context: Context): Boolean {
            return AuthUI.UNCONFIGURED_CONFIG_VALUE ==
                    context.getString(R.string.default_web_client_id1)
        }


        fun getConfiguredProviders(context: Context): List<IdpConfig> {
            val providers: MutableList<IdpConfig> = ArrayList()
            if (!isGoogleMisconfigured(context)) {
                providers.add(GoogleBuilder().build())
            }


            val actionCodeSettings = ActionCodeSettings.newBuilder()
                .setAndroidPackageName("com.example.androidprogetto", true, null)
                .setHandleCodeInApp(true)
                .setUrl("https://google.com")
                .build()
            providers.add(
                EmailBuilder()
                    .setAllowNewAccounts(true)
                    .enableEmailLinkSignIn()
                    .setActionCodeSettings(actionCodeSettings)
                    .build()
            )

            return providers
        }
    }
}