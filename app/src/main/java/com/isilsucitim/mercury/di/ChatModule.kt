package com.isilsucitim.mercury.di

import android.content.Context
import com.isilsucitim.mercury.Application
import com.isilsucitim.mercury.db.Storage
import com.isilsucitim.mercury.network.websocket.ChatService
import com.isilsucitim.mercury.network.websocket.FlowStreamAdapter
import com.squareup.moshi.Moshi
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.lifecycle.android.AndroidLifecycle
import com.tinder.scarlet.messageadapter.moshi.MoshiMessageAdapter
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient

@Module
@InstallIn(SingletonComponent::class)
class ChatModule {

    @Provides
    fun providesApplication(@ApplicationContext context: Context): Application {
        return context as Application
    }

    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder().build()
    }

//    @Provides
//    fun provideHTTPClient(): OkHttpClient {
//        return OkHttpClient.Builder().build()
//    }

    @Provides
    fun provideScarlet(application: Application, client: OkHttpClient, moshi: Moshi): Scarlet {
        return Scarlet.Builder()
            .webSocketFactory(client.newWebSocketFactory(Storage.getChatUrl(application)))
            .addMessageAdapterFactory(MoshiMessageAdapter.Factory(moshi))
            .addStreamAdapterFactory(FlowStreamAdapter.Factory())
            .lifecycle(AndroidLifecycle.ofApplicationForeground(application))
            .build()
    }

    @Provides
    fun provideChatService(scarlet: Scarlet): ChatService {
        return scarlet.create()
    }
}




