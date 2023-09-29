package com.jdc.iotcontrolcenter.domain

import com.jdc.iotcontrolcenter.data.model.Alarm
import com.jdc.iotcontrolcenter.data.model.AlarmConditionsDTO
import com.jdc.iotcontrolcenter.data.Result

interface AlarmUseCase {

    suspend fun updateAlarm(
        idAlarm: String,
        alarmConditionsDTO: AlarmConditionsDTO
    ): Result<Alarm>
}