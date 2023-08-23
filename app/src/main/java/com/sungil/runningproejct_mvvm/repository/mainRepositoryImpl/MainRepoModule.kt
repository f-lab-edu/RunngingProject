package com.sungil.runningproejct_mvvm.repository.mainRepositoryImpl

import com.sungil.runningproejct_mvvm.appDatabase.RunningDao
import com.sungil.runningproejct_mvvm.appDatabase.UserInfoDao
import com.sungil.runningproejct_mvvm.repository.MainRepository
import com.sungil.runningproejct_mvvm.useCase.MainUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//MainRepo Module
@Module
@InstallIn(SingletonComponent :: class)
object MainRepoModule {
    @Provides
    @Singleton
    fun providedMainRepository(
        runningDao : RunningDao,
        userDao : UserInfoDao,
        useCase : MainUseCase
    ): MainRepository  {
        return MainRepositoryImpl(runningDao , userDao ,useCase )
    }


}