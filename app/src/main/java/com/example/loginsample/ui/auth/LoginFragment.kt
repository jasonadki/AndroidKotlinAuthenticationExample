package com.example.loginsample.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.loginsample.R
import com.example.loginsample.databinding.FragmentLoginBinding
import com.example.loginsample.data.network.AuthApi
import com.example.loginsample.data.network.Resource
import com.example.loginsample.data.repository.AuthRepository
import com.example.loginsample.ui.base.BaseFragment
import com.example.loginsample.ui.enable
import com.example.loginsample.ui.handleApiError
import com.example.loginsample.ui.home.HomeActivity
import com.example.loginsample.ui.startNewActivity
import com.example.loginsample.ui.visible
import kotlinx.coroutines.launch

class LoginFragment : BaseFragment<AuthViewModel, FragmentLoginBinding, AuthRepository>() {


    override fun onActivityCreated(savedInstanceState: Bundle?){
        super.onActivityCreated(savedInstanceState)

        binding.progressbar.visible(false)
        binding.buttonSubmit.enable(false)

        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            // Make progress bar not visible
            binding.progressbar.visible(it is Resource.Loading)
            when(it){
                is Resource.Success -> {
                        lifecycleScope.launch {
                            // Save the access token to the store
                            viewModel.saveAuthToken(it.value.access, it.value.refresh)

                            // Go to home activity
                            requireActivity().startNewActivity(HomeActivity::class.java)
                        }
                }
                is Resource.Failure -> handleApiError(it) { login() }

                else -> {}
            }

        })


        binding.editTextPassword.addTextChangedListener{
            val email = binding.editTextEmail.text.toString().trim()
            binding.buttonSubmit.enable(email.isNotEmpty() && it.toString().isNotEmpty())
        }

        binding.buttonSubmit.setOnClickListener{
            login()
        }
    }

    private fun login(){
        val email = binding.editTextEmail.text.toString().trim()
        val password = binding.editTextPassword.text.toString().trim()

        //@todo add validations
        viewModel.login(email, password)
    }

    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentLoginBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() = AuthRepository(remoteDataSource.buildApi(AuthApi::class.java), userPreferences)


}