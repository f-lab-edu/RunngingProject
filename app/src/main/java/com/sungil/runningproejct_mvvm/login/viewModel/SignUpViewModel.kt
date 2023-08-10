package com.sungil.runningproejct_mvvm.login.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sungil.runningproejct_mvvm.R
import com.sungil.runningproejct_mvvm.`object`.UserInfo
import com.sungil.runningproejct_mvvm.repository.LoginRepository
import com.sungil.runningproejct_mvvm.utill.ListenerMessage
import com.sungil.runningproejct_mvvm.utill.RepositoryListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * 회원가입 viewModel
 */
class SignUpViewModel (private val repository: LoginRepository , private val context : Context) : ViewModel() , RepositoryListener{
    //이미 회원정보인지 확인하기 위해 선언한 LiveData
    private var _signUpCheckLiveData = MutableLiveData<SignUpStatus>()
    val signUpCheckLiveData get() = _signUpCheckLiveData

    //회원가입을 위해 선언한 LiveData
    private var _signUpLiveData = MutableLiveData<SignUpStatus>()
    val signUpLiveData get() = _signUpLiveData

    //user정보 latiner 은 이값은 초기화을 명시적으로
    private  var userInfo : UserInfo ? = null

    //중복회원 확인
    fun checkAlreadySignUp(data : UserInfo) {

        _signUpCheckLiveData.value = SignUpStatus.SignUpLoading

        userInfo = data
        viewModelScope.launch(Dispatchers.IO){
            repository.requestCheckUserInfo(data , this@SignUpViewModel)
        }
    }

    //회원가입 요청
    private fun requestSignUp()  {
        _signUpLiveData.value = SignUpStatus.SignUpLoading
        viewModelScope.launch (Dispatchers.IO){
            repository.requestSignUp(userInfo!! , this@SignUpViewModel)
        }
    }


    //FIrebase 에서 응답할떄 listener 로 받아서 LiveData에 값 주입
    override fun onMessageSuccess(data: ListenerMessage) {
        when(data.message){
            context.getString(R.string.msg_signup_okay) ->{
                _signUpCheckLiveData.value = SignUpStatus.SignUpSuccess(data.message)
                requestSignUp()
            }
            else ->   _signUpLiveData.value = SignUpStatus.SignUpSuccess(context.getString(R.string.msg_success_signup))
        }

    }

    override fun onMessageFail(data: ListenerMessage) {
        _signUpCheckLiveData.value = SignUpStatus.SignUpError(data.message)
    }

    override fun onCleared() {
        super.onCleared()
        Timber.d("The SignUpViewModel is Clear")
    }

    sealed class SignUpStatus {

        object SignUpLoading: SignUpStatus()
        data class SignUpSuccess(val data : String) : SignUpStatus()

        data class SignUpError(val message : String) : SignUpStatus()
    }

}



