package com.example.study.DataBaseUtils

/**
 * Created by 铖哥 on 2017/8/12.
 */


@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class Data ( val column : String)
