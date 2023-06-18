package com.jdc.iotcontrolcenter.domain.impl

import com.jdc.iotcontrolcenter.data.ApiRespository
import com.jdc.iotcontrolcenter.data.model.Door
import com.jdc.iotcontrolcenter.domain.DoorUseCase
import okio.IOException
import javax.inject.Inject

class DoorUseCaseImpl @Inject constructor(
    private val apiRespository: ApiRespository,
    private val sessionUseCaseImpl: SessionUseCaseImpl
) : DoorUseCase {

    /**
     * Retrieves all doors.
     * @return a mutable list of doors, or an empty list if there is any error.
     */
    override suspend fun getAllDoors(): MutableList<Door> {
        return try {
            apiRespository.findAllDoors(sessionUseCaseImpl.getToken()).toMutableList()
        } catch (e: IOException) {
            mutableListOf()
        }
    }

    /**
     * Updates the state of a door.
     * @param door the door to update.
     * @return `true` if the update was successful, or `false` in case of error.
     */
    override suspend fun updateDoorstate(door: Door): Boolean {
        return try {
            apiRespository.updateDoor(sessionUseCaseImpl.getToken(),door)
        } catch (e: IOException) {
            false
        }
    }
}