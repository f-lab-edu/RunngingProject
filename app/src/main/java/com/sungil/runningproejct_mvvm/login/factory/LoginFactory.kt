package com.sungil.runningproejct_mvvm.login.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sungil.runningproejct_mvvm.login.viewModel.LoginViewModel
import com.sungil.runningproejct_mvvm.repository.LoginRepository

/**
 * 로그인 viewModel 을 위한 Factory
 */
class LoginFactory(private val repository: LoginRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(LoginViewModel :: class.java)){
            return LoginViewModel(repository) as T
        }
        throw IllegalArgumentException("UnKnow ViewModel class")
    }
}