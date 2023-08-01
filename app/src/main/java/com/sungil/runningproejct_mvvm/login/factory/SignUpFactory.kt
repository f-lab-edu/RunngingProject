package com.sungil.runningproejct_mvvm.login.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sungil.runningproejct_mvvm.login.viewModel.SignUpViewModel
import com.sungil.runningproejct_mvvm.repository.LoginRepository

/**
 * 회원가입 viewModel 을 위한 Facotry
 */
class SignUpFactory(private val repository: LoginRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SignUpViewModel :: class.java)){
            return SignUpViewModel(repository) as T
        }
        throw IllegalArgumentException("UnKnow ViewModel class")
    }
}