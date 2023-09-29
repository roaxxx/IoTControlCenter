package com.jdc.iotcontrolcenter.domain.impl

import com.jdc.iotcontrolcenter.data.ApiRepository
import com.jdc.iotcontrolcenter.data.model.DHT11Data
import com.jdc.iotcontrolcenter.domain.Dht11SensorUseCase
import com.jdc.iotcontrolcenter.domain.SessionUseCase
import javax.inject.Inject
import com.jdc.iotcontrolcenter.data.Result

class Dht11SensorUseCaseImpl @Inject constructor(
    private val apiRespository: ApiRepository,
    private val sessionUseCase: SessionUseCase
) : Dht11SensorUseCase {

    override suspend fun getAllDht11Record(): Result<List<DHT11Data>> {
        return apiRespository.findAllDHT11Records(sessionUseCase.getToken())
    }

    /**
     * Clear all record for DHT11 Sensor online
     */
    override suspend fun clearRecords(): Result<Boolean> {
        return apiRespository.deleteDHT11Records(sessionUseCase.getToken())
    }
}