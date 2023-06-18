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

    /**
     * Retrieves all alarms.
     * @return A mutable list of Alarm objects containing all alarms.
     */
    override suspend fun getAllAlarms(): MutableList<Alarm> {
        return try {
            apiRepository.listAllAlarms(sessionUseCaseImpl.getToken()).toMutableList()
        } catch (e: IOException) {
            Log.e("getAllAlarms", e.toString())
            emptyList<Alarm>().toMutableList()
        }
    }

    /**
     * Updates the given alarm.
     * @param alarm The Alarm object to update.
     * @return A boolean value indicating whether the update was successful or not.
     */
    override suspend fun updateAlarm(alarm: Alarm): Boolean {
        return try {
            apiRepository.updateAlarm(sessionUseCaseImpl.getToken(),alarm)
        } catch (e: IOException) {
            Log.e("updateAlarms", e.toString())
            false
        }
    }
}