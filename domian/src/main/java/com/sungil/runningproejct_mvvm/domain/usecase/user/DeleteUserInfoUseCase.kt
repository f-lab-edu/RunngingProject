package com.sungil.runningproejct_mvvm.domain.usecase.user

import com.sungil.runningproejct_mvvm.domain.entity.UserInfoData
import com.sungil.runningproejct_mvvm.domain.interactor.ControllerRepository
import com.sungil.runningproejct_mvvm.domain.interactor.UserInfoRepository
import javax.inject.Inject

class DeleteUserInfoUseCase @Inject constructor(private val userInfoRepository: UserInfoRepository) {

    fun deleteUserInfo(data: UserInfoData) {
        userInfoRepository.deleteUserInfo(data)
    }
}