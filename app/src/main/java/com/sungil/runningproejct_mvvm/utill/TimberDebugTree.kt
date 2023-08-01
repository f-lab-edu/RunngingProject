package com.sungil.runningproejct_mvvm.utill

import timber.log.Timber

/**
 * 로그 라이브러리
 */
class TimberDebugTree : Timber.DebugTree() {
    private val filterName = "Timber"

    override fun createStackElementTag(element: StackTraceElement): String {
        return "${filterName}(${element.fileName})"
    }
}