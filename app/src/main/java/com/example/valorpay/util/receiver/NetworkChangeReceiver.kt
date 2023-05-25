package com.example.valorpay.util.receiver
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.valorpay.view.activity.NoInternetActivity

class NetworkChangeReceiver : BroadcastReceiver() {

    private var isConnected = false

    override fun onReceive(context: Context, intent: Intent) {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.activeNetwork ?: return
        val activeNetwork = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return

        val isConnectedNew = activeNetwork.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)

        if (isConnected != isConnectedNew) {
            isConnected = isConnectedNew
            if (isConnected) {

            } else {
                val noConnectionIntent = Intent(context, NoInternetActivity::class.java)
                noConnectionIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(noConnectionIntent)
            }
        }
    }
}
