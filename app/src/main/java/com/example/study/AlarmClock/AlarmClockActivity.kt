package com.example.study.AlarmClock

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.example.study.BaseActivity
import com.example.study.DataBaseUtils.ClockData
import com.example.study.R
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_alarm_clock.*
import org.jetbrains.anko.startActivity
import java.time.Clock
import android.support.v4.content.ContextCompat.startActivity
import com.example.study.Survival.LiveActivity.ScreenManager
import android.app.admin.DevicePolicyManager
import com.example.study.Survival.*


class AlarmClockActivity : BaseActivity() {

    override fun onResume() {
        super.onResume()
        recyclerClock.adapter.notifyDataSetChanged()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_clock)

        recyclerClock.adapter  = ClockAdapter()
        recyclerClock.layoutManager = LinearLayoutManager(this)

        alarmFAB.setOnClickListener { startActivity<AlarmSettingActivity>() }



        val intent = Intent(this,SurivalService::class.java)
        startService(Intent(this, Service2::class.java))
        startService(intent)

//        val intent = Intent(this,JobLiveService::class.java)
//        startService(intent)
//        val screenManager = ScreenManager
//        val listener = ScreenBroadcastListener(this)
//        listener.registerListener(object : ScreenBroadcastListener.ScreenStateListener {
//            override fun onScreenOn() {
//                screenManager.finishActivity()
//            }
//
//            override fun onScreenOff() {
//                screenManager.startActivity()
//            }
//        })


    }

    fun activation() {
        val intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN)
        // 初始化要激活的组件
        val mDeviceAdminSample = ComponentName(this@AlarmClockActivity, DeviceAdmin::class.java)
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mDeviceAdminSample)
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "激活可以防止随意卸载应用")
        startActivity(intent)
    }
}
