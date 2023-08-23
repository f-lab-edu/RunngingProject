package com.sungil.runningproejct_mvvm.utill

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class CommUtil {
    companion object{
        fun convertLocalTime(timeStamp : Long): String {
            val timeZone = TimeZone.getDefault()
            val format = SimpleDateFormat("MM-dd HH:mm", Locale.getDefault())
            format.timeZone = timeZone

            val date = Date(timeStamp)
            return format.format(date)
        }
    }
}