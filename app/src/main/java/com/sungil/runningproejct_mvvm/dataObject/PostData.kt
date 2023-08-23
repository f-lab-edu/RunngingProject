package com.sungil.runningproejct_mvvm.dataObject

data class PostData (
    val writer : String = "",
    val title : String = "",
    val content : String = "",
    val time : Long = 0L,
    val running : String = "",
    val nickName : String = ""
)
