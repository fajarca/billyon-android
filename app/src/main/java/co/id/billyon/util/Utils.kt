package co.id.billyon.util

import android.os.Environment
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

object Utils {
    @JvmStatic
    fun getCurrentTimestampAsId() = SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.US).format(Date()).toLong()

    @JvmStatic
    fun getCurrentTimeStamp() = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(Date())

    /**
     * Because the external storage might be unavailable—such as when the user has mounted the storage
     * to a PC or has removed the SD card that provides the external
     * storage—you should always verify that the volume is available before accessing it.
     * You can query the external storage state by calling getExternalStorageState().
     * If the returned state is MEDIA_MOUNTED, then you can read and write your files.
     * If it's MEDIA_MOUNTED_READ_ONLY, you can only read the files.
     */
    /* Checks if external storage is available for read and write */
    fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    @JvmStatic
    fun addThousandSeparator(value : Long) : String {
        var formatter = NumberFormat.getInstance(Locale.GERMAN) as DecimalFormat
        formatter.applyPattern("#,###,###,###")

        return formatter.format(value)
    }
}
