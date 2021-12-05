package com.isilsucitim.mercury.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "messages",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["username"],
        childColumns = ["username"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Message(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "username") val user: String,
    @ColumnInfo(name = "data") val data: String,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "needs_push") val needsPush: String,
    @ColumnInfo(name = "status") val status: String, // READ, UNREAD, LIKED, DELETED
    @ColumnInfo(name = "created_at", defaultValue = "current_timestamp") val createdAt: Long,
    @ColumnInfo(name = "from_me", defaultValue = "0") val fromMe: Boolean,
)