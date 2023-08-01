package com.sungil.runningproejct_mvvm.utill

/**
 * LiveData 상태 관찰을 위한
 */
data class Resource<out T>(val status: Status, val data: T?, val exception: String?) {
    enum class Status {
        LOADING,
        SUCCESS,
        ERROR,
    }

    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data,null)
        }

        fun <T> error(exception: String?): Resource<T> {
            return Resource(Status.ERROR, null, exception)
        }

        fun <T> loading(): Resource<T> {
            return Resource(Status.LOADING, null, null)
        }

    }
}
