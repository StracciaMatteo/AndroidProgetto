/*package com.firebase.uidemo.auth

import android.R
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.firebase.ui.auth.util.ExtraConstants
import com.firebase.uidemo.databinding.SignedInLayoutBinding
import com.firebase.uidemo.storage.GlideApp
import com.google.android.gms.tasks.Task
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.*

class SignedInActivity : AppCompatActivity() {
    private var mBinding: SignedInLayoutBinding? = null
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser == null) {
            startActivity(AuthUiActivity.createIntent(this))
            finish()
            return
        }
        val response = intent.getParcelableExtra<IdpResponse>(ExtraConstants.IDP_RESPONSE)
        mBinding = SignedInLayoutBinding.inflate(layoutInflater)
        setContentView(mBinding.getRoot())
        populateProfile(response)
        populateIdpToken(response)
        mBinding.deleteAccount.setOnClickListener { view -> deleteAccountClicked() }
        mBinding.signOut.setOnClickListener { view -> signOut() }
    }

    fun signOut() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener { task: Task<Void?> ->
                if (task.isSuccessful) {
                    startActivity(AuthUiActivity.createIntent(this@SignedInActivity))
                    finish()
                } else {
                    Log.w(TAG, "signOut:failure", task.exception)
                    showSnackbar(R.string.sign_out_failed)
                }
            }
    }

    fun deleteAccountClicked() {
        MaterialAlertDialogBuilder(this)
            .setMessage("Are you sure you want to delete this account?")
            .setPositiveButton(
                "Yes, nuke it!"
            ) { dialogInterface: DialogInterface?, i: Int -> deleteAccount() }
            .setNegativeButton("No", null)
            .show()
    }

    private fun deleteAccount() {
        AuthUI.getInstance()
            .delete(this)
            .addOnCompleteListener(
                this
            ) { task: Task<Void?> ->
                if (task.isSuccessful) {
                    startActivity(AuthUiActivity.createIntent(this@SignedInActivity))
                    finish()
                } else {
                    showSnackbar(R.string.delete_account_failed)
                }
            }
    }

    private fun populateProfile(response: IdpResponse?) {
        val user = FirebaseAuth.getInstance().currentUser
        if (user!!.photoUrl != null) {
            GlideApp.with(this)
                .load(user.photoUrl)
                .fitCenter()
                .into(mBinding.userProfilePicture)
        }
        mBinding.userEmail.setText(
            if (TextUtils.isEmpty(user.email)) "No email" else user.email
        )
        mBinding.userPhoneNumber.setText(
            if (TextUtils.isEmpty(user.phoneNumber)) "No phone number" else user.phoneNumber
        )
        mBinding.userDisplayName.setText(
            if (TextUtils.isEmpty(user.displayName)) "No display name" else user.displayName
        )
        if (response == null) {
            mBinding.userIsNew.setVisibility(View.GONE)
        } else {
            mBinding.userIsNew.setVisibility(View.VISIBLE)
            mBinding.userIsNew.setText(if (response.isNewUser) "New user" else "Existing user")
        }
        val providers: MutableList<String> = ArrayList()
        if (user.providerData.isEmpty()) {
            providers.add(getString(R.string.providers_anonymous))
        } else {
            for (info in user.providerData) {
                when (info.providerId) {
                    GoogleAuthProvider.PROVIDER_ID -> providers.add(getString(R.string.providers_google))
                    FacebookAuthProvider.PROVIDER_ID -> providers.add(getString(R.string.providers_facebook))
                    TwitterAuthProvider.PROVIDER_ID -> providers.add(getString(R.string.providers_twitter))
                    EmailAuthProvider.PROVIDER_ID -> providers.add(getString(R.string.providers_email))
                    PhoneAuthProvider.PROVIDER_ID -> providers.add(getString(R.string.providers_phone))
                    AuthUI.EMAIL_LINK_PROVIDER -> providers.add(getString(R.string.providers_email_link))
                    FirebaseAuthProvider.PROVIDER_ID -> {}
                    else -> providers.add(info.providerId)
                }
            }
        }
        mBinding.userEnabledProviders.setText(getString(R.string.used_providers, providers))
    }

    private fun populateIdpToken(response: IdpResponse?) {
        var token: String? = null
        var secret: String? = null
        if (response != null) {
            token = response.idpToken
            secret = response.idpSecret
        }
        val idpTokenLayout = findViewById<View>(R.id.idp_token_layout)
        if (token == null) {
            idpTokenLayout.visibility = View.GONE
        } else {
            idpTokenLayout.visibility = View.VISIBLE
            (findViewById<View>(R.id.idp_token) as TextView).text = token
        }
        val idpSecretLayout = findViewById<View>(R.id.idp_secret_layout)
        if (secret == null) {
            idpSecretLayout.visibility = View.GONE
        } else {
            idpSecretLayout.visibility = View.VISIBLE
            (findViewById<View>(R.id.idp_secret) as TextView).text = secret
        }
    }

    private fun showSnackbar(@StringRes errorMessageRes: Int) {
        Snackbar.make(mBinding.getRoot(), errorMessageRes, Snackbar.LENGTH_LONG).show()
    }

    companion object {
        const val TAG = "SignedInActivity"
        fun createIntent(context: Context, response: IdpResponse?): Intent {
            return Intent().setClass(context, SignedInActivity::class.java)
                .putExtra(ExtraConstants.IDP_RESPONSE, response)
        }
    }
}*/

