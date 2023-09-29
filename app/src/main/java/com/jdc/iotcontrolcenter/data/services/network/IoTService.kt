package com.jdc.iotcontrolcenter.data.services.network

import com.jdc.iotcontrolcenter.data.ApiRepository
import com.jdc.iotcontrolcenter.data.Result
import com.jdc.iotcontrolcenter.data.model.*
import com.jdc.iotcontrolcenter.di.NetworkModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.IOException
import java.net.ConnectException
import javax.inject.Inject

class IoTService @Inject constructor(): ApiRepository {

    private val retrofitService = NetworkModule.providesIoTApiClient()

    override suspend fun loginInApi(requestLogin: RequestLogin): Result<String> {
        return  try {
            withContext(Dispatchers.IO) {
                val response = retrofitService.login(requestLogin)
                if(response.isSuccessful ){
                    Result.Success(response.body()!!)
                }else{
                    Result.Error(IllegalStateException("ERR_ON_RESPONSE${response.code()},${response.errorBody()}"))
                }
            }
        } catch (e: IOException){
            Result.Error(IOException("SERVER_CONN_ERR"))
        }  catch (ex: ConnectException){
        Result.Error(IOException("SERVER_CONN_ERR $ex"))
        }
    }

    override suspend fun getIotInformation(token: String): Result<IoTInformationDTO> {
        return try {
            withContext(Dispatchers.IO) {
                val response = retrofitService.getIoTInformation(token)
                if(response.isSuccessful ){
                    Result.Success(response.body()!!)
                }else{
                    Result.Error(IllegalStateException("ERR_ON_RESPONSE${response.code()},${response.errorBody()}"))
                }
            }
        } catch (e: IOException){
            Result.Error(IOException("1 $e"))
        } catch (ex: ConnectException){
            Result.Error(IOException("2 $ex"))
        }
    }

    override suspend fun findAllDHT11Records(token: String): Result<List<DHT11Data>> {

        return try {
            withContext(Dispatchers.IO) {
                val response = retrofitService.getAllDHT11Data(token)
                if(response.isSuccessful ){
                    Result.Success(response.body() ?: emptyList())
                }else{
                    Result.Error(IllegalStateException("ERR_ON_RESPONSE${response.code()},${response.errorBody()}"))
                }
            }
        } catch (e: IOException){
            Result.Error(IOException("SERVER_CONN_ERR"))
        }
    }

    override suspend fun deleteDHT11Records(token: String): Result<Boolean> {
        return try {
            withContext(Dispatchers.IO) {
                val response = retrofitService.deleteDHT11Data(token)
                if(response.isSuccessful ){
                    Result.Success(response.body()!!)
                }else{
                    Result.Error(IllegalStateException("ERR_ON_RESPONSE${response.code()},${response.errorBody()}"))
                }
            }
        } catch (e: IOException){
            Result.Error(IOException("SERVER_CONN_ERR"))
        }
    }


    override suspend fun updateDoor(token: String, idDoor: Int, doorState: String): Result<Door> {
        return try {
            withContext(Dispatchers.IO) {
                val response = retrofitService.updateDoor(token, idDoor,doorState)
                if(response.isSuccessful ){
                    Result.Success(response.body()!!)
                }else{
                    Result.Error(IllegalStateException("ERR_ON_RESPONSE${response.code()},${response.errorBody()}"))
                }
            }
        } catch (e: IOException){
            Result.Error(IOException("SERVER_CONN_ERR"))
        }
    }

    override suspend fun listAllLightbulbs(token: String): Result<List<Lightbulb>> {
        return try {
            withContext(Dispatchers.IO) {
                val response = retrofitService.listLightbulbs(token)
                if(response.isSuccessful ){
                    Result.Success(response.body()!!)
                }else{
                    Result.Error(IllegalStateException("ERR_ON_RESPONSE${response.code()},${response.errorBody()}"))
                }
            }
        } catch (e: IOException){
            Result.Error(IOException("SERVER_CONN_ERR1"))
        } catch (ex: ConnectException){
            Result.Error(IOException("SERVER_CONN_ERR2 $ex"))
        }
    }

    override suspend fun updateLightbulb(
        token: String,
        idLightbulb: Int,
        lightbulbValue: String
    ): Result<Lightbulb> {
        return try {
            withContext(Dispatchers.IO) {
                val response = retrofitService.updateLightbulbState(token, idLightbulb, lightbulbValue)
                if(response.isSuccessful ){
                    Result.Success(response.body()!!)
                }else{
                    Result.Error(IllegalStateException("ERR_ON_RESPONSE${response.code()},${response.errorBody()}"))
                }
            }
        } catch (e: IOException){
            Result.Error(IOException("SERVER_CONN_ERR"))
        }
    }

    override suspend fun updateAlarm(
        token: String,
        idAlarm: String,
        alarmConditionsDTO: AlarmConditionsDTO
    ): Result<Alarm> {
        return try {
            withContext(Dispatchers.IO) {
                val response = retrofitService.updateAlarm(token, idAlarm, alarmConditionsDTO)
                if(response.isSuccessful ){
                    Result.Success(response.body()!!)
                }else{
                    Result.Error(IllegalStateException("ERR_ON_RESPONSE${response.code()},${response.errorBody()}"))
                }
            }
        } catch (e: IOException){
            Result.Error(IOException("SERVER_CONN_ERR"))
        }
    }

    override suspend fun listAllNotifiactions(token: String): Result<List<Notification>> {
        return try {
            withContext(Dispatchers.IO) {
                val response = retrofitService.listNotifications(token)
                if(response.isSuccessful ){
                    Result.Success(response.body()!!)
                }else{
                    Result.Error(IllegalStateException("ERR_ON_RESPONSE${response.code()},${response.errorBody()}"))
                }
            }
        } catch (e: IOException){
            Result.Error(IOException("SERVER_CONN_ERR"))
        }
    }

    override suspend fun deleteAllNotifications(token: String): Result<Boolean> {
        return try {
            withContext(Dispatchers.IO) {
                val response = retrofitService.deleteAllNotifications(token)
                if(response.isSuccessful ){
                    Result.Success(response.body()!!)
                }else{
                    Result.Error(IllegalStateException("ERR_ON_RESPONSE${response.code()},${response.errorBody()}"))
                }
            }
        } catch (e: IOException){
            Result.Error(IOException("SERVER_CONN_ERR"))
        }
    }

    override suspend fun getCurrentUser(token: String): Result<String> {
        return try {
            withContext(Dispatchers.IO) {
                val response = retrofitService.getCurrentUser(token)
                if(response.isSuccessful ){
                    Result.Success(response.body()!!)
                }else{
                    Result.Error(IllegalStateException("ERR_ON_RESPONSE${response.code()},${response.errorBody()}"))
                }
            }
        } catch (e: IOException){
            Result.Error(IOException("SERVER_CONN_ERR"))
        }
    }

}