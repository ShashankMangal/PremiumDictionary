package com.sharkBytesLab.premiumDictionary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.chaquo.python.PyObject
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.sharkBytesLab.premiumDictionary.databinding.ActivityMainScreenBinding

class MainScreen : AppCompatActivity() {

    private lateinit var binding : ActivityMainScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
    }

}