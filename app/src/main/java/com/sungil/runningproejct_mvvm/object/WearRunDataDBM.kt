package com.sungil.runningproejct_mvvm.`object`

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sungil.runningproejct_mvvm.appDatabase.AppDatabase

@Entity(
    tableName =  AppDatabase.WEAR_RUNNING_TABLE
)
//wear 에서 올 데이터
 class WearRunDataDBM(
    val runData : String,
    val time : String
){
     @PrimaryKey(autoGenerate = true)
     var id : Int = 0
}
