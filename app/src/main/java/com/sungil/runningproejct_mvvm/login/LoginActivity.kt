package com.sungil.runningproejct_mvvm.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.firebase.BuildConfig
import com.sungil.runningproejct_mvvm.MainApplication
import com.sungil.runningproejct_mvvm.R
import com.sungil.runningproejct_mvvm.databinding.ActivityLoginBinding
import com.sungil.runningproejct_mvvm.login.factory.LoginFactory
import com.sungil.runningproejct_mvvm.login.viewModel.LoginViewModel
import com.sungil.runningproejct_mvvm.main.MainActivity
import com.sungil.runningproejct_mvvm.`object`.LoginData
import timber.log.Timber

/**
 * 로그인 화묜
 */
class LoginActivity : AppCompatActivity() {

    private lateinit var  binding : ActivityLoginBinding

    private val viewModel : LoginViewModel by viewModels{LoginFactory(MainApplication.appContext)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observer()
    }

    private fun observer(){
        //개발자 용
        binding.icIcon.setOnClickListener {
            if(!BuildConfig.DEBUG){
                binding.editId.setText("sonny132@naver.com")
                binding.editPassword.setText("12345")
            }
        }

        viewModel.loginLiveData.observe(this , Observer {loginStatus ->
            when(loginStatus) {
                is LoginViewModel.LoginStatus.LoginLoading->{
                    Timber.d("Loading for Login")
                    Toast.makeText(this , getString(R.string.msg_login_loading), Toast.LENGTH_SHORT ).show()
                }

                is LoginViewModel.LoginStatus.LoginSuccess ->{
                    Timber.d("Success for Login")
                    Toast.makeText(this , getString(R.string.msg_success_login), Toast.LENGTH_SHORT ).show()
                    val intent = Intent(this , MainActivity :: class.java)
                    intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
                is LoginViewModel.LoginStatus.LoginError ->{
                    Timber.e("ERROR to Login")
                }
            }
        })

        binding.btnLogin.setOnClickListener {
            val id = binding.editId.text.toString().trim().replace(".","")
            val password = binding.editPassword.text.toString().trim().replace(".","")
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