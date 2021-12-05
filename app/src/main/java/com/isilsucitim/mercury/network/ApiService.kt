package com.isilsucitim.mercury.network

import com.isilsucitim.mercury.network.dto.AuthData
import com.isilsucitim.mercury.network.dto.ProfileDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("auth/")
    suspend fun login(@Body body:AuthData) :Response<ProfileDto>
}