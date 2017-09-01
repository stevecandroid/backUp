package com.example.study.Survival

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import com.example.study.R
import android.app.Activity
import android.content.Context
import java.lang.ref.WeakReference
import android.content.Intent
import com.example.study.App
import android.content.IntentFilter
import android.content.BroadcastReceiver




class LiveActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live)


        window.setGravity(Gravity.START or Gravity.TOP)
        val attributes = window.attributes
        attributes.width = 1
        attributes.height = 1
        attributes.x = 0
        attributes.y = 0
        window.attributes = attributes
        ScreenManager.setActivity(this)

    }

    companion object {
        fun actionToLiveActivity(pContext: Context) {
            val intent = Intent(pContext, LiveActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            pContext.startActivity(intent)
        }
    }


    object ScreenManager   {

        private var mActivityWref: WeakReference<Activity>? = null

        fun setActivity(pActivity: Activity) {
            mActivityWref = WeakReference<Activity>(pActivity)
        }

        fun startActivity() {
            LiveActivity.actionToLiveActivity(App.instance)
        }

        fun finishActivity() {
            //结束掉LiveActivity
            if (mActivityWref != null) {
                val activity = mActivityWref!!.get()
                if (activity != null) {
                    activity!!.finish()
                }
            }
        }

    }
}

class ScreenBroadcastListener(context: Context) {

    private val mContext: Context

    private val mScreenReceiver: ScreenBroadcastReceiver

    private var mListener: ScreenStateListener? = null

    init {
        mContext = App.instance
        mScreenReceiver = ScreenBroadcastReceiver()
    }

     interface ScreenStateListener {

        fun onScreenOn()

        fun onScreenOff()
    }

    /**
     * screen状态广播接收者
     */
     inner class ScreenBroadcastReceiver : BroadcastReceiver() {
        private var action: String? = null

        override fun onReceive(context: Context, intent: Intent) {
            action = intent.action
            if (Intent.ACTION_SCREEN_ON == action) { // 开屏
                mListener!!.onScreenOn()
            } else if (Intent.ACTION_SCREEN_OFF == action) { // 锁屏
                mListener!!.onScreenOff()
            }
        }
    }

    fun registerListener(listener: ScreenStateListener) {
        mListener = listener
        registerListener()
    }

    private fun registerListener() {
        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_SCREEN_ON)
        filter.addAction(Intent.ACTION_SCREEN_OFF)
        mContext.registerReceiver(mScreenReceiver, filter)
    }
}
