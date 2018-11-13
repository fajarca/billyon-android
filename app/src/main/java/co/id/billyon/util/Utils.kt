package co.id.billyon.util

import java.text.SimpleDateFormat
import java.util.*

object Utils {
    @JvmStatic
    fun getCurrentTimestamp() : Long {
        val date = SimpleDateFormat("yyyyMMddHHmmssSSS").format(Date())
        return date.toLong()
    }
}
