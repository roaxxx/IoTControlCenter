package com.jdc.iotcontrolcenter.data.services.network

import android.util.Log
import com.jdc.iotcontrolcenter.data.model.DHT11Data
import com.jdc.iotcontrolcenter.data.model.Door
import com.jdc.iotcontrolcenter.data.model.Lightbulb
import com.jdc.iotcontrolcenter.data.model.RequestLogin
import com.jdc.iotcontrolcenter.di.NetworkModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.IOException

class IoTService {

    private val retrofitService = NetworkModule.buildService(APIService::class.java)

    suspend fun loginInApi(requestLogin: RequestLogin): String{
        return withContext(Dispatchers.IO){
            retrofitService.login(requestLogin).body()!!
        }
    }

    suspend fun findLatestDHT11Data(): DHT11Data{
        return withContext(Dispatchers.IO){
            retrofitService.getLastDHT11Data().body()!!
        }
    }

    suspend fun findAllDHT11Records(): List<DHT11Data>{
        return try {
            withContext(Dispatchers.IO) {
                retrofitService.listAllDHTData().body()!!
            }
        }catch(e : IOException){
            Log.e("findAllDHT11Records","$e")
            emptyList<DHT11Data>()
        }
    }
    suspend fun deleteDHTRecords(): String?{
        return try {
            withContext(Dispatchers.IO) {
                retrofitService.deleteDHT11Data().body()
            }
        }catch(e : IOException){
            Log.e("deleteDHTRecords","$e")
            return null
        }
    }

    suspend fun findAllDoors(): List<Door>{
        return try {
            withContext(Dispatchers.IO) {
                retrofitService.findAllDoors().body()!!
            }
        }catch(e : IOException){
            Log.e("okhttpFindAllDoors","$e")
            emptyList<Door>()
        }
    }
    suspend fun updateDoor(door: Door): Boolean{
        return try {
            withContext(Dispatchers.IO) {
                retrofitService.updateDoor(door).body()!!
            }
        }catch(e : IOException){
            Log.e("okhttpFindAllDoors","$e")
            false
        }
    }

    suspend fun findAllLightbulbs(): List<Lightbulb>{
        return try {
            withContext(Dispatchers.IO) {
                retrofitService.listLightbulbs().body()!!
            }
        }catch(e : IOException){
            Log.e("okhttpFindAllDoors","$e")
            emptyList<Lightbulb>()
        }
    }
    suspend fun updateLightbulb(lightbulb: Lightbulb): Boolean{
        return try {
            withContext(Dispatchers.IO) {
                retrofitService.updateLightbulbState(lightbulb).body()!!
            }
        }catch(e : IOException){
            Log.e("okhttpFindAllDoors","$e")
            false
        }
    }
}