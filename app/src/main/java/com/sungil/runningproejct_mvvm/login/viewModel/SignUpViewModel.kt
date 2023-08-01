package com.sungil.runningproejct_mvvm.login.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sungil.runningproejct_mvvm.`object`.UserInfo
import com.sungil.runningproejct_mvvm.repository.LoginRepository
import com.sungil.runningproejct_mvvm.utill.Define
import com.sungil.runningproejct_mvvm.utill.ListenerMessage
import com.sungil.runningproejct_mvvm.utill.RepositoryListener
import com.sungil.runningproejct_mvvm.utill.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * 회원가입 viewModel
 */
class SignUpViewModel (private val repository: LoginRepository) : ViewModel() , RepositoryListener{
    //이미 회원정보인지 확인하기 위해 선언한 LiveData
    private var _signUpCheckLiveData = MutableLiveData<Resource<String>>()
    val signUpCheckLiveData get() = _signUpCheckLiveData

    //회원가입을 위해 선언한 LiveData
    private var _signUpLiveData = MutableLiveData<Resource<String>>()
    val signUpLiveData get() = _signUpLiveData

    //user정보
    private lateinit var userInfo : UserInfo

    //중복회원 확인
    fun checkAlreadySignUp(data : UserInfo) = viewModelScope.launch(Dispatchers.IO) {

        _signUpCheckLiveData.postValue(Resource.loading())

        userInfo = data

        repository.requestCheckUserInfo(data , this@SignUpViewModel)
    }

    //회원가입 요청
    private fun requestSignUp() = viewModelScope.launch (Dispatchers.IO) {
        _signUpLiveData.postValue(Resource.loading())
        repository.requestSignUp(userInfo , this@SignUpViewModel)
    }


    //FIrebase 에서 응답할떄 listener 로 받아서 LiveData에 값 주입
    override fun onMessageSuccess(data: ListenerMessage) {
        when (data.message) {
            Define.PROP_MESSAGE_SIGN_UP_OKAY -> {
                Timber.d("SignUp okay")
                _signUpCheckLiveData.postValue(Resource.success(data.message))
                requestSignUp()

            }
            Define.PROP_MESSAGE_SIGN_UP_SUCCESS ->{
                _signUpLiveData.postValue(Resource.success(data.message))
            }
        }
    }

    override fun onMessageFail(data: ListenerMessage) {
        when (data.message) {
            Define.PROP_MESSAGE_SIGN_UP_FAIL -> {
                _signUpLiveData.postValue(Resource.error(data.message))
            }

            else -> _signUpCheckLiveData.postValue(Resource.error(data.message))
        }
    }

    override fun onCleared() {
        super.onCleared()
        Timber.d("The SignUpViewModel is Clear")
    }

}