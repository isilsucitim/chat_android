package com.isilsucitim.mercury.network.dto

import com.google.gson.annotations.SerializedName

data class AuthData(
    val email: String,
    val password: String,
    var username: String? = null,
    var fullname: String? = null,
    @SerializedName("notification_key") var notificationKey: String? = null,
    var bio: String? = null,
    @SerializedName("firebase_uid") var firebaseUid:String? = null
)
