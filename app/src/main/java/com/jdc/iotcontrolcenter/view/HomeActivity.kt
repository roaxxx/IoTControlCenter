package com.jdc.iotcontrolcenter.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.jdc.iotcontrolcenter.data.model.DHT11Data
import com.jdc.iotcontrolcenter.data.model.Door
import com.jdc.iotcontrolcenter.databinding.ActivityHomeBinding
import com.jdc.iotcontrolcenter.domain.DoorManagent
import com.jdc.iotcontrolcenter.domain.TemperatureSensorManagement
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHomeBinding
    private val temperatureSensorManagement = TemperatureSensorManagement()
    private val doorManagement = DoorManagent()
    private  var latestDHTData : DHT11Data? = null
    private lateinit var loadingDialog: AlertDialog
    private val listOfDoors = mutableListOf<Door>()
    private val handler = Handler()
    private val updateInterval = 2000L
    private val dataPointHandler: Runnable = object : Runnable {
        override fun run() {
            showDashboardInfo()
            handler.postDelayed(this, updateInterval)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadingDialog = LoadingDialog().createProgressDialog(this)
        loadingDialog.show()
        initListeners()
        val bundle = intent.extras
        binding.welcomText.text = "Hola, buenos días, "+bundle?.getString("userName")!!
        showDashboardInfo()
    }

    private fun initListeners() {
        binding.lightingCard.setOnClickListener {
            startActivity(Intent(this,LightingActivity::class.java))
        }
        binding.temperatureCard.setOnClickListener {
            startActivity(Intent(this,TemperatureActivity::class.java))
        }
        binding.humidityCard.setOnClickListener {
            startActivity(Intent(this,HumidityActivity::class.java))
        }
        binding.changeDoorState.setOnClickListener {
            lifecycleScope.launch{
                listOfDoors[0].let { door ->
                    binding.doorLastUpdate.text = door.updateDate
                    if (door.doorState == "open") {
                        listOfDoors[0].doorState = "close"
                    }else{
                        listOfDoors[0].doorState = "open"
                    }
                    doorManagement.updateDoorstate(listOfDoors[0])
                }
            }
            showDashboardInfo()
        }
    }
    @SuppressLint("SetTextI18n")
    private fun showDashboardInfo() {
        lifecycleScope.launch{
            latestDHTData = temperatureSensorManagement.getSensorLatestData()
            listOfDoors.clear()
            listOfDoors.addAll(doorManagement.getAllDoors())

            if(latestDHTData != null) {
                binding.lastTemperatureDate.text = latestDHTData!!.date
                binding.temperatureValue.text = latestDHTData!!.temperature.toString() + " C°"

                binding.lastHumidityDate.text =  latestDHTData!!.date
                binding.humidityPorcentage.text = latestDHTData!!.humidity.toString() + " %"
            }

            val mainDoor = listOfDoors[0]
            mainDoor.let {
                binding.doorLastUpdate.text = mainDoor.updateDate
                if (mainDoor.doorState=="open"){
                    binding.doorState.text = "Puerta principal: Abierta"
                    binding.changeDoorState.text = "Cerrar"
                }else{
                    binding.doorState.text = "Puerta principal: Cerrada"
                    binding.changeDoorState.text = "Abir"
                }
            }
            loadingDialog.hide()
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