package com.jdc.iotcontrolcenter.data.services.network

import android.util.Log
import com.jdc.iotcontrolcenter.data.model.*
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
            emptyList()
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
            emptyList()
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

    suspend fun findAllAlarms(): List<Alarm>{
        return try {
            withContext(Dispatchers.IO) {
                retrofitService.listAllAlarms().body()!!
            }
        }catch(e : IOException){
            Log.e("okhttpFindAllAlarms","$e")
            emptyList()
        }
    }
    suspend fun updateAlarm(alarm: Alarm): Boolean{
        return try {
            withContext(Dispatchers.IO) {
                retrofitService.updateAlarm(alarm).body()!!
            }
        }catch(e : IOException){
            Log.e("okhttpupdateAlarm","$e")
            false
        }
    }

    suspend fun findAllNotifications(): List<Notification>{
        return try {
            withContext(Dispatchers.IO) {
                retrofitService.listNotifications().body()!!
            }
        }catch(e : IOException){
            Log.e("okhttpFindAllNotif","$e")
            emptyList()
        }
    }

    suspend fun deleteAllNotifications(): Boolean{
        return try {
            withContext(Dispatchers.IO) {
                retrofitService.deleteAllNotifications().body()!!
            }
        }catch(e : IOException){
            Log.e("okhttdeleteNots","$e")
            false
        }
    }

}