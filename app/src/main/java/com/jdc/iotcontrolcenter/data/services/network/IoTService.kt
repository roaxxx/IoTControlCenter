package com.jdc.iotcontrolcenter.data.services.network

import android.util.Log
import com.jdc.iotcontrolcenter.data.model.*
import com.jdc.iotcontrolcenter.di.NetworkModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.IOException
import javax.inject.Inject

class IoTService @Inject constructor() {

    private val retrofitService = NetworkModule.buildService(APIService::class.java)

    suspend fun loginInApi(requestLogin: RequestLogin): String? {
        return withContext(Dispatchers.IO) {
            val response = retrofitService.login(requestLogin)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        }
    }

    suspend fun findLatestDHT11Data(token: String): DHT11Data {
        return withContext(Dispatchers.IO) {
            val response = retrofitService.getLastDHT11Data(token)
            if (response.isSuccessful) {
                response.body()!!
            } else {
                DHT11Data(0, 0, 0, "MMM, d Y", "hh:mm:ss")
            }
        }
    }

    suspend fun findAllDHT11Records(token: String): List<DHT11Data> {

        return withContext(Dispatchers.IO) {
            val response = retrofitService.listAllDHTData(token)
            if (response.isSuccessful) {
                response.body()!!
            } else {
                emptyList()
            }
        }
    }

    suspend fun deleteDHTRecords(token: String): String? {
        return try {
            withContext(Dispatchers.IO) {
                retrofitService.deleteDHT11Data(token).body()
            }
        } catch (e: IOException) {
            Log.e("deleteDHTRecords", "$e")
            return null
        }
    }

    suspend fun findAllDoors(token: String): List<Door> {
        return try {
            withContext(Dispatchers.IO) {
                retrofitService.findAllDoors(token).body()!!
            }
        } catch (e: IOException) {
            Log.e("okhttpFindAllDoors", "$e")
            emptyList()
        }
    }

    suspend fun updateDoor(token: String, door: Door): Boolean {
        return try {
            withContext(Dispatchers.IO) {
                retrofitService.updateDoor(token,door).body()!!
            }
        } catch (e: IOException) {
            Log.e("okhttpFindAllDoors", "$e")
            false
        }
    }

    suspend fun findAllLightbulbs(token: String): List<Lightbulb> {
        return try {
            withContext(Dispatchers.IO) {
                retrofitService.listLightbulbs(token).body()!!
            }
        } catch (e: IOException) {
            Log.e("okhttpFindAllDoors", "$e")
            emptyList()
        }
    }

    suspend fun updateLightbulb(token: String, lightbulb: Lightbulb): Boolean {
        return try {
            withContext(Dispatchers.IO) {
                retrofitService.updateLightbulbState(token,lightbulb).body()!!
            }
        } catch (e: IOException) {
            Log.e("okhttpFindAllDoors", "$e")
            false
        }
    }

    suspend fun findAllAlarms(token: String): List<Alarm> {
        return try {
            withContext(Dispatchers.IO) {
                retrofitService.listAllAlarms(token).body()!!
            }
        } catch (e: IOException) {
            Log.e("okhttpFindAllAlarms", "$e")
            emptyList()
        }
    }

    suspend fun updateAlarm(token: String, alarm: Alarm): Boolean {
        return try {
            withContext(Dispatchers.IO) {
                retrofitService.updateAlarm(token, alarm).body()!!
            }
        } catch (e: IOException) {
            Log.e("okhttpupdateAlarm", "$e")
            false
        }
    }

    suspend fun findAllNotifications(token: String): List<Notification> {
        return try {
            withContext(Dispatchers.IO) {
                retrofitService.listNotifications(token).body()!!
            }
        } catch (e: IOException) {
            Log.e("okhttpFindAllNotif", "$e")
            emptyList()
        }
    }

    suspend fun deleteAllNotifications(token: String): Boolean {
        return try {
            withContext(Dispatchers.IO) {
                retrofitService.deleteAllNotifications(token).body()!!
            }
        } catch (e: IOException) {
            Log.e("okhttdeleteNots", "$e")
            false
        }
    }
}