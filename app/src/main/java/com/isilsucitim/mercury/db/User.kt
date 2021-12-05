package com.isilsucitim.mercury.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "users", indices = [Index(value = ["username"], unique = true)])
data class User(
    @PrimaryKey val uid: String,
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "full_name") val fullName: String,
    @ColumnInfo(name = "photo") val photo: String?,
    @ColumnInfo(name = "bio") val bio: String,
    @ColumnInfo(name = "muted" , defaultValue = "0") val muted: Boolean,
    @ColumnInfo(name = "draft") val draft: String,
    @ColumnInfo(name = "blocked" , defaultValue = "0") val blocked: Boolean = false,
    )