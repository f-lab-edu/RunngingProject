package com.sungil.runningproejct_mvvm.login.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sungil.runningproejct_mvvm.login.viewModel.SignUpViewModel
import com.sungil.runningproejct_mvvm.repository.LoginRepository
import com.sungil.runningproejct_mvvm.repository.loginImpl.LoginRepoImpl

/**
 * 회원가입 viewModel 을 위한 Facotry
 */
class SignUpFactory(private val context : Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SignUpViewModel :: class.java)){
            return SignUpViewModel(LoginRepoImpl(context) , context) as T
        }
        throw IllegalArgumentException("UnKnow ViewModel class")
    }
}