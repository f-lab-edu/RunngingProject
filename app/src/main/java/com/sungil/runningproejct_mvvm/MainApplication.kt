package com.sungil.runningproejct_mvvm

import android.app.Application
import com.sungil.runningproejct_mvvm.utill.TimberDebugTree
import timber.log.Timber

/**
 * MainApplication
 */
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init(){
        Timber.plant(TimberDebugTree())
    }

}