package com.sungil.runningproejct_mvvm.login


import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sungil.runningproejct_mvvm.R
import com.sungil.runningproejct_mvvm.databinding.ActivitySignUpBinding
import com.sungil.runningproejct_mvvm.login.factory.SignUpFactory
import com.sungil.runningproejct_mvvm.login.viewModel.SignUpViewModel
import com.sungil.runningproejct_mvvm.`object`.UserInfo
import com.sungil.runningproejct_mvvm.repository.loginImpl.LoginRepoImpl
import com.sungil.runningproejct_mvvm.utill.Define
import com.sungil.runningproejct_mvvm.utill.Resource
import timber.log.Timber

/**
 * 회원가입 Activity
 */
class SignUpActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySignUpBinding

    private var _viewModel : SignUpViewModel ?= null
    private val viewModel get() = _viewModel!!

    private val repository = LoginRepoImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        addListener()
    }

    //viewModel init
    private fun init(){
        val factory = SignUpFactory(repository)
        _viewModel = ViewModelProvider(this  , factory)[SignUpViewModel :: class.java]
    }

    private fun addListener(){
        //종복회원인지 확인 LiveData
        viewModel.signUpCheckLiveData.observe(this  , Observer {
            when(it.status){
                Resource.Status.LOADING ->{
                    Timber.d("Loading for check SignUp")
                }
                Resource.Status.SUCCESS ->{
                    Timber.d("Success to get SignUp")
                    Toast.makeText(this ,getString(R.string.msg_signup_okay) , Toast.LENGTH_SHORT).show()
                }
                Resource.Status.ERROR ->{
                    Timber.e("ERROR to SignUp")
                    when(it.exception){
                        Define.PROP_MESSAGE_ERROR_TO_NETWORK ->{
                            Toast.makeText(this ,getString(R.string.msg_check_network) , Toast.LENGTH_SHORT).show()
                        }
                        Define.PROP_MESSAGE_SIGN_UP_NOT_OKAY ->{
                            Toast.makeText(this ,getString(R.string.msg_already_signup) , Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        })

        //중복회원이 아닐시 회원가입 LiveData
        viewModel.signUpLiveData.observe(this , Observer {
            when(it.status){
                Resource.Status.LOADING ->{
                    Timber.d("Loading for SignUp")
                }

                Resource.Status.SUCCESS ->{
                    Timber.d("Success to SignUp")
                    Toast.makeText(this ,getString(R.string.msg_success_signup) , Toast.LENGTH_SHORT).show()
                    finish()
                }

                Resource.Status.ERROR ->{
                    Toast.makeText(this ,getString(R.string.msg_check_network) , Toast.LENGTH_SHORT).show()
                }
            }
        })

        //개발자용
        binding.icIcon.setOnClickListener {
            binding.editId.setText("sonny132@naver.com")
            binding.editPassword.setText("12345")
            binding.editNickName.setText("김성일")
            binding.editPhoneNumber.setText("01088014672")
        }

        //회원정보 입력을 확실히 했는지 체크 -> 중복회원 검사
        binding.btnLogin.setOnClickListener {
            val id = binding.editId.text.toString().replace(" ","").replace(".","")
            val password = binding.editPassword.text.toString().replace(" ", "")
            val nickName = binding.editNickName.text.toString().replace(" ", "")
            val phoneNumber = binding.editPhoneNumber.text.toString().replace(" ","")
            if(id == "" || password ==""||nickName ==""||phoneNumber ==""){
                Timber.e("The UserInfo is Null")
                Toast.makeText(this , getString(R.string.msg_pls_input_userinfo) , Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val data = UserInfo(id, password, phoneNumber, nickName)
            viewModel.checkAlreadySignUp(data)
        }

        //로그인 창으로
        binding.layoutBottom.setOnClickListener {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _viewModel = null
    }
}