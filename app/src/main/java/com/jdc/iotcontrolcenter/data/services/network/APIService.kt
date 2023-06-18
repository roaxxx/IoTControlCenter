package com.jdc.iotcontrolcenter.data.services.network

import com.jdc.iotcontrolcenter.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface APIService {
    @POST("login")
    suspend fun login(@Body requestLogin: RequestLogin): Response<String?>

    @GET("dhtSensor/getLatest")
    suspend fun getLastDHT11Data(@Header("Authorization") token: String):Response<DHT11Data>

    @GET("dhtSensor/listDHT11Data")
    suspend fun listAllDHTData(@Header("Authorization") token: String): Response<List<DHT11Data>>

    @GET("dhtSensor/delete")
    suspend fun deleteDHT11Data(@Header("Authorization") token: String): Response<String?>

    @GET("door/listDoors")
    suspend fun findAllDoors(@Header("Authorization") token: String): Response<List<Door>>

    @POST("door/updateDoor")
    suspend fun updateDoor(@Header("Authorization") token: String, @Body door: Door): Response<Boolean>

    @GET("alarm/findAlarms")
    suspend fun findAllAlarms(@Header("Authorization") token: String):Response<List<Alarm>>

    @POST("alarm/UpdateAlarm")
    suspend fun updateAlarm(@Header("Authorization") token: String): Response<Boolean>

    @GET("lightbulb/list")
    suspend fun listLightbulbs(@Header("Authorization") token: String): Response<List<Lightbulb>>

    @POST("lightbulb/update")
    suspend fun updateLightbulbState(@Header("Authorization") token: String,@Body lightbulb: Lightbulb): Response<Boolean>

    @GET("alarm/findAlarms")
    suspend fun listAllAlarms(@Header("Authorization") token: String): Response<List<Alarm>>

    @POST("alarm/UpdateAlarm")
    suspend fun updateAlarm(@Header("Authorization") token: String,@Body alarm: Alarm): Response<Boolean>

    @GET("notifications/listNotifications")
    suspend fun listNotifications(@Header("Authorization") token: String,): Response<List<Notification>>

    @GET("notifications/deleteAll")
    suspend fun deleteAllNotifications(@Header("Authorization") token: String): Response<Boolean>
}