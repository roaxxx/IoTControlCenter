package com.jdc.iotcontrolcenter.view

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.jdc.iotcontrolcenter.data.model.Lightbulb
import com.jdc.iotcontrolcenter.databinding.ActivityLightingBinding
import com.jdc.iotcontrolcenter.domain.LightbulbManager
import com.jdc.iotcontrolcenter.view.adapters.LightBulbRecyclerViewAdapter
import kotlinx.coroutines.launch

class LightingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLightingBinding
    private lateinit var recyclerViewAdapter: LightBulbRecyclerViewAdapter
    private val lightbulbManager = LightbulbManager()
    private val lightbulbList = mutableListOf<Lightbulb>()
    private val handler = Handler()
    private val updateInterval = 2000L
    private val dataPointHandler: Runnable = object : Runnable {
        override fun run() {
            changeRecyclerViewData()
            handler.postDelayed(this, updateInterval)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLightingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
        changeRecyclerViewData()
    }

    private fun changeRecyclerViewData() {
        lightbulbList.clear()
        lifecycleScope.launch{
            lightbulbList.addAll(lightbulbManager.listLightbulbs())
            recyclerViewAdapter.notifyDataSetChanged()
        }
    }

    private fun initRecyclerView() {
        recyclerViewAdapter = LightBulbRecyclerViewAdapter(
            lightbulbList,
            onClickChangeState = { i -> changeLighbulbState(i) }
        )
        binding.lightbulbList.layoutManager = LinearLayoutManager(this)
        binding.lightbulbList.adapter = recyclerViewAdapter
    }

    private fun changeLighbulbState(i: Int) {
        if(lightbulbList[i].bulbControlerType=="RGB"){
            if(lightbulbList[i].bulbValue=="0,0,0"){
                lightbulbList[i].bulbValue="1,1,1"
            }else{
                lightbulbList[i].bulbValue="0,0,0"
            }
        }else{
            if(lightbulbList[i].bulbValue=="0"){
                lightbulbList[i].bulbValue="1"
            }else{
                lightbulbList[i].bulbValue="0"
            }
        }
        lifecycleScope.launch {
            lightbulbManager.updateLightbulb(lightbulbList[i])
        }
    }
    override fun onResume() {
        super.onResume()
        handler.postDelayed(dataPointHandler, updateInterval)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(dataPointHandler)
    }
}