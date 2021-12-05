package com.isilsucitim.mercury.db

import androidx.room.*

@Dao
interface UserDao {
    @Query("select * from users")
    fun getAll():List<User>

    @Query("select * from users where username = :username limit 1")
    fun getByUserName(username:String): User

    @Insert
    fun insertAll(vararg users:User)

    @Insert
    fun insertUser(user:User)

    @Delete
    fun deleteUser(user:User)

    @Update
    fun updateUser(user:User)

}