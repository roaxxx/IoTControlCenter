package com.jdc.iotcontrolcenter.domain

import com.jdc.iotcontrolcenter.data.model.Alarm

interface AlarmUseCase {

    suspend fun getAllAlarms(): MutableList<Alarm>

    suspend fun updateAlarm(alarm: Alarm): Boolean
}