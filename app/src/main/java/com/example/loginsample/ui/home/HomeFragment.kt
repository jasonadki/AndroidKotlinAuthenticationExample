package com.example.loginsample.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.loginsample.data.network.Resource
import com.example.loginsample.data.network.UserApi
import com.example.loginsample.data.repository.UserRepository
import com.example.loginsample.data.responses.UserResponse
import com.example.loginsample.databinding.FragmentHomeBinding
import com.example.loginsample.ui.base.BaseFragment
import com.example.loginsample.ui.visible
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking


class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding, UserRepository>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)

        binding.progressbar.visible(false)

        viewModel.getUser()

        viewModel.userResponse.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success -> {
                    binding.progressbar.visible(false)
                    updateUI(it.value)
                }

                is Resource.Loading -> {
                    binding.progressbar.visible(true)

                }

                else -> {}
            }
        })
    }

    private fun updateUI(userResponse: UserResponse) {
        with(binding){
            textViewGreeting.text = "Hello " + userResponse.email
        }
    }

    override fun getViewModel() = HomeViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHomeBinding.inflate(inflater, container, false)

    override fun getFragmentRepository(): UserRepository {
        val token = runBlocking { userPreferences.authToken.first() }
        val api = remoteDataSource.buildApi(UserApi::class.java, token)

        return UserRepository(api)

    }



}