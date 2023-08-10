package com.sungil.runningproejct_mvvm.appDatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.sungil.runningproejct_mvvm.`object`.UserInfo
//로그인정보 추가
@Dao
interface UserInfoDao {

    @Insert
    fun insert(data : UserInfo)

    @Delete
    fun delete(data : UserInfo)

    @Update
    fun update(data : UserInfo)

    @Query("SELECT * FROM ${AppDatabase.USERINFO_TABLE}")
    fun getUserInfo() : UserInfo?
}