package com.sungil.runningproejct_mvvm.utill

/**
 * Repository -> viewModel Listener
 */
interface RepositoryListener {


    fun onMessageSuccess(data : ListenerMessage)

    fun onMessageFail(data : ListenerMessage)
}