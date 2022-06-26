package com.karry.remote.api

import com.karry.data.dto.account.ResponseDTO
import com.karry.data.models.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {
    @POST("register")
    suspend fun register(@Body registrationData: User.Register): ResponseDTO

    @POST("login")
    suspend fun login(@Body auth: User.Login): ResponseDTO

    @POST("logout")
    suspend fun logout(@Header("Authorization") token: String): ResponseDTO

    @GET("whoami")
    suspend fun whoami(@Header("Authorization") token: String): ResponseDTO

}