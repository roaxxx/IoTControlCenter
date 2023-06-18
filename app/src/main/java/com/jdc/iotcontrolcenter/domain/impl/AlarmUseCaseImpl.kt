package com.jdc.iotcontrolcenter.domain.impl

import android.util.Log
import com.jdc.iotcontrolcenter.data.ApiRespository
import com.jdc.iotcontrolcenter.data.model.Alarm
import com.jdc.iotcontrolcenter.domain.AlarmUseCase
import okio.IOException
import javax.inject.Inject

class AlarmUseCaseImpl @Inject constructor(
    private val apiRepository: ApiRespository,
    private val sessionUseCaseImpl: SessionUseCaseImpl
) : AlarmUseCase {

    override suspend fun getAllAlarms(): MutableList<Alarm> {
        return try {
            apiRepository.listAllAlarms(sessionUseCaseImpl.getToken()).toMutableList()
        } catch (e: IOException) {
            Log.e("getAllAlarms", e.toString())
            emptyList<Alarm>().toMutableList()
        }
    }

    override suspend fun updateAlarm(alarm: Alarm): Boolean {
        return try {
            apiRepository.updateAlarm(sessionUseCaseImpl.getToken(),alarm)
        } catch (e: IOException) {
            Log.e("updateAlarms", e.toString())
            false
        }
    }
}