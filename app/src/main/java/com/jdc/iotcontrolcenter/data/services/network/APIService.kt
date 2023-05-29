package com.jdc.iotcontrolcenter.data.services.network

import com.jdc.iotcontrolcenter.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface APIService {

    @POST("login")
    suspend fun login(@Body requestLogin: RequestLogin): Response<String>

    @GET("dhtSensor/getLatest")
    suspend fun getLastDHT11Data():Response<DHT11Data>

    @GET("dhtSensor/listDHT11Data")
    suspend fun listAllDHTData(): Response<List<DHT11Data>>

    @GET("dhtSensor/delete")
    suspend fun deleteDHT11Data(): Response<String?>

    @GET("door/listDoors")
    suspend fun findAllDoors(): Response<List<Door>>

    @POST("door/updateDoor")
    suspend fun updateDoor(@Body door: Door): Response<Boolean>

    @GET("alarm/findAlarms")
    suspend fun findAllAlarms():Response<List<Alarm>>

    @POST("alarm/UpdateAlarm")
    suspend fun updateAlarm(): Response<Boolean>

    @GET("lightbulb/list")
    suspend fun listLightbulbs(): Response<List<Lightbulb>>

    @POST("lightbulb/update")
    suspend fun updateLightbulbState(@Body lightbulb: Lightbulb): Response<Boolean>

    @GET("alarm/findAlarms")
    suspend fun listAllAlarms(): Response<List<Alarm>>

    @POST("alarm/UpdateAlarm")
    suspend fun updateAlarm(@Body alarm: Alarm): Response<Boolean>

    @GET("notifications/listNotifications")
    suspend fun listNotifications(): Response<List<Notification>>

    @GET("notifications/deleteAll")
    suspend fun deleteAllNotifications(): Response<Boolean>
}