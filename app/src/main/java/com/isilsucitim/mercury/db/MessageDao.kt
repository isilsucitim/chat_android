package com.isilsucitim.mercury.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MessageDao {

    @Query("SELECT * FROM messages WHERE username = :username")
    fun getMessagesByUsername(username: String): List<Message>

    @Query("SELECT * FROM messages WHERE username = :username LIMIT 1")
    fun getLastMessageByUsername(username: String): Message

    @Insert
    fun insertAll(vararg messages: Message)

    @Insert
    fun insert(message: Message)

    @Delete
    fun delete(message: Message)

}

