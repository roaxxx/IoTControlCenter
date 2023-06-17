package com.jdc.iotcontrolcenter.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.jdc.iotcontrolcenter.R
import com.jdc.iotcontrolcenter.data.model.Alarm
import com.jdc.iotcontrolcenter.data.model.DHT11Data
import com.jdc.iotcontrolcenter.data.model.Door
import com.jdc.iotcontrolcenter.databinding.ActivityHomeBinding
import com.jdc.iotcontrolcenter.databinding.FireAlarmSettingsBinding
import com.jdc.iotcontrolcenter.ui.viewmodel.AlarmViewModel
import com.jdc.iotcontrolcenter.ui.viewmodel.Dht11SensorViewModel
import com.jdc.iotcontrolcenter.ui.viewmodel.DoorViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okio.IOException

@AndroidEntryPoint
class HomeActivity  : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    private val dht11SensorViewModel : Dht11SensorViewModel by viewModels()
    private val doorViewModel : DoorViewModel by viewModels()
    private val alarmViewModel : AlarmViewModel by viewModels()

    private var latestDHTData: DHT11Data? = null
    private lateinit var loadingDialog: AlertDialog
    private val listOfDoors = mutableListOf<Door>()
    private val alarmList = mutableListOf<Alarm>()

    private lateinit var intruderAlarm: Alarm
    private lateinit var fireAlarm: Alarm
    private lateinit var mainDoor: Door
    private lateinit var garageDoor: Door

    private lateinit var dialogBinding: FireAlarmSettingsBinding
    private val handler = Handler()
    private val updateInterval = 1000L
    private val dataPointHandler: Runnable = object : Runnable {
        override fun run() {
            updateData()
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
        initObservables()

        val bundle = intent.extras
        binding.welcomText.text = "Hola, buenos días, " + bundle?.getString("userName")!!
        updateData()
    }

    private fun updateData() {
        dht11SensorViewModel.getLastRecord()
        dht11SensorViewModel.getAllDhtRecordList()
        doorViewModel.getAllDoorsOnline()
        alarmViewModel.getAllAlarmsOnline()
    }

    private fun initObservables() {
        dht11SensorViewModel.dhtSesorLastRecordObservable.observe(this, Observer { dhtLastRecord ->
            binding.lastTemperatureDate.text = dhtLastRecord.date
            binding.temperatureValue.text = dhtLastRecord.temperature.toString() + " C°"

            binding.lastHumidityDate.text = dhtLastRecord.date
            binding.humidityPorcentage.text = dhtLastRecord.humidity.toString() + " %"
        })

        doorViewModel.doorListObservable.observe(this, Observer { doorList->
            listOfDoors.clear()
            listOfDoors.addAll(doorList)
            if(listOfDoors.size>0 && listOfDoors[0].idDoor==1){
                mainDoor = listOfDoors[0]
                garageDoor = listOfDoors[1]
            }else {
                mainDoor = listOfDoors[1]
                garageDoor = listOfDoors[0]
            }

            mainDoor.let {
                binding.doorLastUpdate.text = mainDoor.updateDate
                if (mainDoor.doorState == "open") {
                    binding.doorState.text = "Puerta principal: Abierta"
                    binding.changeDoorState.text = "Cerrar"
                    binding.doorImg.setImageResource(R.drawable.ic_open_door)
                } else {
                    binding.doorState.text = "Puerta principal: Cerrada"
                    binding.changeDoorState.text = "Abir"
                    binding.doorImg.setImageResource(R.drawable.ic_close_door)
                }
            }
            garageDoor.let {
                binding.garageLastUpdate.text = garageDoor.updateDate
                if (garageDoor.doorState=="open"){
                    binding.changeGarageDoorState.text = "Cerrar"
                }else{
                    binding.changeGarageDoorState.text = "Abrir"
                }
            }
        })

        alarmViewModel.responseAlarmsViewModel.observe(this, Observer { alarmMutableList ->
            alarmList.clear()
            alarmList.addAll(alarmMutableList)
            if (alarmList[0].idAlarm == "alarm01") {
                intruderAlarm = alarmList[0]
                fireAlarm = alarmList[1]
            } else {
                intruderAlarm = alarmList[0]
                fireAlarm = alarmList[1]
            }
            if (intruderAlarm.alarmState == "on") {
                binding.changeIntruderAlarmState.text = "Desactivar"
                binding.intruderAlarmImg.setImageResource(R.drawable.ic_actived_alarm)
            } else {
                binding.changeIntruderAlarmState.text = "Activar"
                binding.intruderAlarmImg.setImageResource(R.drawable.ic_desactived_alarm)
            }
            loadingDialog.hide()
        })

    }

    private fun initListeners() {
        binding.lightingCard.setOnClickListener {
            startActivity(Intent(this, LightingActivity::class.java))
        }
        binding.temperatureCard.setOnClickListener {
            startActivity(Intent(this, TemperatureActivity::class.java))
        }
        binding.humidityCard.setOnClickListener {
            startActivity(Intent(this, HumidityActivity::class.java))
        }
        binding.changeDoorState.setOnClickListener {
            updateDoorState(mainDoor)
        }
        binding.changeGarageDoorState.setOnClickListener {
            updateDoorState(garageDoor)
        }
        binding.showNotifications.setOnClickListener {
            startActivity(Intent(this, NotificationActivity::class.java))
        }
        binding.changeIntruderAlarmState.setOnClickListener {
            try {
                intruderAlarm.alarmState = if (intruderAlarm.alarmState == "on") "off" else "on"
                lifecycleScope.launch {
                    alarmViewModel.updateAlarmOnline(intruderAlarm)
                }
            } catch (e: IOException) {
                Log.e("changeIntruderAlarm", e.toString())
            }
        }
        binding.closeSessionButton.setOnClickListener {
            onBackPressed()
        }
        binding.configuraFireAlarmButton.setOnClickListener { showFireAlarmSettingDialog() }
    }

    private fun showFireAlarmSettingDialog() {
        val builder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.fire_alarm_settings,null)
        builder.setView(view)
        val fireAlarmConfigurerDialog = builder.create()
        fireAlarmConfigurerDialog.window?.setBackgroundDrawableResource(R.drawable.white_card)
        dialogBinding = FireAlarmSettingsBinding.bind(view)

        fireAlarmConfigurerDialog.show()

        dialogBinding.fireAlarmTemperature.setText(fireAlarm.condition.toString())
        if(fireAlarm.alarmState=="on"){
            dialogBinding.radioButtonGroup.check(R.id.activedRadioButton)
        }else{
            dialogBinding.radioButtonGroup.check(R.id.desactivedRadioButton)
        }
        dialogBinding.cancelButton.setOnClickListener { fireAlarmConfigurerDialog.hide()}
        dialogBinding.applyButton.setOnClickListener {
            fireAlarm.condition = (dialogBinding.fireAlarmTemperature.text.toString()).toInt()
            if(dialogBinding.activedRadioButton.isChecked){
                fireAlarm.alarmState = "on"
            }else{
                fireAlarm.alarmState = "off"
            }
            try {

                lifecycleScope.launch {
                    alarmViewModel.updateAlarmOnline(fireAlarm)
                }
            } catch (e: IOException) {
                Log.e("changeIntruderAlarm", e.toString())
            }
            fireAlarmConfigurerDialog.hide()
        }
    }

    private fun updateDoorState(door: Door) {
        try {
            lifecycleScope.launch {
                if(door.doorState == "open"){
                    door.doorState = "close"
                }else{
                    door.doorState = "open"
                }
                doorViewModel.updateDoorOnline(door)
            }
        } catch (e: IOException) {
            Log.e("onClickChangeDoorState", e.toString())
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

    override fun onBackPressed() {
        val sessionDialog = AlertDialog.Builder(this)
        sessionDialog.setTitle("¿Cerrar sesión?")
        sessionDialog.setMessage("Desea salir y terminar esta sesión?")

        sessionDialog.setPositiveButton("Sí") {_ ,_->
            super.onBackPressed()
        }
        sessionDialog.setNegativeButton("No",null)
        val dialog = sessionDialog.create()
        dialog.window?.setBackgroundDrawableResource(R.drawable.light_blue_card_bg)
        dialog.show()
    }
}