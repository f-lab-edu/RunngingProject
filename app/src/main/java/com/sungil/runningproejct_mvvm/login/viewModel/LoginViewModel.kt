package com.sungil.runningproejct_mvvm.login.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sungil.runningproejct_mvvm.`object`.LoginData
import com.sungil.runningproejct_mvvm.repository.LoginRepository
import com.sungil.runningproejct_mvvm.utill.ListenerMessage
import com.sungil.runningproejct_mvvm.utill.RepositoryListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Login viewModel
 */
class LoginViewModel(private val repository: LoginRepository) : ViewModel(), RepositoryListener {
    //LiveData 여러개 쓰던가
    //LiveData 하나만?->
    private var _loginLiveData = MutableLiveData<LoginStatus<String>>()
    val loginLiveData get() = _loginLiveData

    //로그인 요청
    fun requestLogin(data: LoginData) = viewModelScope.launch(Dispatchers.IO) {
//        _loginLiveData.postValue(LoginStatus<String>(null , null).loadingForLogin())
         _loginLiveData.postValue(LoginStatus.loadingForLogin())
        repository.requestLogin(data, this@LoginViewModel)
    }

    //Firebase 에 대한 응답 Listener
    override fun onMessageSuccess(data: ListenerMessage) {
//        _loginLiveData.postValue(LoginStatus<String>(data.message as UserInfo , null ).successForLogin())
        _loginLiveData.postValue(LoginStatus.loginSuccess(data.message))
    }

    override fun onMessageFail(data: ListenerMessage) {
//        _loginLiveData.postValue(Resource.error(data.message))
        _loginLiveData.postValue(LoginStatus.loginError(data.message))
    }

    data class LoginStatus<out T>(val status : LoadingStatus, val successMessage: String ?, val exception : String?){
        enum class LoadingStatus{
            LOADING,
            SUCCESS,
            ERROR
        }
        companion object{
            fun <T> loadingForLogin() : LoginStatus<T>{
                return LoginStatus(LoadingStatus.LOADING ,  null , null )
            }

            fun <T> loginSuccess(data : String) :  LoginStatus<T> {
                return LoginStatus(LoadingStatus.SUCCESS , data ,  null )
            }

            fun <T> loginError(message: String) : LoginStatus<T> {
                return LoginStatus(LoadingStatus.ERROR , null , message)
            }
        }
    }
}