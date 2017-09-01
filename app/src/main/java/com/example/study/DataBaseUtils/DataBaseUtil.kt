package com.example.study.DataBaseUtils

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.study.App

/**
 * Created by 铖哥 on 2017/8/12.
 */

/**
 * 大坑日后再填 编译时注解
 *
 */
class DataBaseUtil {

    companion object {

        var dbHelper : SQLiteOpenHelper? = null

        fun <T>createTable( clazz : Class<T>){
            val table = StringBuilder()
            table.append("create table ${clazz.name.substring(clazz.name.lastIndexOf('.')+1,clazz.name.length)} (id integer primary key autoincrement, ")

            val fields = clazz.declaredFields
            for( field in fields){
                val a = field.getAnnotation(Data::class.java)
                if(a != null){
                    table.append("${a.column} ${columnParser(field.genericType.toString())}, ")
                }
            }

            table.replace(table.length-2,table.length-1,")")

        }

        private fun columnParser( originalColumn : String) : String{
            when(originalColumn){
                "int" -> return "integer"
                "float","double" -> return "real"
                "class java lang.String"->  return "text"
            }

            when{
                originalColumn.contains("class [List.lang.") || originalColumn.contains("java.util.List") -> return "text"
                else -> throw IllegalAccessException("Not Support this Type !!! ")
            }
        }

        private fun initDataBase( dataBaseName : String ,  tableCreator : String,  version : Int = 1){
            dbHelper = MyDataBaseHelper(App.instance,dataBaseName,tableCreator,version)
            dbHelper!!.writableDatabase
        }

//        fun <T>insert( objectx : T){
//            T
//        }
    }

}

class  MyDataBaseHelper( val ctx : Context, val dataBaseName : String , val tableCreator : String, val version : Int) : SQLiteOpenHelper(ctx,dataBaseName,null,version) {
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(tableCreator)
    }

    fun SQLiteDatabase.upgradeTable(tableName: String){
        execSQL("drop table if exists ${tableName}")
        execSQL(tableCreator)
    }
}