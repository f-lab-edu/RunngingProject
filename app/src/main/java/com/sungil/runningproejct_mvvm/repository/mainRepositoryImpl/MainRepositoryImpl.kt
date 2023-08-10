package com.sungil.runningproejct_mvvm.repository.mainRepositoryImpl

import androidx.lifecycle.LiveData
import com.sungil.runningproejct_mvvm.appDatabase.AppDatabase
import com.sungil.runningproejct_mvvm.`object`.WearRunDataDBM
import com.sungil.runningproejct_mvvm.repository.MainRepository

class MainRepositoryImpl() : MainRepository {
    //repository
    override fun getRunningRoomDB(): LiveData<WearRunDataDBM?> {
        return AppDatabase.getInst().wearRunningDao().getRunningData()
    }


}