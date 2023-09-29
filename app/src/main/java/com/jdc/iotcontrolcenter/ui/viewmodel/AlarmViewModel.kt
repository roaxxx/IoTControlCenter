package com.jdc.iotcontrolcenter.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdc.iotcontrolcenter.data.model.Alarm
import com.jdc.iotcontrolcenter.data.model.AlarmConditionsDTO
import com.jdc.iotcontrolcenter.domain.AlarmUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.jdc.iotcontrolcenter.data.Result

@HiltViewModel
class AlarmViewModel @Inject constructor(
    private val alarmManager: AlarmUseCase
) : ViewModel() {

    val isUpdatedViewModel = MutableLiveData<Result<Alarm>>()

    fun updateAlarmOnline(alarmId: String, alarmConditionsDTO: AlarmConditionsDTO){
        viewModelScope.launch {
            isUpdatedViewModel.postValue(alarmManager.updateAlarm(alarmId, alarmConditionsDTO))
        }
    }
}