package com.jdc.iotcontrolcenter.domain

import android.util.Log
import com.jdc.iotcontrolcenter.data.ApiRespository
import com.jdc.iotcontrolcenter.data.model.Notification
import okio.IOException
import javax.inject.Inject

class NotificationManager @Inject constructor(private val apiRepository : ApiRespository) {

    suspend fun getAllNotifications(): MutableList<Notification>{
        return try{
            apiRepository.listAllNotifiactions().toMutableList()
        }catch (e: IOException){
            Log.e("httpGetNots",e.toString())
            emptyList<Notification>().toMutableList()
        }
    }

    suspend fun deleteAllNotifications():Boolean{
        return try{
            apiRepository.deleteAllNotifications()
        }catch (e: IOException){
            Log.e("httpGetNots",e.toString())
            false
        }
    }
}