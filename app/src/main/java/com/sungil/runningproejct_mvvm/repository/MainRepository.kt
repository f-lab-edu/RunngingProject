package com.sungil.runningproejct_mvvm.repository

import androidx.lifecycle.LiveData
import com.sungil.runningproejct_mvvm.`object`.WearRunDataDBM
//Main interface
interface MainRepository {
    fun getRunningRoomDB () : LiveData<WearRunDataDBM?>

}