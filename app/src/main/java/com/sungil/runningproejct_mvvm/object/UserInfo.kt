package com.sungil.runningproejct_mvvm.`object`

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sungil.runningproejct_mvvm.appDatabase.AppDatabase

//기본 init 변수 추가

@Entity(
    tableName = AppDatabase.USERINFO_TABLE
)
class UserInfo(
    @PrimaryKey val id : String = "" ,
    @ColumnInfo val password : String = "",
    @ColumnInfo val phoneNumber : String = "",
    @ColumnInfo val nickName : String = ""
)