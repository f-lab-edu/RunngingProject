package com.sungil.runningproejct_mvvm.activityRate.useCase

import com.sungil.controller.repository.ControllerRepository
import com.sungil.runningproejct_mvvm.appDatabase.RunningDao
import com.sungil.runningproejct_mvvm.dataObject.WearRunDataDBM
import javax.inject.Inject

class StopRunningRateUseCase @Inject constructor(
    private val repository: ControllerRepository,
    private val runningDao: RunningDao,
) {
    suspend fun stopRunningRate() {
        repository.stopGpsApi()
    }

    fun saveRunningRate(distance: String) {
        val beforeData = runningDao.getRunningData()
        if (beforeData == null) {
            runningDao.insert(WearRunDataDBM(distance))
            return
        }
        runningDao.update(WearRunDataDBM(distance))
    }
}