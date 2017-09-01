package com.example.study.AlarmClock

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.study.DataBaseUtils.ClockData
import com.example.study.R
import kotlinx.android.synthetic.main.activity_alarm_setting.*
import org.jetbrains.anko.selector
import java.text.SimpleDateFormat
import android.content.Intent
import com.example.study.BaseActivity


class AlarmSettingActivity : BaseActivity() {

    private val alarmDatas = ClockHandler.alarmDatas
    private var responseDate = arrayListOf<Int>()
    private var isVibrate = false
    private var state = ClockData.STATE_ON
    private lateinit var alarmMgr : AlarmManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_setting)
        initView()
        initListener()

        alarmMgr = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val clockData = intent.getSerializableExtra("data")
        val pos = intent.getIntExtra("pos", -1)



        DoneButton.setOnClickListener {
            if(responseDate.size == 0 ){
                responseDate.addAll(listOf(1,2,3,4,5,6,7))
            }

            val save = ClockData(timePicker.currentHour, timePicker.currentMinute, 0, ClockData.STATE_ON,isVibrate,responseDate)
            if(pos == -1) {
                alarmDatas.add(save)
                setAlarmClock(save,false)
            }else{
                alarmDatas.set(pos,save)
            }
            ClockHandler.save(alarmDatas)
            onBackPressed()
        }


        if (clockData != null) {
            clockData as ClockData
            timePicker.currentHour = clockData.hour
            timePicker.currentMinute = clockData.minute
            curMode.text = ClockHandler.responseDataParser(clockData.responseDate)
            vribateSwitch.isChecked = clockData.isVibarte
            responseDate = clockData.responseDate
            state = clockData.state
            isVibrate = clockData.isVibarte
        }

    }

    fun initView() {
        val decorView = window.decorView;
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.statusBarColor = Color.TRANSPARENT;
        timePicker.setIs24HourView(true)
    }

    fun initListener(){

        modeLL.setOnClickListener {
            val list = listOf("周一至周五", "每天", "自定义")
            selector(null, list, { dialogInterface, i ->
                responseDate.clear()
                when (i) {

                    0 -> {
                        responseDate.addAll(listOf(1, 2, 3, 4, 5))
                        curMode.text = list[1]
                    }
                    1 -> {
                        responseDate.addAll(listOf(1,2,3,4,5,6,7))
                        curMode.text = list[2]
                    }
                    2 -> {

                    }
                }
            }
            )
        }


        vribateSwitch.setOnCheckedChangeListener {
            o1, isChecked -> isVibrate = isChecked
        }
    }

    fun setAlarmClock( clockData: ClockData , isModify : Boolean){
        val intent = Intent("ELITOR_CLOCK")
        intent.putExtra("msg", "你该打酱油了")
        if(!isModify && clockData.state == ClockData.STATE_ON){
            for( day in clockData.responseDate){
                alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+1000,
                        60000, PendingIntent.getBroadcast(this,0,intent,0))

            }
        }
    }

    fun calculateStartTime(clockData : ClockData , day : Int) : Long{
        val cur = System.currentTimeMillis()
        val dateformat = SimpleDateFormat("HH:mm:ss")
        val dateStr = dateformat.format(System.currentTimeMillis())

        val trible = dateStr.split(":")
        val s = trible[2].toDouble()
        val m = trible[1].toInt()
        var h = trible[0].toInt()

        var cm = 0
        var ch = 0

        if(m <= clockData.minute){
            cm = clockData.minute - m
        }else{
            cm = 60 - m
            h ++
        }

        while(h != clockData.hour){
            h ++
            if(h == 24){
                h = 0
            }
            ch ++
        }

        return ch*60*60*1000L + cm*60*100L


    }

}
