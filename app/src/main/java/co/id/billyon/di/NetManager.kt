package co.id.billyon.di

import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetManager @Inject constructor (private var applicationContext: Context) {
    private var status: Boolean? = false

    val isConnectedToInternet: Boolean
        get() {
            val conManager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val ni = conManager.activeNetworkInfo
            return ni != null && ni.isConnected
        }
}