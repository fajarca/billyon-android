package co.id.billyon.util

import java.text.SimpleDateFormat
import java.util.*

object Utils {
    @JvmStatic
    fun getCurrentTimestampAsId() = SimpleDateFormat("yyyyMMddHHmmssSSS").format(Date()).toLong()

    @JvmStatic
    fun getCurrentTimeStamp() = SimpleDateFormat("yyyyMMddHHmmssSSS").format(Date()).toLong()
}
