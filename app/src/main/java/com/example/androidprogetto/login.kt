//package com.firebase.uidemo.auth

import android.R
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultCallback
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.firebase.ui.auth.*
import com.firebase.ui.auth.AuthUI.IdpConfig
import com.firebase.ui.auth.AuthUI.IdpConfig.*
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.firebase.ui.auth.util.ExtraConstants
import com.firebase.uidemo.databinding.AuthUiLayoutBinding
import com.example.androidprogetto.ConfigurationUtils
import com.google.android.gms.common.Scopes
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class AuthUiActivity : AppCompatActivity(),
    ActivityResultCallback<FirebaseAuthUIAuthenticationResult> {
    private var mBinding: AuthUiLayoutBinding? = null
    private val signIn = registerForActivityResult(FirebaseAuthUIActivityResultContract(), this)
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = AuthUiLayoutBinding.inflate(layoutInflater)
        setContentView(mBinding.getRoot())

        // Workaround for vector drawables on API 19
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        if (ConfigurationUtils.isGoogleMisconfigured(this)) {
            mBinding.googleProvider.setChecked(false)
            mBinding.googleProvider.setEnabled(false)
            mBinding.googleProvider.setText(R.string.google_label_missing_config)
            setGoogleScopesEnabled(false)
        } else {
            setGoogleScopesEnabled(mBinding.googleProvider.isChecked())
            mBinding.googleProvider.setOnCheckedChangeListener { compoundButton, checked ->
                setGoogleScopesEnabled(
                    checked
                )
            }
        }
        if (ConfigurationUtils.isFacebookMisconfigured(this)) {
            mBinding.facebookProvider.setChecked(false)
            mBinding.facebookProvider.setEnabled(false)
            mBinding.facebookProvider.setText(R.string.facebook_label_missing_config)
            setFacebookPermissionsEnabled(false)
        } else {
            setFacebookPermissionsEnabled(mBinding.facebookProvider.isChecked())
            mBinding.facebookProvider.setOnCheckedChangeListener { compoundButton, checked ->
                setFacebookPermissionsEnabled(
                    checked
                )
            }
        }
        mBinding.emailLinkProvider.setOnCheckedChangeListener { buttonView, isChecked ->
            flipPasswordProviderCheckbox(
                isChecked
            )
        }
        mBinding.emailProvider.setOnCheckedChangeListener { buttonView, isChecked ->
            flipEmailLinkProviderCheckbox(
                isChecked
            )
        }
        mBinding.emailLinkProvider.setChecked(false)
        mBinding.emailProvider.setChecked(true)

        // The custom layout in this app only supports Email and Google providers.
        mBinding.customLayout.setOnCheckedChangeListener { compoundButton, checked ->
            if (checked) {
                mBinding.googleProvider.setChecked(true)
                mBinding.emailProvider.setChecked(true)
                mBinding.facebookProvider.setChecked(false)
                mBinding.twitterProvider.setChecked(false)
                mBinding.emailLinkProvider.setChecked(false)
                mBinding.phoneProvider.setChecked(false)
                mBinding.anonymousProvider.setChecked(false)
                mBinding.microsoftProvider.setChecked(false)
                mBinding.yahooProvider.setChecked(false)
                mBinding.appleProvider.setChecked(false)
                mBinding.githubProvider.setChecked(false)
            }
        }

        // useEmulator can't be reversed until the FirebaseApp is cleared, so we make this
        // checkbox "sticky" until the app is restarted
        mBinding.useAuthEmulator.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mBinding.useAuthEmulator.setEnabled(false)
            }
        }
        mBinding.signIn.setOnClickListener { view -> signIn() }
        mBinding.signInSilent.setOnClickListener { view -> silentSignIn() }
        if (ConfigurationUtils.isGoogleMisconfigured(this)
            || ConfigurationUtils.isFacebookMisconfigured(this)
        ) {
            showSnackbar(R.string.configuration_required)
        }
        catchEmailLinkSignIn()
    }

    fun catchEmailLinkSignIn() {
        if (intent.extras == null) {
            return
        }
        val link = intent.extras!!.getString(ExtraConstants.EMAIL_LINK_SIGN_IN)
        link?.let { signInWithEmailLink(it) }
    }

    fun flipPasswordProviderCheckbox(emailLinkProviderIsChecked: Boolean) {
        if (emailLinkProviderIsChecked) {
            mBinding.emailProvider.setChecked(false)
        }
    }

    fun flipEmailLinkProviderCheckbox(passwordProviderIsChecked: Boolean) {
        if (passwordProviderIsChecked) {
            mBinding.emailLinkProvider.setChecked(false)
        }
    }

    fun signIn() {
        signIn.launch(getSignInIntent( /*link=*/null))
    }

    fun signInWithEmailLink(link: String?) {
        signIn.launch(getSignInIntent(link))
    }

    val authUI: AuthUI
        get() {
            val authUI = AuthUI.getInstance()
            if (mBinding.useAuthEmulator.isChecked()) {
                authUI.useEmulator("10.0.2.2", 9099)
            }
            return authUI
        }

    private fun getSignInIntent(link: String?): Intent {
        val builder = authUI.createSignInIntentBuilder()
            .setTheme(selectedTheme)
            .setLogo(selectedLogo)
            .setAvailableProviders(selectedProviders)
            .setIsSmartLockEnabled(
                mBinding.credentialSelectorEnabled.isChecked(),
                mBinding.hintSelectorEnabled.isChecked()
            )
        if (mBinding.customLayout.isChecked()) {
            val customLayout =
                AuthMethodPickerLayout.Builder(R.layout.auth_method_picker_custom_layout)
                    .setGoogleButtonId(R.id.custom_google_signin_button)
                    .setEmailButtonId(R.id.custom_email_signin_clickable_text)
                    .setTosAndPrivacyPolicyId(R.id.custom_tos_pp)
                    .build()
            builder.setTheme(R.style.CustomTheme)
            builder.setAuthMethodPickerLayout(customLayout)
        }
        if (selectedTosUrl != null && selectedPrivacyPolicyUrl != null) {
            builder.setTosAndPrivacyPolicyUrls(
                selectedTosUrl!!,
                selectedPrivacyPolicyUrl!!
            )
        }
        if (link != null) {
            builder.setEmailLink(link)
        }
        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null && auth.currentUser!!.isAnonymous) {
            builder.enableAnonymousUsersAutoUpgrade()
        }
        return builder.build()
    }

    fun silentSignIn() {
        authUI.silentSignIn(this, selectedProviders)
            .addOnCompleteListener(
                this
            ) { task: Task<AuthResult?> ->
                if (task.isSuccessful) {
                    startSignedInActivity(null)
                } else {
                    showSnackbar(R.string.sign_in_failed)
                }
            }
    }

    override fun onResume() {
        super.onResume()
        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null && intent.extras == null) {
            startSignedInActivity(null)
            finish()
        }
    }

    private fun handleSignInResponse(resultCode: Int, response: IdpResponse?) {
        // Successfully signed in
        if (resultCode == RESULT_OK) {
            startSignedInActivity(response)
            finish()
        } else {
            // Sign in failed
            if (response == null) {
                // User pressed back button
                showSnackbar(R.string.sign_in_cancelled)
                return
            }
            if (response.error!!.errorCode == ErrorCodes.NO_NETWORK) {
                showSnackbar(R.string.no_internet_connection)
                return
            }
            if (response.error!!.errorCode == ErrorCodes.ANONYMOUS_UPGRADE_MERGE_CONFLICT) {
                val intent = Intent(
                    this,
                    AnonymousUpgradeActivity::class.java
                ).putExtra(ExtraConstants.IDP_RESPONSE, response)
                startActivity(intent)
            }
            if (response.error!!.errorCode == ErrorCodes.ERROR_USER_DISABLED) {
                showSnackbar(R.string.account_disabled)
                return
            }
            showSnackbar(R.string.unknown_error)
            Log.e(TAG, "Sign-in error: ", response.error)
        }
    }

    private fun startSignedInActivity(response: IdpResponse?) {
        startActivity(SignedInActivity.createIntent(this, response))
    }

    @get:StyleRes
    private val selectedTheme: Int
        private get() {
            if (mBinding.greenTheme.isChecked()) {
                return R.style.GreenTheme
            }
            return if (mBinding.appTheme.isChecked()) {
                R.style.AppTheme
            } else AuthUI.getDefaultTheme()
        }

    @get:DrawableRes
    private val selectedLogo: Int
        private get() {
            if (mBinding.firebaseLogo.isChecked()) {
                return R.drawable.firebase_auth_120dp
            } else if (mBinding.googleLogo.isChecked()) {
                return R.drawable.ic_googleg_color_144dp
            }
            return AuthUI.NO_LOGO
        }
    private val selectedProviders: List<IdpConfig>
        private get() {
            val selectedProviders: MutableList<IdpConfig> = ArrayList()
            if (mBinding.googleProvider.isChecked()) {
                selectedProviders.add(
                    GoogleBuilder().setScopes(googleScopes).build()
                )
            }
            if (mBinding.facebookProvider.isChecked()) {
                selectedProviders.add(
                    FacebookBuilder()
                        .setPermissions(facebookPermissions)
                        .build()
                )
            }
            if (mBinding.emailProvider.isChecked()) {
                selectedProviders.add(
                    EmailBuilder()
                        .setRequireName(mBinding.requireName.isChecked())
                        .setAllowNewAccounts(mBinding.allowNewEmailAccounts.isChecked())
                        .build()
                )
            }
            if (mBinding.emailLinkProvider.isChecked()) {
                val actionCodeSettings = ActionCodeSettings.newBuilder()
                    .setAndroidPackageName("com.firebase.uidemo", true, null)
                    .setHandleCodeInApp(true)
                    .setUrl("https://google.com")
                    .build()
                selectedProviders.add(
                    EmailBuilder()
                        .setAllowNewAccounts(mBinding.allowNewEmailAccounts.isChecked())
                        .setActionCodeSettings(actionCodeSettings)
                        .enableEmailLinkSignIn()
                        .build()
                )
            }
            if (mBinding.phoneProvider.isChecked()) {
                selectedProviders.add(PhoneBuilder().build())
            }
            if (mBinding.anonymousProvider.isChecked()) {
                selectedProviders.add(AnonymousBuilder().build())
            }
            if (mBinding.twitterProvider.isChecked()) {
                selectedProviders.add(TwitterBuilder().build())
            }
            if (mBinding.microsoftProvider.isChecked()) {
                selectedProviders.add(MicrosoftBuilder().build())
            }
            if (mBinding.yahooProvider.isChecked()) {
                selectedProviders.add(YahooBuilder().build())
            }
            if (mBinding.appleProvider.isChecked()) {
                selectedProviders.add(AppleBuilder().build())
            }
            if (mBinding.githubProvider.isChecked()) {
                selectedProviders.add(GitHubBuilder().build())
            }
            return selectedProviders
        }
    private val selectedTosUrl: String?
        private get() {
            if (mBinding.googleTosPrivacy.isChecked()) {
                return GOOGLE_TOS_URL
            }
            return if (mBinding.firebaseTosPrivacy.isChecked()) {
                FIREBASE_TOS_URL
            } else null
        }
    private val selectedPrivacyPolicyUrl: String?
        private get() {
            if (mBinding.googleTosPrivacy.isChecked()) {
                return GOOGLE_PRIVACY_POLICY_URL
            }
            return if (mBinding.firebaseTosPrivacy.isChecked()) {
                FIREBASE_PRIVACY_POLICY_URL
            } else null
        }

    private fun setGoogleScopesEnabled(enabled: Boolean) {
        mBinding.googleScopesHeader.setEnabled(enabled)
        mBinding.googleScopeDriveFile.setEnabled(enabled)
        mBinding.googleScopeYoutubeData.setEnabled(enabled)
    }

    private fun setFacebookPermissionsEnabled(enabled: Boolean) {
        mBinding.facebookPermissionsHeader.setEnabled(enabled)
        mBinding.facebookPermissionFriends.setEnabled(enabled)
        mBinding.facebookPermissionPhotos.setEnabled(enabled)
    }

    private val googleScopes: List<String>
        private get() {
            val result: MutableList<String> = ArrayList()
            if (mBinding.googleScopeYoutubeData.isChecked()) {
                result.add("https://www.googleapis.com/auth/youtube.readonly")
            }
            if (mBinding.googleScopeDriveFile.isChecked()) {
                result.add(Scopes.DRIVE_FILE)
            }
            return result
        }
    private val facebookPermissions: List<String>
        private get() {
            val result: MutableList<String> = ArrayList()
            if (mBinding.facebookPermissionFriends.isChecked()) {
                result.add("user_friends")
            }
            if (mBinding.facebookPermissionPhotos.isChecked()) {
                result.add("user_photos")
            }
            return result
        }

    private fun showSnackbar(@StringRes errorMessageRes: Int) {
        Snackbar.make(mBinding.getRoot(), errorMessageRes, Snackbar.LENGTH_LONG).show()
    }

    override fun onActivityResult(result: FirebaseAuthUIAuthenticationResult) {
        // Successfully signed in
        val response = result.idpResponse
        handleSignInResponse(result.resultCode, response)
    }

    companion object {
        private const val TAG = "AuthUiActivity"
        private const val GOOGLE_TOS_URL = "https://www.google.com/policies/terms/"
        private const val FIREBASE_TOS_URL = "https://firebase.google.com/terms/"
        private const val GOOGLE_PRIVACY_POLICY_URL = "https://www.google" +
                ".com/policies/privacy/"
        private const val FIREBASE_PRIVACY_POLICY_URL = "https://firebase.google" +
                ".com/terms/analytics/#7_privacy"
        private const val RC_SIGN_IN = 100
        fun createIntent(context: Context): Intent {
            return Intent(context, AuthUiActivity::class.java)
        }
    }
}