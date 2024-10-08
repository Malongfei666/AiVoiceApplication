package com.example.lib_base.helper

import android.app.Activity
import android.app.Application
import android.os.Bundle

import com.alibaba.android.arouter.launcher.ARouter
import com.example.lib_base.config.BuildConfig

/**
 * @description: TODO
 * @author: mlf
 * @date: 2024/6/21 10:42
 * @version: 1.0
 */
object ARouterHelper {
    const val PATH_APP_MANAGER="/app_manager/app_manager_activity"
    const val PATH_CONSTELLATION="/constellation/constellation_activity"
    const val PATH_DEVELOPER="/developer/developer_activity"
    const val PATH_JOKE="/joke/joke_activity"
    const val PATH_MAP="/map/map_activity"
    const val PATH_SETTING="/setting/setting_activity"
    const val PATH_VOICE_SETTING="/voice_setting/voice_setting_activity"
    const val PATH_WEATHER="/weather/weather_activity"



    //初始化
    fun initHelper(application:Application){
        if(BuildConfig.DEBUG){
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(application)
    }
    //跳转页面
    fun startActivity(path:String){
        ARouter.getInstance().build(path).navigation()
    }
    //跳转页面
    fun startActivity(activity: Activity,path:String,requestCode:Int){
        ARouter.getInstance().build(path).navigation(activity,requestCode)
    }
    //跳转页面 传递一个string
    fun startActivity(path: String, key:String, value:String){
        ARouter.getInstance().build(path).withString(key,value).navigation()
    }
    // 跳转页面 传递一个int
    fun startActivity(path: String, key:String, value:Int){
        ARouter.getInstance().build(path).withInt(key,value).navigation()
    }
    //跳转页面 传递一个boole
    fun startActivity(path: String, key:String, value:Boolean){
        ARouter.getInstance().build(path).withBoolean(key,value).navigation()
    }
    //跳转页面 传递一个Long
    fun startActivity(path: String, key:String, value:Long){
        ARouter.getInstance().build(path).withLong(key,value).navigation()
    }
    //跳转页面 传递一个Bundle
    fun startActivity(path: String, key:String, value: Bundle){
        ARouter.getInstance().build(path).withBundle(key,value).navigation()
    }
    //跳转页面 传递一个对象
    fun startActivity(path: String, key:String, value: Any){
        ARouter.getInstance().build(path).withObject(key,value).navigation()
    }

    fun startActivity(path: String, key: String, value: String, key1: String, value1: String) {
        ARouter.getInstance().build(path).withString(key, value).withString(key1, value1).navigation()
    }

}