package com.example.loginsample.data.network

import com.example.loginsample.data.responses.UserResponse
import okhttp3.ResponseBody
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApi {

    @GET("/accounts/1/")
    suspend fun getUser(): UserResponse

    @POST("/accounts/logout/")
    suspend fun logout(
        @Field("refreshToken") refreshToken: String,
        ): ResponseBody
}