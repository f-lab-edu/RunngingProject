package com.sungil.runningproejct_mvvm.login.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sungil.database.entity.UserInfoDBM
import com.sungil.database.entity.toDomainModel
import com.sungil.device.entity.LoginData
import com.sungil.runningproejct_mvvm.domain.entity.UserInfoData
import com.sungil.runningproejct_mvvm.repository.LoginRepository
import com.sungil.runningproejct_mvvm.utill.Define
import com.sungil.runningproejct_mvvm.utill.ListenerMessage
import com.sungil.runningproejct_mvvm.utill.RepositoryListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Login viewModel Hilt 적용
 */
@HiltViewModel
class LoginViewModel @Inject constructor (private val repository: LoginRepository) : ViewModel(), RepositoryListener {
    private var _loginLiveData = MutableLiveData<LoginStatus>()
    val loginLiveData get() = _loginLiveData

    //이전 함수 시작할때부터 코루틴 -> 이제 Repository 들어갈떄만 코루틴
    fun requestLogin(data: LoginData){
        _loginLiveData.value = LoginStatus.LoginLoading
        viewModelScope.launch (Dispatchers.IO){
            repository.requestLogin(data, this@LoginViewModel)
        }
    }

    //Firebase 에 대한 응답 Listener
    override fun onMessageSuccess(data: ListenerMessage) {
        when(data.message){
            Define.PROP_SAVE_USERINFO ->{
                val userInfo = data.data as UserInfoData
//                val userInfo = UserInfoData(data.data.id , data.data.password , data.data.phoneNumber , data.data.nickName)
                repository.saveUserInfo(userInfo , this )
            }
            else -> _loginLiveData.setValue(LoginStatus.LoginSuccess(data.message))
        }
    }

    override fun onMessageFail(data: ListenerMessage) {
        _loginLiveData.value = LoginStatus.LoginError(data.message)
    }
    //data class Resource -> sealed class 로 변경
    sealed class LoginStatus {
        data class LoginSuccess(val data : String) : LoginStatus()
        data class LoginError(val message : String) : LoginStatus()

        object LoginLoading : LoginStatus()

    }
}