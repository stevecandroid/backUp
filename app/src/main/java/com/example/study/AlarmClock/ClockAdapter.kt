package com.example.study.AlarmClock

import android.support.constraint.ConstraintLayout
import android.support.v7.view.menu.ActionMenuItemView
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Switch
import android.widget.TextView
import com.example.study.ContentReader.ContentLoader
import com.example.study.DataBaseUtils.ClockData
import com.example.study.R
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import java.util.*

/**
 * Created by 铖哥 on 2017/8/11.
 */
class ClockAdapter : RecyclerView.Adapter<ClockAdapter.ClockViewHolder>() {

    val clockDatas = ClockHandler.alarmDatas

    override fun getItemCount(): Int {
       return clockDatas.size
    }

    override fun onBindViewHolder(holder: ClockViewHolder, pos: Int) {
        holder.alarmTime.text =  "${timeParser(clockDatas[pos].hour)}:${timeParser(clockDatas[pos].minute)}"
        holder.alarmDetail.text =  ClockHandler.responseDataParser(clockDatas[pos].responseDate)
        holder.alarmSwitch.isChecked = clockDatas[pos].state==ClockData.STATE_ON
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClockViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.alarm_item,parent,false)
        val holder = ClockViewHolder(view)

        view.setOnClickListener { parent.context!!.startActivity<AlarmSettingActivity>(
                "data" to clockDatas[holder.adapterPosition], "pos" to holder.adapterPosition ) }



        holder.alarmSwitch.setOnCheckedChangeListener { p0, ischeck ->
            if(ischeck) {
                clockDatas[holder.adapterPosition].state = ClockData.STATE_ON
            }
            else{
                clockDatas[holder.adapterPosition].state = ClockData.STATE_OFF
            }
        }

        return holder

    }

    class ClockViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val alarmTime = itemView.find<TextView>(R.id.alarm_time_tv)
        val alarmDetail = itemView.find<TextView>(R.id.alarm_detail_tv)
        val alarmSwitch = itemView.find<Switch>(R.id.alarm_switch)
        val alarmWarp = itemView.find<ConstraintLayout>(R.id.alarm_warp)
    }

    fun timeParser( time : Int) : String = if(time < 9 )  "0${time}" else time.toString()


}