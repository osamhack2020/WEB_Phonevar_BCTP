package kr.osam.phonevar.util

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    const val LOG_DATE_TYPE = "yyyy-MM-dd HH:mm:ss"
    fun getCurrentDate(format: String): String {
        val dateFormat: DateFormat = SimpleDateFormat(format)
        val date = Date()
        return dateFormat.format(date)
    }
}