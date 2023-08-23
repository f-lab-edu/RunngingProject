package com.sungil.runningproejct_mvvm.repository

import androidx.lifecycle.LiveData
import com.sungil.runningproejct_mvvm.dataObject.PostData
import com.sungil.runningproejct_mvvm.dataObject.UserInfoDBM
import com.sungil.runningproejct_mvvm.dataObject.WearRunDataDBM

interface MainRepository {
    fun getRunningRoomDB(): LiveData<WearRunDataDBM?>

    fun getFollower(userId: String)

    fun getUserInfo() : UserInfoDBM?

    fun getFollowerLiveData () : LiveData<List<String>>

    fun getFollowerPost(follower: ArrayList<String>)

    fun setNewFollower(userId : String , myId : String)
    fun getFollowerPostLiveData ()  :LiveData<ArrayList<PostData>>

    fun getUnFollowerPost(follower: ArrayList<String>)

    fun getUnFollowerPostLiveData (): LiveData<ArrayList<PostData>>

    fun getSetFollowerLiveData () : LiveData<String>
}