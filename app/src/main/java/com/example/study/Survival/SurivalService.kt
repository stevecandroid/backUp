package com.example.study.Survival

import android.app.Notification
import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Binder
import android.os.IBinder
import android.util.Log
import android.os.Build
import org.jetbrains.anko.toast

val  GRAY_SERVICE_ID  = 1001

class SurivalService : Service() {

    var binder :  RemoteServiceBinder? = null

    override fun onCreate() {
        super.onCreate()
        if (binder!=null){
            binder = RemoteServiceBinder()
        }

        toast("CREATE of SURVIVALSERVICE")
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(connection)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

//        startService(Intent(this,Service2::class.java))
        bindService(Intent(this,Service2::class.java),connection,BIND_AUTO_CREATE)

            val innerIntent = Intent(this, SurvivalInnerService()::class.java)
            startService(innerIntent)
            startForeground(GRAY_SERVICE_ID, Notification())

        return super.onStartCommand(intent, flags, startId)
    }//同ID灰色保活大法

    override fun onBind(intent: Intent): IBinder? {
        return binder
    }

    class RemoteServiceBinder : Binder(){
        fun binderLog(){
            Log.e("binderLog","LOGGER")
        }
    }

    val connection : ServiceConnection = object :  ServiceConnection {
        override fun onBindingDied(name: ComponentName?) {
            super.onBindingDied(name)
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            bindService(Intent(this@SurivalService,Service2::class.java),this,BIND_AUTO_CREATE)
            toast("disConnect of Service2")
        }

        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {

            toast("CONNECTED TO SERVICE2")

        }
    }

    class SurvivalInnerService : Service(){
        override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
            startForeground(GRAY_SERVICE_ID, Notification())
            stopForeground(true)
            stopSelf()
            return super.onStartCommand(intent, flags, startId)
        }

        override fun onBind(p0: Intent?): IBinder {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
        override fun onDestroy() {
            super.onDestroy()
        }
    }

}

//双服务互保大法
class Service2 : Service() {

    var mBider : MyBinder? = null

    override fun onBind(intent: Intent): IBinder? {
        return mBider
    }

    class MyBinder : Binder(){
        fun binderLog(){
            Log.e("binderLog","LOGGER")
        }
    }

    override fun onCreate() {
        if(mBider==null){
            mBider = MyBinder()
        }
        super.onCreate()
       toast("CREATE of SERVICE2")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        bindService(Intent(this,SurivalService::class.java),connection, Context.BIND_AUTO_CREATE)
        Log.e("TAG","SERVICE 2 CREATE")
        return super.onStartCommand(intent, flags, startId)
    }

    val connection : ServiceConnection = object :  ServiceConnection {
        override fun onBindingDied(name: ComponentName?) {
            super.onBindingDied(name)
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            bindService(Intent(this@Service2,SurivalService::class.java),this,BIND_AUTO_CREATE)
            toast("disConnect to SurivalService")
        }

        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            toast("CONNECTED TO SURIVAL")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(connection)
    }
}


