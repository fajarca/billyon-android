package co.id.billyon.util

import java.text.SimpleDateFormat
import java.util.*

object Utils {
    @JvmStatic
    fun getCurrentTimestampAsId() = SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.US).format(Date()).toLong()

    @JvmStatic
    fun getCurrentTimeStamp() = SimpleDateFormat("YYYY-MM-dd HH:mm:ss", Locale.US).format(Date())
}
