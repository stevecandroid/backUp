package com.example.study.Survival

import android.app.Service
import android.app.job.JobInfo
import android.app.job.JobParameters
import android.app.job.JobScheduler
import android.app.job.JobService
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.example.study.AlarmClock.AlarmClockActivity
import org.jetbrains.anko.startService
import org.jetbrains.anko.toast

class JobLiveService : JobService() {

    override fun onCreate() {
        super.onCreate()



    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startJob()
        return START_STICKY
    }

    fun startJob(){
        val jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        val componentName = ComponentName(this,JobLiveService::class.java)
        val info = JobInfo.Builder(1,componentName)
                .setOverrideDeadline(2000)
                .setMinimumLatency(2000)
                .setRequiresCharging(false)
                .setRequiresDeviceIdle(false)
                .setPersisted(true).build()
        jobScheduler.schedule(info)
    }

    override fun onStopJob(p0: JobParameters?): Boolean {

        return false
    }

    override fun onStartJob(p0: JobParameters?): Boolean {
        Log.e("TAG","JOB START")
        toast("JOB START")
        val intent = Intent(this,JobLiveService::class.java)
        startService(intent)
        val brocast = Intent("ELITOR_CLOCK")
        sendBroadcast(brocast)
        return false
    }


}
