package com.example.loginsample.data.network

import com.example.loginsample.data.responses.UserResponse
import retrofit2.http.GET

interface UserApi {

    @GET("/accounts/1/")
    suspend fun getUser(): UserResponse
}