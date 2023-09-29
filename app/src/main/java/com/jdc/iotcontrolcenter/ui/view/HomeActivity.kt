package com.jdc.iotcontrolcenter.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.jdc.iotcontrolcenter.R
import com.jdc.iotcontrolcenter.data.Result
import com.jdc.iotcontrolcenter.data.model.*
import com.jdc.iotcontrolcenter.data.preferences.PreferencesProvider
import com.jdc.iotcontrolcenter.databinding.ActivityHomeBinding
import com.jdc.iotcontrolcenter.databinding.FireAlarmSettingsBinding
import com.jdc.iotcontrolcenter.ui.viewmodel.*
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okio.IOException

@AndroidEntryPoint
class HomeActivity  : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    private lateinit var ioTInfoViewModel: IoTInfoViewModel
    private val doorViewModel:  DoorViewModel  by viewModels()
    private val alarmViewModel: AlarmViewModel by viewModels()
    private val loginViewModel: LoginViewModel by viewModels()

    private lateinit var loadingDialog: AlertDialog

    private lateinit var alarmConditionsDTO : AlarmConditionsDTO

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

        ioTInfoViewModel = ViewModelProvider(this)
            .get(IoTInfoViewModel::class.java)

        initListeners()
        initObservables()
        loginViewModel.getSessionUser()
        updateData()
    }

    private fun updateData() {
        ioTInfoViewModel.getIoTInformation()
    }

    private fun initObservables() {
        loginViewModel.usernameObservable.observe(this, Observer { usernameResult ->
            when(usernameResult){
                is Result.Success -> binding.welcomText.text = "Hola, buenos días, " + usernameResult.data
                is Result.Error -> Toast.makeText(this,"${usernameResult.exception}", Toast.LENGTH_LONG).show()
            }

        })

        ioTInfoViewModel.ioTInfoObservable.observe(this, Observer { ioTInfoResult ->

            when(ioTInfoResult){
                is Result.Success -> onSuccessIotInformation(ioTInfoResult.data)
                is Result.Error -> Toast.makeText(this,"${ioTInfoResult.exception}", Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun onSuccessIotInformation(ioTInfoResult: IoTInformationDTO) {
        updateTemperatureAndHumidityOnUI(ioTInfoResult.lastRecord)

        mainDoor   = ioTInfoResult.doors.find { door -> door.idDoor == 1 }!!
        garageDoor = ioTInfoResult.doors.find { door -> door.idDoor == 2 }!!

        mainDoor.let {mainDoor
            updateMainDoorOnUI(mainDoor.doorState, mainDoor.updateDate)
        }

        garageDoor.let {
            binding.garageLastUpdate.text = garageDoor.updateDate
            binding.changeGarageDoorState.text = if (garageDoor.doorState == "open") "Cerrar" else "Abrir"
        }

        intruderAlarm = ioTInfoResult.alarms.find { alarm -> alarm.idAlarm == "alarm01" }!!
        fireAlarm     = ioTInfoResult.alarms.find { alarm -> alarm.idAlarm == "alarm02" }!!

        updateIntruderAlarmUI(intruderAlarm.alarmState)
        loadingDialog.hide()
    }

    private fun updateIntruderAlarmUI(state: String) {
        val buttonText = if (state == "on") "Desactivar" else "Activar"
        val imageResource = if (state == "on") R.drawable.ic_actived_alarm else R.drawable.ic_desactived_alarm

        binding.changeIntruderAlarmState.text = buttonText
        binding.intruderAlarmImg.setImageResource(imageResource)
    }

    private fun updateTemperatureAndHumidityOnUI(lastRecord: DHT11Data) {
        binding.lastTemperatureDate.text = lastRecord.date
        binding.temperatureValue.text = "${lastRecord.temperature} C°"

        binding.lastHumidityDate.text = lastRecord.date
        binding.humidityPorcentage.text = "${lastRecord.humidity} %"
    }

    fun updateMainDoorOnUI(state: String, mainDoorUpdatedDate: String) {
        binding.doorLastUpdate.text = mainDoorUpdatedDate

        val doorText = if (state == "open") "Abierta" else "Cerrada"
        binding.doorState.text = "Puerta principal: $doorText"
        binding.changeDoorState.text = if (state == "open") "Cerrar" else "Abrir"
        val doorImageResource = if (state == "open") R.drawable.ic_open_door else R.drawable.ic_close_door
        binding.doorImg.setImageResource(doorImageResource)
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
                 val newIntruderAlarmState = if (intruderAlarm.alarmState == "on") "off" else "on"
                alarmConditionsDTO = AlarmConditionsDTO(newIntruderAlarmState, 0)
                lifecycleScope.launch {
                    alarmViewModel.updateAlarmOnline(intruderAlarm.idAlarm, alarmConditionsDTO)
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
            val newfireAlarmcondition = (dialogBinding.fireAlarmTemperature.text.toString()).toInt()
            val newFireAlarmState = if(dialogBinding.activedRadioButton.isChecked)  "on" else "off"

            val fireAlarmConditons = AlarmConditionsDTO(newFireAlarmState, newfireAlarmcondition)

            alarmViewModel.updateAlarmOnline(fireAlarm.idAlarm, fireAlarmConditons)

            fireAlarmConfigurerDialog.hide()
        }
    }

    private fun updateDoorState(door: Door) {
        try {

            val newState: String = if(door.doorState == "open")  "close" else "open"
            doorViewModel.updateDoorOnline(door.idDoor,newState)
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

    override fun onDestroy() {
        super.onDestroy()
        PreferencesProvider.clear(this)
    }
}