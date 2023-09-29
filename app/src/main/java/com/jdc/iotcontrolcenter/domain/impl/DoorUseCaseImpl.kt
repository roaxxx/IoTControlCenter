package com.jdc.iotcontrolcenter.domain.impl

import com.jdc.iotcontrolcenter.data.ApiRepository
import com.jdc.iotcontrolcenter.data.model.Door
import com.jdc.iotcontrolcenter.domain.DoorUseCase
import com.jdc.iotcontrolcenter.domain.SessionUseCase
import okio.IOException
import javax.inject.Inject
import com.jdc.iotcontrolcenter.data.Result

class DoorUseCaseImpl @Inject constructor(
    private val apiRespository: ApiRepository,
    private val sessionUseCase: SessionUseCase
) : DoorUseCase {

    /**
     * Updates the state of a door.
     * @param door the door to update.
     * @return `true` if the update was successful, or `false` in case of error.
     */
    override suspend fun updateDoorstate(idDoor: Int, newDoorState: String): Result<Door> {
        return apiRespository.updateDoor(sessionUseCase.getToken(),idDoor, newDoorState)

    }
}