package com.jdc.iotcontrolcenter.data.services.network

import com.jdc.iotcontrolcenter.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface APIService {
    @POST("auth/login")
    suspend fun login(
        @Body requestLogin: RequestLogin
    ): Response<String>
    
    @GET("IoTInfo")
    suspend fun getIoTInformation(
        @Header("Authorization") token: String
    ): Response<IoTInformationDTO>

    @GET("dhtSensor")
    suspend fun getAllDHT11Data(
        @Header("Authorization") token: String
    ): Response<List<DHT11Data>>

    @DELETE("dhtSensor")
    suspend fun deleteDHT11Data(
        @Header("Authorization") token: String
    ): Response<Boolean>

    @PATCH("door/{idDoor}")
    suspend fun updateDoor(
        @Header("Authorization") token: String,
        @Path("idDoor") idDoor: Int,
        @Body doorState: String
    ): Response<Door>

    @PATCH("alarms/{alarmId}")
    suspend fun updateAlarm(
        @Header("Authorization") token: String,
        @Path("alarmId") alarmId: String,
        @Body alarmConditionsDTO: AlarmConditionsDTO
    ): Response<Alarm>

    @PATCH("alarm/{alarmId}")
    suspend fun updateAlarm(
        @Header("Authorization") token: String,
        @Path("alarmId") alarmId: String,
        @Body alarm: Alarm
    ): Response<Alarm>


    @PATCH("lightbulb/{idLightbulb}")
    suspend fun updateLightbulbState(
        @Header("Authorization") token: String,
        @Path("idLightbulb") idLightbulb: Int,
        @Body lightbulbState: String
    ): Response<Lightbulb>

    @GET("lightbulb")
    suspend fun listLightbulbs(@Header("Authorization") token: String): Response<List<Lightbulb>>


    @GET("notifications")
    suspend fun listNotifications(
        @Header("Authorization") token: String
    ): Response<List<Notification>>

    @DELETE("notifications")
    suspend fun deleteAllNotifications(
        @Header("Authorization") token: String
    ): Response<Boolean>

    @GET("auth/getCurrentUser")
    suspend fun getCurrentUser(@Header("Authorization") token: String): Response<String?>
}