package com.jdc.iotcontrolcenter.domain

import com.jdc.iotcontrolcenter.data.model.Door

interface DoorUseCase {

    suspend fun getAllDoors(): MutableList<Door>

    suspend fun updateDoorstate(door: Door): Boolean
}