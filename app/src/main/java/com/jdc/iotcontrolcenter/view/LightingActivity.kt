package com.jdc.iotcontrolcenter.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.jdc.iotcontrolcenter.R
import com.jdc.iotcontrolcenter.data.model.Lightbulb
import com.jdc.iotcontrolcenter.databinding.ActivityLightingBinding
import com.jdc.iotcontrolcenter.databinding.ColorPickerBinding
import com.jdc.iotcontrolcenter.domain.LightbulbManager
import com.jdc.iotcontrolcenter.view.adapters.LightBulbRecyclerViewAdapter
import kotlinx.coroutines.launch

class LightingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLightingBinding
    private lateinit var recyclerViewAdapter: LightBulbRecyclerViewAdapter
    private val lightbulbManager = LightbulbManager()
    private val lightbulbList = mutableListOf<Lightbulb>()
    private val COLOR_LIST = listOf(
        "0,0,1","1,0,0","0,1,1","0,1,0","1,1,0","1,0,1","1,1,1","0,0,0"
    )
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

    @SuppressLint("NotifyDataSetChanged")
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
        try{
            if(lightbulbList[i].bulbControlerType=="RGB"){
                showColorPickerDialog(lightbulbList[i])

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
        }catch ( e: IndexOutOfBoundsException){
            Log.e("listado actualizandose","$e")
        }

    }

    private fun showColorPickerDialog(lightbulb: Lightbulb) {
        val builder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.color_picker,null)

        builder.setView(view)
        val colorPickerBinding = ColorPickerBinding.bind(view)
        val colorPickerDialog = builder.create()
        colorPickerDialog.window?.setBackgroundDrawableResource(R.drawable.white_card)
        colorPickerDialog.show()
        var pickerColor = COLOR_LIST[7]
        val list = resources.getStringArray(R.array.color_list)
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item,list)
        colorPickerBinding.colorList.adapter = spinnerAdapter

        colorPickerBinding.colorList.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                pickerColor = COLOR_LIST[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        colorPickerBinding.cancelButton.setOnClickListener { colorPickerDialog.hide() }
        colorPickerBinding.applyButton.setOnClickListener {
            lifecycleScope.launch {
                lightbulb.bulbValue = pickerColor
                lightbulbManager.updateLightbulb(lightbulb)
            }
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