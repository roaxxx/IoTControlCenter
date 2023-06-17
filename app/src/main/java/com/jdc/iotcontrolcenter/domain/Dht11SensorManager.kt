package com.jdc.iotcontrolcenter.domain

import android.util.Log
import com.jdc.iotcontrolcenter.data.ApiRespository
import com.jdc.iotcontrolcenter.data.model.DHT11Data
import okio.IOException
import javax.inject.Inject

class Dht11SensorManager @Inject constructor(private val apiRespository :ApiRespository) {

    suspend fun getSensorLatestData(): DHT11Data?{
        try {
            return apiRespository.findLatestDHT11Data()
        }catch (e : IOException){
            Log.e("getSensorLatestData"," $e")
            return null
        }
    }

    suspend fun getAllDHTRecords(): MutableList<DHT11Data>{
        return try {
            return apiRespository.findAllDHT11Records().toMutableList()
        }catch (e : IOException){
            Log.e("getSensorLatestData"," $e")
            emptyList<DHT11Data>().toMutableList()
        }
    }

    suspend fun clearRecords(){
        apiRespository.deleteDHT11Records()
    }

}