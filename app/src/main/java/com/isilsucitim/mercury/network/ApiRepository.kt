package com.isilsucitim.mercury.network

import com.isilsucitim.mercury.network.dto.AuthData
import retrofit2.http.Body

class ApiRepository(private val apiService:ApiService) {
    suspend fun login(@Body body: AuthData) = apiService.login(body)
}