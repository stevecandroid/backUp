package com.example.study.DataBaseUtils

import java.io.Serializable

/**
 * Created by 铖哥 on 2017/8/12.
 */
data class ClockData( var hour : Int, var minute : Int,var second : Int = 0 ,
                     var state : Int, var isVibarte : Boolean ,var responseDate : ArrayList<Int>) : Serializable{

    companion object {
        val STATE_ON = 1
        val STATE_OFF = 0

        val MONDAY = 1
        val TUESDAY = 2
        val WEDNESDAY = 3
        val THIRSDAY =4
        val FRIDAY = 5
        val SATUSDAY =6
        val SUNDAY = 7
        val ONLYONE = 0
    }


}