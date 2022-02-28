package com.isilsucitim.mercury

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.isilsucitim.mercury.databinding.ActivityAuthBinding
import com.isilsucitim.mercury.db.Storage
import com.isilsucitim.mercury.network.ApiRepository
import com.isilsucitim.mercury.network.dto.AuthData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    @Inject
    lateinit var apiRepository: ApiRepository
    var isLogin = true
    var notificationKey: String? = null
//    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_auth)
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener {
            if (!it.isSuccessful) {
                return@OnCompleteListener
            }
            notificationKey = it.result
        })
        firebaseAuth = Firebase.auth
        // Configure Google Sign In
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getString(R.string.default_web_client_id))
//            .requestEmail()
//            .build()
//
//        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
//        if (requestCode == RC_SIGN_IN) {
//            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
//            try {
//                // Google Sign In was successful, authenticate with Firebase
//                val account = task.getResult(ApiException::class.java)!!
//                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
//                firebaseAuthWithGoogle(account.id!!,account.idToken!!)
//            } catch (e: ApiException) {
//                // Google Sign In failed, update UI appropriately
//                Log.w(TAG, "Google sign in failed", e)
//            }
//        }
//    }

//    private fun firebaseAuthWithGoogle(id: String, idToken: String) {
//        val credential = GoogleAuthProvider.getCredential(idToken, null)
//        firebaseAuth.signInWithCredential(credential)
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    // Sign in success, update UI with the signed-in user's information
//                    Log.d(TAG, "signInWithCredential:success")
//                    val user = firebaseAuth.currentUser
//                    user!!.uid
//                    user.email
//                    user.photoUrl
//                    auth(AuthData(user.email!!,fullname = user.displayName))
//                } else {
//                    // If sign in fails, display a message to the user.
//                    Log.w(TAG, "signInWithCredential:failure", task.exception)
//                }
//            }
//    }

    override fun onStart() {
        super.onStart()
        if (firebaseAuth.currentUser != null){
            firebaseAuth.signOut()
        }
    }

    fun auth(authData: AuthData){
        authData.notificationKey = notificationKey
        CoroutineScope(Dispatchers.IO).launch {
            if (!isLogin) {
                authData.username = binding.usernameInput.text.toString()
                authData.fullname = binding.fullnameInput.text.toString()
                authData.bio = binding.bioInput.text.toString()
            }
            val response = apiRepository.login(authData)
            withContext(Dispatchers.Main) {
                if (!response.isSuccessful) {
                    when (response.code()) {
                        422 -> {
                            binding.errorMessage.text = getString(R.string.validation_error)
                            binding.errorMessage.visibility = View.VISIBLE
                        }
                        400 -> {
                            binding.errorMessage.text = getString(R.string.is_wrong)
                            binding.errorMessage.visibility = View.VISIBLE
                        }

                    }
                    return@withContext
                }
                val responseBody = response.body()
                Log.d(TAG, responseBody!!.token)
                Storage.saveUser(
                    this@AuthActivity,
                    responseBody.user.username,
                    responseBody.token,
                    responseBody.user.id,
                    responseBody.user.fullname,
                    responseBody.user.bio,
                    ""
                )
                startActivity(Intent(this@AuthActivity, MainActivity::class.java))
                finish()
            }
        }
    }

    fun loginEmail(v: View) {
        binding.errorMessage.visibility = View.GONE
        auth(AuthData(binding.emailInput.text.toString(),binding.passwordInput.text.toString()))
    }

    fun toggleRegister(view: View) {
        binding.errorMessage.visibility = View.GONE
        if (isLogin) {
            binding.toggleRegister.text = getString(R.string.login_if_you_have)
            binding.usernameInput.visibility = View.VISIBLE
            binding.fullnameInput.visibility = View.VISIBLE
            binding.bioInput.visibility = View.VISIBLE
            binding.authButton.text = getString(R.string.register)
        } else {
            binding.toggleRegister.text = getString(R.string.create_an_account)
            binding.usernameInput.visibility = View.GONE
            binding.fullnameInput.visibility = View.GONE
            binding.bioInput.visibility = View.GONE
            binding.authButton.text = getString(R.string.login)
        }
        isLogin = !isLogin
    }

    fun loginGoogle(view: View) {
//
//        val signInIntent = googleSignInClient.signInIntent
//
//        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    fun loginFacebook(view: View) {

    }

    companion object{
        private const val TAG = "AuthActivity"
        private const val RC_SIGN_IN = 1246
    }
}