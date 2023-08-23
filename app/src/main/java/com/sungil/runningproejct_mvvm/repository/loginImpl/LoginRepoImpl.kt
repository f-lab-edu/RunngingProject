package com.sungil.runningproejct_mvvm.repository.loginImpl

import android.content.Context
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.sungil.runningproejct_mvvm.R
import com.sungil.runningproejct_mvvm.appDatabase.UserInfoDao
import com.sungil.runningproejct_mvvm.dataObject.LoginData
import com.sungil.runningproejct_mvvm.dataObject.UserInfoDBM
import com.sungil.runningproejct_mvvm.repository.LoginRepository
import com.sungil.runningproejct_mvvm.utill.Define
import com.sungil.runningproejct_mvvm.utill.ListenerMessage
import com.sungil.runningproejct_mvvm.utill.RepositoryListener
import timber.log.Timber
import javax.inject.Inject
import kotlin.Exception


//로그인 Reop Hilt 주입
class LoginRepoImpl @Inject constructor(private val context : Context,  private val userInfoDao: UserInfoDao ) : LoginRepository {

    private val database = Firebase.database(Define.FIREBASE_BASE_URL)
    private val userInfoURL = Define.FIREBASE_USERINFO_URL
    private val loginImplContext = context
    private var userInfo : UserInfoDao = userInfoDao
    override suspend fun requestCheckUserInfo(data: UserInfoDBM, Listener : RepositoryListener)  {
        val myUserInfoUrl = userInfoURL+"/"+data.id

        database.getReference(myUserInfoUrl).addListenerForSingleValueEvent(object  : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.getValue<UserInfoDBM>()
                if(value == null){
                    Timber.e("The User Data is Null")
//                    Listener.onMessageSuccess(ListenerMessage(data , Define.PROP_MESSAGE_SIGN_UP_OKAY))
                    Listener.onMessageSuccess(ListenerMessage(data , loginImplContext.getString(R.string.msg_signup_okay)))
                    return
                }
                Timber.e("Already Sign Up")
                Listener.onMessageFail(ListenerMessage(null , loginImplContext.getString(R.string.msg_already_signup)))
            }
            override fun onCancelled(error: DatabaseError) {
                Timber.e("The Error to get UserInfo")
                Listener.onMessageFail(ListenerMessage(null , loginImplContext.getString(R.string.msg_check_network)))
            }
        })
    }

    override suspend fun requestSignUp(data: UserInfoDBM, Listener: RepositoryListener) {
        val ref: DatabaseReference = database.getReference("/" + Define.FIREBASE_USERINFO_URL)
        ref.child(data.id).setValue(
            data,
            DatabaseReference.CompletionListener { databaseError, databaseReference ->
                if (databaseError != null) {
                    Timber.e("Data could not be saved ${databaseError.message}")
                    Listener.onMessageFail(ListenerMessage(null , loginImplContext.getString(R.string.msg_check_network)))
                    return@CompletionListener
                }
                Timber.d("Data saved successfully.")
                Listener.onMessageSuccess(ListenerMessage(data  , loginImplContext.getString(R.string.msg_success_signup)))
            })
    }

    override suspend fun requestLogin(data: LoginData, Listener: RepositoryListener) {
        val myUserInfoUrl = Define.FIREBASE_USERINFO_URL+"/"+data.id


        database.getReference(myUserInfoUrl).addListenerForSingleValueEvent(object  : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.getValue<UserInfoDBM>()
                if(value == null){
                    Timber.e("The User Data is Null")
                    Listener.onMessageFail(ListenerMessage(null , loginImplContext.getString(R.string.msg_check_login_data)))
                    return
                }
                if(value.id == data.id && value.password == data.password){
                    Listener.onMessageSuccess(ListenerMessage(value  , Define.PROP_SAVE_USERINFO))
                    return
                }
                Listener.onMessageFail(ListenerMessage(null , loginImplContext.getString(R.string.msg_check_login_data)))
            }
            override fun onCancelled(error: DatabaseError) {
                Timber.e("The Error to get UserInfo")
                Listener.onMessageFail(ListenerMessage(null , loginImplContext.getString(R.string.msg_check_network)))
            }
        })
    }

    override fun saveUserInfo(data: UserInfoDBM, listener: RepositoryListener) {
        try{
            val userData = userInfo.getUserInfo()
            if(userData == null){
                userInfo.insert(data)
            }else{
                if(userData.id == data.id && userData.password == data.password){
                    userInfo.update(data)
                }else{
                    userInfo.delete(userData)
                    userInfo.insert(data)
                }
            }
            listener.onMessageSuccess(ListenerMessage(null  , loginImplContext.getString(R.string.msg_success_login)))
        }catch (e : Exception){
            listener.onMessageFail(ListenerMessage(null , loginImplContext.getString(R.string.msg_error_app)))
        }
    }
}