package com.isilsucitim.mercury.db

import android.content.Context
import com.isilsucitim.mercury.Constants.BASE_CHAT_URL
import com.isilsucitim.mercury.Constants.SHARED_PREF_BIO
import com.isilsucitim.mercury.Constants.SHARED_PREF_FULL_NAME
import com.isilsucitim.mercury.Constants.SHARED_PREF_NAME
import com.isilsucitim.mercury.Constants.SHARED_PREF_PHOTO_URL
import com.isilsucitim.mercury.Constants.SHARED_PREF_TOKEN
import com.isilsucitim.mercury.Constants.SHARED_PREF_UID
import com.isilsucitim.mercury.Constants.SHARED_PREF_USER_NAME

class Storage {
    companion object {
        @JvmStatic
        fun isLogged(context: Context): Boolean {
            val prefs = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            val uid = prefs.getString(SHARED_PREF_UID, "")
            return !(uid == null || uid == "")
        }
        @JvmStatic
        fun saveUser(
            context: Context,
            username: String,
            token: String,
            uid: String,
            fullname: String,
            bio: String,
            photo: String
        ) {
            val prefs = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            prefs.edit().apply {
                putString(SHARED_PREF_UID, uid)
                putString(SHARED_PREF_USER_NAME, username)
                putString(SHARED_PREF_BIO, bio)
                putString(SHARED_PREF_TOKEN, token)
                putString(SHARED_PREF_FULL_NAME, fullname)
                putString(SHARED_PREF_PHOTO_URL, photo)
            }.apply()
        }
        @JvmStatic
        fun getToken(context: Context): String {
            val prefs = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return prefs.getString(SHARED_PREF_TOKEN, "")!!
        }

        @JvmStatic
        fun getChatUrl(context: Context): String {
            val prefs = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            val token = prefs.getString(SHARED_PREF_TOKEN, "")!!
            return "$BASE_CHAT_URL$token"
        }
    }

}