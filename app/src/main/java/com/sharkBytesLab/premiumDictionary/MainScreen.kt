package com.sharkBytesLab.premiumDictionary

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.applovin.sdk.AppLovinSdk
import com.applovin.sdk.AppLovinSdkConfiguration
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.sharkBytesLab.premiumDictionary.databinding.ActivityMainScreenBinding

class MainScreen : AppCompatActivity() {

    private lateinit var binding : ActivityMainScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        setListeners()

    }

    private fun setListeners()
    {
        binding.findButton.setOnClickListener {
            val data = binding.editText.text
            if(!Python.isStarted())
            {
                Python.start(AndroidPlatform(this))
                val py=Python.getInstance()
                val pyObj = py.getModule("res")
                val obj = pyObj.callAttr("backEnd", data.toString())
                binding.answer.text = obj.toString()
                binding.question.text = "You searched for : " + data
            }
            else{
                val py=Python.getInstance()
                val pyObj = py.getModule("res")
                val obj = pyObj.callAttr("backEnd", data.toString())
                binding.answer.text = obj.toString()
                binding.question.text = "You searched for : " + data
            }

        }

        binding.menu.setOnClickListener {
            val i = Intent(applicationContext, MenuActivity::class.java)
            startActivity(i)
        }

    }

    private fun init()
    {
        AppLovinSdk.getInstance( this ).setMediationProvider( "max" )
        AppLovinSdk.getInstance( this ).initializeSdk({ configuration: AppLovinSdkConfiguration ->
            // AppLovin SDK is initialized, start loading ads
            binding.applovinAdMain.loadAd()
        })
    }

}