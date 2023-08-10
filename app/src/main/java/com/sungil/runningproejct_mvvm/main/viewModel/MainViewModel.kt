package com.sungil.runningproejct_mvvm.main.viewModel

import androidx.lifecycle.ViewModel
import com.sungil.runningproejct_mvvm.repository.MainRepository
import com.sungil.runningproejct_mvvm.utill.ListenerMessage
import com.sungil.runningproejct_mvvm.utill.RepositoryListener

class MainViewModel(private val repository: MainRepository) : ViewModel() , RepositoryListener {
    val wearLiveData get() = repository.getRunningRoomDB()
    //repository
    override fun onMessageSuccess(data: ListenerMessage) {
        TODO("Not yet implemented")
    }

    override fun onMessageFail(data: ListenerMessage) {
        TODO("Not yet implemented")
    }

}