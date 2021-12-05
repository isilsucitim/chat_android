package com.isilsucitim.mercury.network.dto

import com.google.gson.annotations.SerializedName
import java.util.*

data class Profile(
    val id: String,
    val email: String,
    val username: String,
    val fullname: String,
    val bio: String,
    val phone: String?,
    @SerializedName("created_at") val createdAt: Date
)

