package com.jdc.iotcontrolcenter.data

import com.jdc.iotcontrolcenter.data.model.*

interface ApiRepository {

    suspend fun loginInApi(requestLogin: RequestLogin): Result<String>

    suspend fun getCurrentUser(token: String): Result<String>

    suspend fun getIotInformation(token: String): Result<IoTInformationDTO>
    suspend fun findAllDHT11Records(token: String): Result<List<DHT11Data>>

    suspend fun deleteDHT11Records(token: String): Result<Boolean>

    suspend fun updateDoor(token: String, idDoor: Int, newDoorState: String): Result<Door>

    suspend fun listAllLightbulbs(token: String): Result<List<Lightbulb>>

    suspend fun updateLightbulb(
        token: String,
        idLightbulb: Int,
        lightbulbValue: String
    ): Result<Lightbulb>

    suspend fun updateAlarm(
        token: String,
        idAlarm: String,
        alarmConditionsDTO: AlarmConditionsDTO
    ): Result<Alarm>
    suspend fun listAllNotifiactions(token: String): Result<List<Notification>>

    suspend fun deleteAllNotifications(token: String): Result<Boolean>
}