package com.jdc.iotcontrolcenter.domain

import com.jdc.iotcontrolcenter.data.model.DHT11Data
import com.jdc.iotcontrolcenter.data.Result

interface Dht11SensorUseCase {

    suspend fun getAllDht11Record(): Result<List<DHT11Data>>

    suspend fun clearRecords(): Result<Boolean>

}