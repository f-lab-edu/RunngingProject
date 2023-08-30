package com.sungil.runningproejct_mvvm.appDatabase

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sungil.runningproejct_mvvm.MainApplication
import com.sungil.runningproejct_mvvm.`object`.UserInfo
import com.sungil.runningproejct_mvvm.`object`.WearRunDataDBM

@Singleton
@Database(
    entities = [WearRunDataDBM :: class , UserInfoDBM :: class],
    version = 1
)

//appDatabase 추가
abstract class AppDatabase : RoomDatabase() {

    abstract fun wearRunningDao() :  RunningDao

    abstract fun userInfoDao () : UserInfoDao

    companion object{
        const val WEAR_RUNNING_TABLE = "runningTable"
        const val USERINFO_TABLE = "userInfoTable"

        @Volatile
        private var instance : AppDatabase ? = null

        @JvmStatic
        fun getInst(): AppDatabase {
            instance ?: synchronized(this) { // 존재하지 않는다면 sychronized
                instance = Room.databaseBuilder(
                    MainApplication.appContext,
                    AppDatabase::class.java,
                    "database"
                ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
            }

            return instance!!
        }
    }

}