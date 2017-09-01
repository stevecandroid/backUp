package com.example.study.AlarmClock

import android.util.Log
import com.example.study.Constant.Constant
import com.example.study.DataBaseUtils.ClockData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.PrintWriter

/**
 * Created by 铖哥 on 2017/8/12.
 */
class ClockHandler {

    companion object {
        val file = File("${Constant.cacheFile}/clockMsg.txt")
val     alarmDatas : MutableList<ClockData> = getClockData()

        fun save( clockList : List<ClockData>){

            if(file.exists()){
                file.delete()
            }
            file.createNewFile()

                val os = FileOutputStream(file)
                val pw = PrintWriter(os)
                val msg = Gson().toJson(clockList)
                pw.print(msg)
                pw.close()
                os.close()
        }

        fun getClockData() : MutableList<ClockData>{

            if(!file.exists()){
                return mutableListOf()
            }

            val msg : StringBuilder = StringBuilder()
            val ins = FileInputStream(file)
            val buf = ByteArray(1024)
            var len = 0 ;

            while(true){
                len =  ins.read(buf)
                if(len == -1){
                    break
                }
                msg.append(String(buf,0,len))
            }
            ins.close()
           return Gson().fromJson<ArrayList<ClockData>>(msg.toString(), object : TypeToken<ArrayList<ClockData>>(){}.type)
        }

        fun responseDataParser( list : MutableList<Int>) : String{

            val result = StringBuilder()
            var i = list.size-1

            if(list.size == 7){
                return "每天"
            }

            while( i >=0 ){
                result.append("星期${list.get(i)},")
                i--
            }

            return result.toString()
        }

    }
}