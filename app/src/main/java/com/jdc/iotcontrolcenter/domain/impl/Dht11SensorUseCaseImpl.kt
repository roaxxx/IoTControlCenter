package com.jdc.iotcontrolcenter.domain.impl

import android.util.Log
import com.jdc.iotcontrolcenter.data.ApiRespository
import com.jdc.iotcontrolcenter.data.model.DHT11Data
import com.jdc.iotcontrolcenter.domain.Dht11SensorUseCase
import okio.IOException
import javax.inject.Inject

class Dht11SensorUseCaseImpl @Inject constructor(
    private val apiRespository: ApiRespository,
    private val sessionUseCaseImpl: SessionUseCaseImpl
) : Dht11SensorUseCase {

    /**
     * Retrieves the latest data from the DHT11 sensor.
     * @return The latest DHT11Data object, or null if an error occurs.
     */
    override suspend fun getSensorLatestData(): DHT11Data? {
        try {
            return apiRespository.findLatestDHT11Data(sessionUseCaseImpl.getToken())
        } catch (e: IOException) {
            Log.e("getSensorLatestData", " $e")
            return null
        }
    }

    /**
     * Retrieves all DHT11 records.
     * @return A mutable list of DHT11Data objects representing all records, or an empty list if an error occurs.
     */
    override suspend fun getAllDHTRecords(): MutableList<DHT11Data> {
        return try {
            return apiRespository.findAllDHT11Records(sessionUseCaseImpl.getToken()).toMutableList()
        } catch (e: IOException) {
            Log.e("getSensorLatestData", " $e")
            emptyList<DHT11Data>().toMutableList()
        }
    }

    /**
     * Clear all record for DHT11 Sensor online
     */
    override suspend fun clearRecords() {
        try {
            apiRespository.deleteDHT11Records(sessionUseCaseImpl.getToken())
        } catch (e: IOException) {
            Log.e("clearRecords", " $e")
        }
    }
}