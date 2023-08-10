package com.sungil.runningproejct_mvvm.main.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sungil.runningproejct_mvvm.main.viewModel.MainViewModel
import com.sungil.runningproejct_mvvm.repository.mainRepositoryImpl.MainRepositoryImpl

//MainViewModelFactory 추가
class MainViewModelFactory () : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel :: class.java)){
            return MainViewModel(MainRepositoryImpl()) as T
        }
        throw IllegalArgumentException("UnKnow ViewModel class")
    }
}