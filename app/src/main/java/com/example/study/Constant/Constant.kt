package com.example.study.Constant

import com.example.study.App
import java.io.File

/**
 * Created by 铖哥 on 2017/8/12.
 */
class Constant {
    companion object {
        val cacheFile : File = App.instance.getExternalCacheDir()
    }
}