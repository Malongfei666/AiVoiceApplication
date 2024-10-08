package com.example.lib_voice.manager

import android.content.Context
import android.util.Log
import com.baidu.speech.EventListener
import com.baidu.speech.asr.SpeechConstant
import com.example.lib_voice.asr.VoiceAsr
import com.example.lib_voice.helper.AudioManagerHelper
import com.example.lib_voice.impl.OnAsrResultListener
import com.example.lib_voice.tts.VoiceTTs
import com.example.lib_voice.wakeup.VoiceWakeUp
import org.json.JSONObject


/**
 * 语音管理类
 */
object VoiceManager :EventListener {
    private  var  TAG=VoiceManager::class.java.simpleName
    //接口
    private lateinit var mOnAsrResultListener:OnAsrResultListener
    //语音key
    const val VOICE_APP_ID = "94414114"
    const val VOICE_APP_KEY = "2RMkyrpU6GdRfD3OfIcCMcho"
    const val VOICE_APP_SECRET = "9S8qhfKTs8xX53gnCYpv6Ax9xuO4svfx"
    fun initManager(mContext: Context,ivmPath:String,mOnAsrResultListener: OnAsrResultListener) {
        this.mOnAsrResultListener=mOnAsrResultListener
        // 初始化语音管理类
        VoiceTTs.initTTS(mContext)
        VoiceAsr.initAsr(mContext,this)
        VoiceWakeUp.initWakeUp(mContext,this)

    }
    //tts start
    //播放
    fun ttsStart(text:String){
        Log.d(TAG, "开始TTS-：$text")
        VoiceTTs.start(text,null)
    }
    //播放且有回调
    fun ttsStart(text:String,mListener:VoiceTTs.OnTTSResultListener){
        Log.d(TAG, "开始TTS-：$text")
        VoiceTTs.start(text,mListener)
    }
    //暂停
    fun ttsPause(){
        VoiceTTs.pause()
    }
    //恢复
    fun ttsResume(){
       VoiceTTs.resume()
    }
    //停止
    fun ttsStop(){
        VoiceTTs.stop()
    }
    //释放
    fun ttsRelease(){
        VoiceTTs.release()
    }
    //设置发音人
    fun setPeople(people:Int){
        VoiceTTs.setPeople(people)
    }
    //设置语速
    fun setSpeed(speed:Int){
        VoiceTTs.setSpeed(speed)
    }
    //设置音量
    fun setVolume(volume:Int){
        VoiceTTs.setVolume(volume)
    }

    //启动唤醒
    fun startWakeUp(){
        Log.d(TAG, "启动唤醒")
        VoiceWakeUp.startWakeUp()
    }
    //停止唤醒
    fun stopWakeUp(){
        Log.d(TAG, "stopWakeUp: ")
        VoiceWakeUp.stopWakeUp()
    }
    //-------Asr start-------
    fun startAsr(){
        VoiceAsr.startAsr()
    }
    fun stopAsr(){
        VoiceAsr.stopAsr()
    }
    fun cancelAsr(){
        VoiceAsr.cancelAsr()
    }
    fun releaseAsr(){
        VoiceAsr.releaseAsr(this)
    }




    //tts end
    override fun onEvent(name: String?, params: String?, byte: ByteArray?,
                         offset: Int, length: Int)
    {
        Log.d(TAG, "onEvent: $name $params $byte $offset $length")
        //语音前置状态
        when (name) {
            SpeechConstant.CALLBACK_EVENT_WAKEUP_READY -> mOnAsrResultListener.weakUpReady()
            SpeechConstant.CALLBACK_EVENT_ASR_BEGIN -> mOnAsrResultListener.asrStartSpeak()
            SpeechConstant.CALLBACK_EVENT_ASR_END -> mOnAsrResultListener.asrStopSpeak()
        }
        //去除脏数据
        if (params == null) {
            return
        }
        val allJson = JSONObject(params)
        Log.i("Test", "allJson:$name:$allJson")
        when (name) {
            SpeechConstant.CALLBACK_EVENT_WAKEUP_SUCCESS -> mOnAsrResultListener.weakUpSuccess(
                allJson
            )
//            SpeechConstant.CALLBACK_EVENT_WAKEUP_ERROR -> mOnAsrResultListener.weakUpError("唤醒失败")
            SpeechConstant.CALLBACK_EVENT_ASR_FINISH -> mOnAsrResultListener.asrResult(allJson)
            SpeechConstant.CALLBACK_EVENT_ASR_PARTIAL -> {
                mOnAsrResultListener.updateUserText(allJson.optString("best_result"))
                byte?.let {
                    val nlu = JSONObject(String(byte, offset, length))
                    mOnAsrResultListener.nluResult(nlu)
                }
            }
        }
    }
    //tts end
}