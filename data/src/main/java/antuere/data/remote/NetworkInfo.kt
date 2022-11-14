package antuere.data.remote

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class NetworkInfo (private val context: Context) {

    fun isNetworkAvailable(): Boolean {
        val connectionManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectionManager.getNetworkCapabilities(connectionManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    return true
                }
            }
        }
        return false
    }
}