package com.sungil.runningproejct_mvvm.login.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sungil.runningproejct_mvvm.login.viewModel.LoginViewModel
import com.sungil.runningproejct_mvvm.repository.LoginRepository
import com.sungil.runningproejct_mvvm.repository.loginImpl.LoginRepoImpl

/**
 * 로그인 viewModel 을 위한 Factory
 * 왜 쓰며
 * 왜 안썯 괜찮으며
 * 안드로이드 x 의 viewModel 참고해봐라
 * 내부적으로 Factory 을 만드어서 쓸수도 있음
 * viewModel Provider 와 연관도있으며 안씀녀 어떠한 현상이 나타는지 확인
 */
class LoginFactory(private val context : Context) : ViewModelProvider.Factory {
    //여기서 repo을 넣어주는것도 생각 -> 멘토님
    //view에 의존성이 있는 값은 줘도 괜찮음
    //hilt 대체 가능
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(LoginViewModel :: class.java)){
            return LoginViewModel(LoginRepoImpl(context)) as T
        }
        throw IllegalArgumentException("UnKnow ViewModel class")
    }
}