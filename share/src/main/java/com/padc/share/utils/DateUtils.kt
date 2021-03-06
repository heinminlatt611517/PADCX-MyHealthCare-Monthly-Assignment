package com.padc.share.utils

import java.text.SimpleDateFormat
import java.util.*

class DateUtils
{
    fun getDaysAgo(daysAgo: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -daysAgo)
        return calendar.time
    }

    fun getCurrentDate() : String{
        val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd")
        return simpleDateFormat.format(Date())
    }

    fun getCurrentDateTime() : String{
        return SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(Date())
    }

    fun getCurrentTime() : String{
       return getCurrentTime()
   }
}
