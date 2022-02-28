package com.isilsucitim.mercury.network.websocket

import com.isilsucitim.mercury.network.websocket.models.Message
import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import kotlinx.coroutines.flow.Flow

interface ChatService {
    @Receive
    fun observeWebSocketEvent(): Flow<WebSocket.Event>

    @Send
    fun sendMessage(message: Message)

    @Receive
    fun observeMessage(): Flow<Message>
}