package com.sungil.runningproejct_mvvm.login.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sungil.runningproejct_mvvm.`object`.LoginData
import com.sungil.runningproejct_mvvm.repository.LoginRepository
import com.sungil.runningproejct_mvvm.utill.ListenerMessage
import com.sungil.runningproejct_mvvm.utill.RepositoryListener
import com.sungil.runningproejct_mvvm.utill.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Login viewModel
 */
class LoginViewModel(private val repository: LoginRepository) : ViewModel(), RepositoryListener {

    private var _loginLiveData = MutableLiveData<Resource<String>>()
    val loginLiveData get() = _loginLiveData

    //로그인 요청
    fun requestLogin(data: LoginData) = viewModelScope.launch(Dispatchers.IO) {
        _loginLiveData.postValue(Resource.loading())

        repository.requestLogin(data, this@LoginViewModel)
    }

    //Firebase 에 대한 응답 Listener
    override fun onMessageSuccess(data: ListenerMessage) {
        _loginLiveData.postValue(Resource.success(data.message))
    }

    override fun onMessageFail(data: ListenerMessage) {
        _loginLiveData.postValue(Resource.error(data.message))
    }
}