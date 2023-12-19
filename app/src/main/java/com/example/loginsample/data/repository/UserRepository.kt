package com.example.loginsample.data.repository

import com.example.loginsample.data.UserPreferences
import com.example.loginsample.data.network.AuthApi
import com.example.loginsample.data.network.UserApi


class UserRepository(
    private val api: UserApi,
) : BaseRepository(){

    suspend fun getUser() = safeApiCall {
        api.getUser()
    }
}