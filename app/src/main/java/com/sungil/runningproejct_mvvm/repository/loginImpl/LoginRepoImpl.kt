package com.sungil.runningproejct_mvvm.repository.loginImpl

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.sungil.runningproejct_mvvm.`object`.LoginData
import com.sungil.runningproejct_mvvm.`object`.UserInfo
import com.sungil.runningproejct_mvvm.repository.LoginRepository
import com.sungil.runningproejct_mvvm.utill.Define
import com.sungil.runningproejct_mvvm.utill.ListenerMessage
import com.sungil.runningproejct_mvvm.utill.RepositoryListener
import timber.log.Timber


/**
 * 로그인 및 회원가입 Repositorty 구현체
 */
class LoginRepoImpl () : LoginRepository {

    override suspend fun requestCheckUserInfo(data: UserInfo , Listener : RepositoryListener)  {
        val database = Firebase.database(Define.FIREBASE_BASE_URL)
        val userInfoUrl = Define.FIREBASE_USERINFO_URL+"/"+data.id

        database.getReference(userInfoUrl).addListenerForSingleValueEvent(object  : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.getValue<UserInfo>()
                if(value == null){
                    Timber.e("The User Data is Null")
                    Listener.onMessageSuccess(ListenerMessage(data , Define.PROP_MESSAGE_SIGN_UP_OKAY))
                    return
                }
                Timber.e("Already Sign Up")
                Listener.onMessageFail(ListenerMessage(null , Define.PROP_MESSAGE_SIGN_UP_NOT_OKAY))
            }
            override fun onCancelled(error: DatabaseError) {
                Timber.e("The Error to get UserInfo")
                Listener.onMessageFail(ListenerMessage(null , Define.PROP_MESSAGE_ERROR_TO_NETWORK))
            }
        })
    }

    override suspend fun requestSignUp(data: UserInfo, Listener: RepositoryListener) {
        val database = Firebase.database(Define.FIREBASE_BASE_URL)
        val ref: DatabaseReference = database.getReference("/" + Define.FIREBASE_USERINFO_URL)
        ref.child(data.id).setValue(
            data,
            DatabaseReference.CompletionListener { databaseError, databaseReference ->
                if (databaseError != null) {
                    Timber.e("Data could not be saved ${databaseError.message}")
                    Listener.onMessageFail(ListenerMessage(null ,Define.PROP_MESSAGE_SIGN_UP_FAIL ))
                    return@CompletionListener
                }
                Timber.d("Data saved successfully.")
                Listener.onMessageSuccess(ListenerMessage(data  ,Define.PROP_MESSAGE_SIGN_UP_SUCCESS ))
            })
    }

    override suspend fun requestLogin(data: LoginData, Listener: RepositoryListener) {
        val database = Firebase.database(Define.FIREBASE_BASE_URL)
        val userInfoUrl = Define.FIREBASE_USERINFO_URL+"/"+data.id


        database.getReference(userInfoUrl).addListenerForSingleValueEvent(object  : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.getValue<UserInfo>()
                if(value == null){
                    Timber.e("The User Data is Null")
                    Listener.onMessageFail(ListenerMessage(null , Define.PROP_MESSAGE_NO_USER))
                    return
                }
                if(value.id == data.id && value.password == data.password){
                    Listener.onMessageSuccess(ListenerMessage(null  , Define.PROP_MESSAGE_SUCCESS_LOGIN))
                    return
                }
                Listener.onMessageFail(ListenerMessage(null , Define.PROP_MESSAGE_CHECK_LOGIN_DATA))
            }
            override fun onCancelled(error: DatabaseError) {
                Timber.e("The Error to get UserInfo")
                Listener.onMessageFail(ListenerMessage(null , Define.PROP_MESSAGE_ERROR_TO_NETWORK))
            }
        })
    }
}