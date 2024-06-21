package com.example.aivoiceapplication

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.aivoiceapplication.databinding.ActivityMainBinding
import com.example.lib_base.base.BaseActivity
import com.example.lib_base.event.EventManger
import com.example.lib_base.event.MessageEvent

import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventManger.register(this)
        binding=ActivityMainBinding.inflate(layoutInflater)
        val root = binding.root
        setContentView(root)


        binding.btn.setOnClickListener {
            EventManger.post(1)
        }

    }

    override fun onStart() {
        super.onStart()
        binding.btn1.setOnClickListener {
          var str: String =binding.btn1.text.toString()
            EventManger.post(1,str)
            }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {
        when (event.type) {
            event.type -> {
                // do something
                if (event.type == 1) {
                    Log.d("event", "event.strValue==${event.strValue}")
                    Log.d("event", "event.type==1")
                }
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        EventManger.unregister(this)
    }
}