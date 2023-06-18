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

    override suspend fun getSensorLatestData(): DHT11Data? {
        try {
            return apiRespository.findLatestDHT11Data(sessionUseCaseImpl.getToken())
        } catch (e: IOException) {
            Log.e("getSensorLatestData", " $e")
            return null
        }
    }

    override suspend fun getAllDHTRecords(): MutableList<DHT11Data> {
        return try {
            return apiRespository.findAllDHT11Records(sessionUseCaseImpl.getToken()).toMutableList()
        } catch (e: IOException) {
            Log.e("getSensorLatestData", " $e")
            emptyList<DHT11Data>().toMutableList()
        }
    }

    override suspend fun clearRecords() {
        apiRespository.deleteDHT11Records(sessionUseCaseImpl.getToken())
    }

}