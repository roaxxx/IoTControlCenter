package com.jdc.iotcontrolcenter.data

import com.jdc.iotcontrolcenter.data.model.*
import com.jdc.iotcontrolcenter.data.services.network.IoTService

class ApiRespository {

    private val ioTService = IoTService()

    suspend fun loginInApi(requestLogin: RequestLogin): String?{
        return ioTService.loginInApi(requestLogin)
    }

    suspend fun findLatestDHT11Data(): DHT11Data{
        return ioTService.findLatestDHT11Data()
    }

    suspend fun findAllDHT11Records(): List<DHT11Data>{
        return ioTService.findAllDHT11Records()
    }

    suspend fun deleteDHT11Records(): String? {
        return ioTService.deleteDHTRecords()
    }

    suspend fun findAllDoors(): List<Door>{
        return ioTService.findAllDoors()
    }

    suspend fun updateDoor(door: Door): Boolean{
        return ioTService.updateDoor(door)
    }

    suspend fun listAllLightbulbs():List<Lightbulb>{
        return ioTService.findAllLightbulbs()
    }

    suspend fun updateLightbulb(lightbulb: Lightbulb):Boolean{
        return ioTService.updateLightbulb(lightbulb)
    }

    suspend fun listAllAlarms():List<Alarm>{
        return ioTService.findAllAlarms()
    }

    suspend fun updateAlarm(alarm: Alarm):Boolean{
        return ioTService.updateAlarm(alarm)
    }
    suspend fun listAllNotifiactions():List<Notification>{
        return ioTService.findAllNotifications()
    }

    suspend fun deleteAllNotifications(): Boolean{
        return ioTService.deleteAllNotifications()
    }
}