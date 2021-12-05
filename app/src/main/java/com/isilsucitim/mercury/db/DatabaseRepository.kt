package com.isilsucitim.mercury.db

import javax.inject.Inject

class DatabaseRepository @Inject constructor(private val userDao: UserDao,private val messageDao: MessageDao){

    suspend fun insertMessage(message: Message) = messageDao.insert(message)
    suspend fun deleteMessage(message: Message) = messageDao.delete(message)
    suspend fun insertAllMessages(vararg messages: Message) = messageDao.insertAll(*messages)
    suspend fun getLastMessageByUsername(username: String) = messageDao.getLastMessageByUsername(username)
    suspend fun getMessagesByUsername(username: String) = messageDao.getMessagesByUsername(username)

    suspend fun getAllUsers() = userDao.getAll()
    suspend fun getUserByUsername(username: String) = userDao.getByUserName(username)
    suspend fun insertAllUsers(vararg users: User) = userDao.insertAll(*users)
    suspend fun insertUser(user: User) = userDao.insertUser(user)
    suspend fun deleteUser(user: User) = userDao.deleteUser(user)
    suspend fun updateUser(user: User) = userDao.updateUser(user)

}