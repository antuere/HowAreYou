package antuere.how_are_you.broadcastReceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class DateChangeReceiver(private val dateChanged: () -> Unit) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_DATE_CHANGED) {
            dateChanged()
        }
    }
}