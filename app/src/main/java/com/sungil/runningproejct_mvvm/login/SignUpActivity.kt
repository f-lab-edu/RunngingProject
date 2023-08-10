package com.sungil.runningproejct_mvvm.login


import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.firebase.BuildConfig
import com.sungil.runningproejct_mvvm.MainApplication
import com.sungil.runningproejct_mvvm.R
import com.sungil.runningproejct_mvvm.databinding.ActivitySignUpBinding
import com.sungil.runningproejct_mvvm.login.factory.SignUpFactory
import com.sungil.runningproejct_mvvm.login.viewModel.SignUpViewModel
import com.sungil.runningproejct_mvvm.`object`.UserInfo
import timber.log.Timber

/**
 * 회원가입 Activity
 */
class SignUpActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySignUpBinding

    private val viewModel : SignUpViewModel by viewModels{SignUpFactory(MainApplication.appContext) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observer()
    }

    private fun observer(){
        //종복회원인지 확인 LiveData
        viewModel.signUpCheckLiveData.observe(this  , Observer {signUpCheck ->
            when(signUpCheck){
                SignUpViewModel.SignUpStatus.SignUpLoading ->{
                    Timber.d("Loading for check SignUp")
                }
                is SignUpViewModel.SignUpStatus.SignUpSuccess ->{
                    Timber.d("Success to get SignUp")
                    Toast.makeText(this ,getString(R.string.msg_signup_okay) , Toast.LENGTH_SHORT).show()
                }
                is SignUpViewModel.SignUpStatus.SignUpError ->{
                    Timber.e("ERROR to SignUp")
                    Toast.makeText(this , signUpCheck.message , Toast.LENGTH_SHORT).show()
                }
            }
        })

        //중복회원이 아닐시 회원가입 LiveData
        viewModel.signUpLiveData.observe(this , Observer {signUp ->
            when(signUp){
                SignUpViewModel.SignUpStatus.SignUpLoading ->{
                    Timber.d("Loading for SignUp")
                }
                is SignUpViewModel.SignUpStatus.SignUpSuccess ->{
                    Timber.d("Success to SignUp")
                    Toast.makeText(this ,getString(R.string.msg_success_signup) , Toast.LENGTH_SHORT).show()
                    finish()
                }
                is SignUpViewModel.SignUpStatus.SignUpError ->{
                    Toast.makeText(this ,getString(R.string.msg_check_network) , Toast.LENGTH_SHORT).show()
                }
            }
        })

        //개발자용
        binding.icIcon.setOnClickListener {
            if(!BuildConfig.DEBUG ){
                binding.editId.setText("sonny8569@naver.com")
                binding.editPassword.setText("12345")
                binding.editNickName.setText("김성일")
                binding.editPhoneNumber.setText("01088014672")
            }
        }

        //회원정보 입력을 확실히 했는지 체크 -> 중복회원 검사
        binding.btnLogin.setOnClickListener {
            val id = binding.editId.text.toString().trim().replace(".","")
            val password = binding.editPassword.text.toString().trim()
            val nickName = binding.editNickName.text.toString().trim()
            val phoneNumber = binding.editPhoneNumber.text.toString().trim()
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
    }
}