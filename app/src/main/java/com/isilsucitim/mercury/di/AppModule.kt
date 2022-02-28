package com.isilsucitim.mercury.di

import android.content.Context
import com.isilsucitim.mercury.Application
import com.isilsucitim.mercury.Constants
import com.isilsucitim.mercury.network.ApiRepository
import com.isilsucitim.mercury.network.ApiService
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
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
//    @Provides
//    fun providesApplication(@ApplicationContext context: Context): Application {
//        return context as Application
//    }

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Constants.BASE_API_URL)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun providesRepository(apiService: ApiService) = ApiRepository(apiService)


//    @Provides
//    fun provideMoshi(): Moshi {
//        return Moshi.Builder().build()
//    }
//
//    @Provides
//    fun provideScarlet(application: Application, client: OkHttpClient, moshi: Moshi): Scarlet {
//        return Scarlet.Builder()
//            .webSocketFactory(client.newWebSocketFactory("wss://efde-46-1-174-237.ngrok.io/?token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6InVzZXJAaXNpbHN1LmNvbSIsInVzZXJuYW1lIjoic3VlIn0.dpBA5T76lYLvk6tK-NR_J-H2MrVuwT79mExQObcIKHU"))
//            .addMessageAdapterFactory(MoshiMessageAdapter.Factory(moshi))
//            .addStreamAdapterFactory(FlowStreamAdapter.Factory())
//            .lifecycle(AndroidLifecycle.ofApplicationForeground(application))
//            .build()
//    }
//
//    @Provides
//    fun provideChatService(scarlet: Scarlet): ChatService {
//        return scarlet.create()
//    }
}