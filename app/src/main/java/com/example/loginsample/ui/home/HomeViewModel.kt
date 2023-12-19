package com.example.loginsample.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginsample.data.network.Resource
import com.example.loginsample.data.repository.UserRepository
import com.example.loginsample.data.responses.UserResponse
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: UserRepository
): ViewModel() {

    private val _userResponse: MutableLiveData<Resource<UserResponse>> = MutableLiveData()
    val userResponse: LiveData<Resource<UserResponse>>
        get() = _userResponse


    fun getUser() = viewModelScope.launch {
        _userResponse.value = Resource.Loading
        _userResponse.value = repository.getUser()
    }


}