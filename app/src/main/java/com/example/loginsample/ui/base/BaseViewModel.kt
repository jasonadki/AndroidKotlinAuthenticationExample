package com.example.loginsample.ui.base

import androidx.lifecycle.ViewModel
import com.example.loginsample.data.network.UserApi
import com.example.loginsample.data.repository.BaseRepository

abstract class BaseViewModel (
    private val repository: BaseRepository
): ViewModel(){


    suspend fun logout(api: UserApi, refreshToken: String?) =
        refreshToken?.let { repository.logout(api, it) }
}