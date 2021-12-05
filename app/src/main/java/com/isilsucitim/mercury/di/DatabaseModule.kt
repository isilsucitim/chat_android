package com.isilsucitim.mercury.di

import com.isilsucitim.mercury.db.AppDatabase
import com.isilsucitim.mercury.db.DatabaseRepository
import com.isilsucitim.mercury.db.MessageDao
import com.isilsucitim.mercury.db.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Provides
    fun provideMessageDao(appDatabase: AppDatabase): MessageDao {
        return appDatabase.messageDao()
    }
    @Provides
    fun provideUserDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao()
    }
    @Provides
    fun provideDBRepository(userDao: UserDao, messageDao: MessageDao) = DatabaseRepository(userDao,messageDao)

}