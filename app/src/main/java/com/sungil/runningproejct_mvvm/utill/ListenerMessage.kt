package com.sungil.runningproejct_mvvm.utill

/**
 * Firebase -> viewModel 에게 메세지 송신 시 사용하는 data class
 */
data class ListenerMessage(
    val data : Any?,
    val message : String
)
