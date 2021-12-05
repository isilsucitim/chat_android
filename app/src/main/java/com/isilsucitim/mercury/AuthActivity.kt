package com.isilsucitim.mercury

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.gms.tasks.OnCompleteListener
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
    val TAG = "AuthActivity"
    var isLogin = true
    var notificationKey :String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_auth)
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener {
            if (!it.isSuccessful){
                return@OnCompleteListener
            }
            notificationKey = it.result
        })
    }

    fun auth(v: View) {
        binding.errorMessage.visibility = View.GONE
        CoroutineScope(Dispatchers.IO).launch {

            val authData =
                AuthData(binding.emailInput.text.toString(), binding.passwordInput.text.toString(),notificationKey=notificationKey)
            if (!isLogin){
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
}