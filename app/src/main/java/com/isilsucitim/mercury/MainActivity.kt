package com.isilsucitim.mercury

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import android.util.Log
import com.isilsucitim.mercury.network.websocket.ChatService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var chatService: ChatService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        chatService.observeMessage()
            .flowOn(Dispatchers.IO)
            .onEach {
                Log.d("TAG",it.data)
            }
            .launchIn(lifecycleScope)
    }
}