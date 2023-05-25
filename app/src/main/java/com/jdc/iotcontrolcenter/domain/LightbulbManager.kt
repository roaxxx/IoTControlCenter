package com.jdc.iotcontrolcenter.domain

import android.util.Log
import com.jdc.iotcontrolcenter.data.ApiRespository
import com.jdc.iotcontrolcenter.data.model.Lightbulb
import okio.IOException

class LightbulbManager {

    private val apiRepository = ApiRespository()

    suspend fun listLightbulbs():MutableList<Lightbulb>{
        return try {
            apiRepository.listAllLightbulbs().toMutableList()
        }catch (e :IOException){
            Log.e("okhttplistlightbubls","$e")
            emptyList<Lightbulb>().toMutableList()
        }
    }

    suspend fun updateLightbulb(lightbulb: Lightbulb):Boolean{
        return try {
            apiRepository.updateLightbulb(lightbulb)
        }catch (e :IOException){
            Log.e("okhttpupdatelightbubls","$e")
            return false
        }
    }
}