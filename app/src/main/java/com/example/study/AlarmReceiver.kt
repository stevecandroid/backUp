package com.example.study

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.study.AlarmClock.AlarmClockActivity
import org.jetbrains.anko.startActivity

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val intent = Intent(context, AlarmClockActivity::class.java)
        intent.putExtra("id", 5)
        intent.flags = (Intent.FLAG_ACTIVITY_NEW_TASK)
       context.startActivity(intent)

    }
}
