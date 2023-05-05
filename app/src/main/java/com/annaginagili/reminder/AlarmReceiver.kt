package com.annaginagili.reminder

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.Ringtone
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.text.FieldPosition
import kotlin.random.Random

class AlarmReceiver(val head: String, val info: String, val date: String, val position: Int): BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        val intent1 = Intent(p0, Details::class.java)
        intent1.putExtra("head", head)
        intent1.putExtra("info", info)
        intent1.putExtra("date", date)
        intent1.putExtra("position", position)
        val pendingIntent = PendingIntent.getActivity(p0, 0, intent1 ,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        val builder = NotificationCompat.Builder(p0!!, "channel")
        builder.setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Reminder")
            .setContentText(head)
            .setContentIntent(pendingIntent)
            .priority = NotificationCompat.PRIORITY_HIGH

        val manager = NotificationManagerCompat.from(p0)
        manager.notify(Random.nextInt(), builder.build())
    }
}