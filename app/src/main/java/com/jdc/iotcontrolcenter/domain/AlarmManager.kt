package com.jdc.iotcontrolcenter.domain

import android.util.Log
import com.jdc.iotcontrolcenter.data.ApiRespository
import com.jdc.iotcontrolcenter.data.model.Alarm
import okio.IOException
import javax.inject.Inject

class AlarmManager @Inject constructor(private val apiRepository : ApiRespository) {

    suspend fun getAllAlarms(): MutableList<Alarm>{
        return try{
            apiRepository.listAllAlarms().toMutableList()
        }catch (e: IOException){
            Log.e("getAllAlarms", e.toString())
            emptyList<Alarm>().toMutableList()
        }
    }

    suspend fun updateAlarm(alarm: Alarm): Boolean{
        return try{
            apiRepository.updateAlarm(alarm)
        }catch(e : IOException){
            Log.e("updateAlarms",e.toString())
            false
        }
    }
}