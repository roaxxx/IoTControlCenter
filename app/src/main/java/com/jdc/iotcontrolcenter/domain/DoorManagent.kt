package com.jdc.iotcontrolcenter.domain

import com.jdc.iotcontrolcenter.data.ApiRespository
import com.jdc.iotcontrolcenter.data.model.Door
import okio.IOException

class DoorManagent {

    private val apiRespository = ApiRespository()

    suspend fun getAllDoors(): MutableList<Door>{
        return try{
            apiRespository.findAllDoors().toMutableList()
        }catch (e : IOException){
            mutableListOf<Door>()
        }
    }

    suspend fun updateDoorstate(door: Door): Boolean{
        return try{
            apiRespository.updateDoor(door)
        }catch (e : IOException){
            false
        }
    }
}