package com.sungil.runningproejct_mvvm.main.viewModel

import androidx.lifecycle.ViewModel
import com.sungil.runningproejct_mvvm.repository.MainRepository
import com.sungil.runningproejct_mvvm.utill.ListenerMessage
import com.sungil.runningproejct_mvvm.utill.RepositoryListener
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
//MainViewModel inject
@HiltViewModel
class MainViewModel @Inject constructor  (private val repository: MainRepository ): ViewModel() , RepositoryListener {
    //repository
    var wearLiveData = repository.getRunningRoomDB()

    var followerLiveData =repository.getFollowerLiveData()

    var followerPostLiveData = repository.getFollowerPostLiveData()

    var unFollowerPostLiveData = repository.getUnFollowerPostLiveData()

    var setFollowerLiveData = repository.getSetFollowerLiveData()

    private var follower = ArrayList<String>()
    fun getFollower(){
        val data = repository.getUserInfo()
        if (data != null) {
            repository.getFollower(data.id)
        }
    }

    fun getFollowerPost() {
        repository.getFollowerPost(follower)
    }

    fun getUnFollowerPost() {
        repository.getUnFollowerPost(follower)
    }

    fun writeNewFollower(userId : String){
        val data = repository.getUserInfo() ?: return
        repository.setNewFollower(userId , data.id)
    }
    fun setFollower(followers : ArrayList<String>){
        this.follower = followers
    }

    override fun onMessageSuccess(data: ListenerMessage) {
        TODO("Not yet implemented")
    }

    override fun onMessageFail(data: ListenerMessage) {
        TODO("Not yet implemented")
    }

}