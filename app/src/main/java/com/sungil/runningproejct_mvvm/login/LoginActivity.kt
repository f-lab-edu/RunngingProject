package com.sungil.runningproejct_mvvm.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sungil.runningproejct_mvvm.R
import com.sungil.runningproejct_mvvm.databinding.ActivityLoginBinding
import com.sungil.runningproejct_mvvm.login.factory.LoginFactory
import com.sungil.runningproejct_mvvm.login.viewModel.LoginViewModel
import com.sungil.runningproejct_mvvm.`object`.LoginData
import com.sungil.runningproejct_mvvm.repository.loginImpl.LoginRepoImpl
import com.sungil.runningproejct_mvvm.utill.Define
import com.sungil.runningproejct_mvvm.utill.Resource
import timber.log.Timber

/**
 * 로그인 화묜
 */
class LoginActivity : AppCompatActivity() {

    private lateinit var  binding : ActivityLoginBinding

    private var _viewModel : LoginViewModel ?= null
    private val viewModel get() = _viewModel!!

    private val repository = LoginRepoImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        addListener()
    }
    //viewModel init
    private fun init(){
        val factory = LoginFactory(repository)
        _viewModel = ViewModelProvider(this , factory)[LoginViewModel :: class.java]
    }

    private fun addListener(){
        //개발자 용
        binding.icIcon.setOnClickListener {
            binding.editId.setText("sonny132@naver.com")
            binding.editPassword.setText("12345")
        }

        //로그인 LiveData
        viewModel.loginLiveData.observe(this , Observer {
            when(it.status){
                Resource.Status.LOADING ->{
                    Timber.d("Loading for Login")
                    Toast.makeText(this , getString(R.string.msg_login_loading), Toast.LENGTH_SHORT ).show()
                }
                Resource.Status.SUCCESS ->{
                    Timber.d("Success for Login")
                    Toast.makeText(this , getString(R.string.msg_success_login), Toast.LENGTH_SHORT ).show()
                }
                Resource.Status.ERROR ->{
                    Timber.e("ERROR to Login")
                    when(it.exception){
                        Define.PROP_MESSAGE_NO_USER ->{
                            Toast.makeText(this , getString(R.string.msg_check_login_data), Toast.LENGTH_SHORT ).show()
                        }
                        Define.PROP_MESSAGE_CHECK_LOGIN_DATA ->{
                            Toast.makeText(this , getString(R.string.msg_un_correct_login_data), Toast.LENGTH_SHORT ).show()
                        }
                        else ->  Toast.makeText(this ,getString(R.string.msg_check_network) , Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
        //로그인시 회원정보가 있는지 판별
        binding.btnLogin.setOnClickListener {
            val id = binding.editId.text.toString().replace(" ","").replace(".","")
            val password = binding.editPassword.text.toString().replace(" ", "").replace(".","")
            if(id == "" || password ==""){
                Timber.d("id or password is Null")
                Toast.makeText(this , getString(R.string.msg_pls_input_userinfo) , Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val data = LoginData(id , password)
            viewModel.requestLogin(data)
        }

        //회원가입 activity로 가기 위한
        binding.layoutBottom.setOnClickListener {
            Timber.d("The User Select Sign Up")
            val intent = Intent(this , SignUpActivity :: class.java)
            startActivity(intent)
        }
    }
}