package com.jdc.iotcontrolcenter.domain

import com.jdc.iotcontrolcenter.data.model.DHT11Data

interface Dht11SensorUseCase {

    suspend fun getSensorLatestData(): DHT11Data?

    suspend fun getAllDHTRecords(): MutableList<DHT11Data>

    suspend fun clearRecords()
}