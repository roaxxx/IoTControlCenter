package com.jdc.iotcontrolcenter.domain

import com.jdc.iotcontrolcenter.data.model.Door
import com.jdc.iotcontrolcenter.data.Result

interface DoorUseCase {

    suspend fun updateDoorstate(idDoor: Int, newDoorState: String): Result<Door>
}