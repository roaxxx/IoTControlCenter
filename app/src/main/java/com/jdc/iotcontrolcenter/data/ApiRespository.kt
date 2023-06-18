package com.jdc.iotcontrolcenter.data

import com.jdc.iotcontrolcenter.data.model.*
import com.jdc.iotcontrolcenter.data.services.network.IoTService
import javax.inject.Inject

class ApiRespository @Inject constructor(private val ioTService : IoTService){

    suspend fun loginInApi(requestLogin: RequestLogin): String?{
        return ioTService.loginInApi(requestLogin)
    }

    suspend fun findLatestDHT11Data(token: String): DHT11Data{
        return ioTService.findLatestDHT11Data(token)
    }

    suspend fun findAllDHT11Records(token: String): List<DHT11Data>{
        return ioTService.findAllDHT11Records(token)
    }

    suspend fun deleteDHT11Records(token: String): String? {
        return ioTService.deleteDHTRecords(token)
    }

    suspend fun findAllDoors(token: String): List<Door>{
        return ioTService.findAllDoors(token)
    }

    suspend fun updateDoor(token: String, door: Door): Boolean{
        return ioTService.updateDoor(token,door)
    }

    suspend fun listAllLightbulbs(token: String):List<Lightbulb>{
        return ioTService.findAllLightbulbs(token)
    }

    suspend fun updateLightbulb(token: String, lightbulb: Lightbulb):Boolean{
        return ioTService.updateLightbulb(token,lightbulb)
    }

    suspend fun listAllAlarms(token: String):List<Alarm>{
        return ioTService.findAllAlarms(token)
    }

    suspend fun updateAlarm(token: String, alarm: Alarm):Boolean{
        return ioTService.updateAlarm(token,alarm)
    }
    suspend fun listAllNotifiactions(token: String):List<Notification>{
        return ioTService.findAllNotifications(token)
    }

    suspend fun deleteAllNotifications(token: String): Boolean{
        return ioTService.deleteAllNotifications(token)
    }
}