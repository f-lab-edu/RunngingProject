package com.sungil.runningproejct_mvvm.login.module

import android.content.Context
import com.sungil.device.room.UserInfoDao
import com.sungil.runningproejct_mvvm.domain.usecase.user.DeleteUserInfoUseCase
import com.sungil.runningproejct_mvvm.domain.usecase.user.GetUserInfoUseCase
import com.sungil.runningproejct_mvvm.domain.usecase.user.SaveUserinfoUseCase
import com.sungil.runningproejct_mvvm.domain.usecase.user.UpdateUserInfoUseCase
import com.sungil.runningproejct_mvvm.repository.loginImpl.LoginRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SingUpLoginImplModule {

    @Provides
    @Singleton
    fun provideSignUpLoginRepository(
        @ApplicationContext context: Context,
        saveUserinfoUseCase: SaveUserinfoUseCase,
        updateUserinfoUseCase: UpdateUserInfoUseCase,
        getUserInfoUseCase: GetUserInfoUseCase,
        deleteUserInfoUseCase: DeleteUserInfoUseCase,
    ): LoginRepoImpl {
        return LoginRepoImpl(
            context,
            saveUserinfoUseCase,
            updateUserinfoUseCase,
            getUserInfoUseCase,
            deleteUserInfoUseCase
        )
    }
}