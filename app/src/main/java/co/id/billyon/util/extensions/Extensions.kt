package co.id.billyon.util.extensions

import android.arch.lifecycle.MutableLiveData

fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { setValue(initialValue) }

fun String.removeAllThousandSeparator() : Long {

    if (!this.isEmpty() && this.contains(".")) {
        return this.replace(".", "").toLong()
    }

    return 0
}