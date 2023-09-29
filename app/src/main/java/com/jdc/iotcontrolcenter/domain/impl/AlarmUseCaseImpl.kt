package com.jdc.iotcontrolcenter.domain.impl

import android.util.Log
import com.jdc.iotcontrolcenter.data.ApiRepository
import com.jdc.iotcontrolcenter.data.model.Alarm
import com.jdc.iotcontrolcenter.data.model.AlarmConditionsDTO
import com.jdc.iotcontrolcenter.domain.AlarmUseCase
import com.jdc.iotcontrolcenter.domain.SessionUseCase
import javax.inject.Inject
import com.jdc.iotcontrolcenter.data.Result

class AlarmUseCaseImpl @Inject constructor(
    private val apiRepository : ApiRepository,
    private val sessionUseCase: SessionUseCase
) : AlarmUseCase {

    /**
     * Updates the given alarm.
     * @param alarm The Alarm object to update.
     * @return A boolean value indicating whether the update was successful or not.
     */
    override suspend fun updateAlarm(idAlarm: String, alarmConditionsDTO: AlarmConditionsDTO): Result<Alarm> {
        return apiRepository.updateAlarm(sessionUseCase.getToken(), idAlarm, alarmConditionsDTO)
    }
}