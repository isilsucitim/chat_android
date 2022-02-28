package com.isilsucitim.mercury.network.websocket.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Message(
    val receiver: String,
    val sender: String,
    val data: String,
    @Json(name = "msg_type") val msgType: String
)
