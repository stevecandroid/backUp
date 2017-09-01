package com.example.study

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import org.jetbrains.anko.toast

/**
 * Created by 铖哥 on 2017/8/12.
 */
open class BaseActivity : AppCompatActivity() {

//    val receiver : AlarmBrocastReceiver = AlarmBrocastReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if ( ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),0)
        }
//
//        val filter = IntentFilter()
//        filter.addAction("ELITOR_CLOCK")
//        registerReceiver(receiver,filter)
    }


//    override fun onDestroy() {
//        super.onDestroy()
//        unregisterReceiver(receiver)
//
//    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

//    class AlarmBrocastReceiver : BroadcastReceiver() {
//        override fun onReceive(ctx: Context, intent: Intent) {
//           Log.e("TAG","RECEIVE BROCAST ${intent.getStringExtra("msg")}")
//        }
//
//    }
}