package com.sungil.runningproejct_mvvm.`object`

//Firebase 에 보내고 받을 회원정보 ( 회원가입시 에만)
data class UserInfo(
    val id : String = "" ,
    val password : String = "",
    val phoneNumber : String = "",
    val nickName : String = ""
)