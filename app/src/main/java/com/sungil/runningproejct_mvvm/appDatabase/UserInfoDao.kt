package com.sungil.runningproejct_mvvm.appDatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.sungil.runningproejct_mvvm.dataObject.UserInfoDBM

@Dao
interface UserInfoDao {

    @Insert
    fun insert(data : UserInfoDBM)

    @Delete
    fun delete(data : UserInfoDBM)

    @Update
    fun update(data : UserInfoDBM)

    @Query("SELECT * FROM ${AppDatabase.USERINFO_TABLE}")
    fun getUserInfo() : UserInfoDBM?
}