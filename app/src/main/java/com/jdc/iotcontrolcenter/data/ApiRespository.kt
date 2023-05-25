package com.jdc.iotcontrolcenter.data

import com.jdc.iotcontrolcenter.data.model.DHT11Data
import com.jdc.iotcontrolcenter.data.model.Door
import com.jdc.iotcontrolcenter.data.model.Lightbulb
import com.jdc.iotcontrolcenter.data.model.RequestLogin
import com.jdc.iotcontrolcenter.data.services.network.IoTService

class ApiRespository {
    private val ioTService = IoTService()

    suspend fun loginInApi(requestLogin: RequestLogin): String{
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
}