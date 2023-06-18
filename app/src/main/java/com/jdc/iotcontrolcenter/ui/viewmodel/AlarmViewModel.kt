package com.jdc.iotcontrolcenter.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdc.iotcontrolcenter.data.model.Alarm
import com.jdc.iotcontrolcenter.domain.impl.AlarmUseCaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(private val alarmManager: AlarmUseCaseImpl) : ViewModel() {

    val responseAlarmsViewModel = MutableLiveData<MutableList<Alarm>>()
    val isUpdatedViewModel = MutableLiveData<Boolean>()

    fun getAllAlarmsOnline(){
        viewModelScope.launch {
            responseAlarmsViewModel.postValue(alarmManager.getAllAlarms())
        }
    }

    fun updateAlarmOnline(alarm: Alarm){
        viewModelScope.launch {
            isUpdatedViewModel.postValue(false)
            isUpdatedViewModel.postValue(alarmManager.updateAlarm(alarm))
        }
    }
}