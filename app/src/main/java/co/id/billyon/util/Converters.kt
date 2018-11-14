package co.id.billyon.util

import android.arch.persistence.room.TypeConverter
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class Converters {

    @TypeConverter
    fun fromTimestamp(timestamp: Long?): Date? = timestamp?.let { Date(it) }

    @TypeConverter
    fun dateToTimestamp(date : Date?) : Long? = date?.time
}