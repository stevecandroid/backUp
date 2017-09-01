package com.example.study

import android.app.Application
import kotlin.properties.Delegates

/**
 * Created by 铖哥 on 2017/8/12.
 */
class App : Application() {

    companion object {
        var instance : Application by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}