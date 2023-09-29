package com.jdc.iotcontrolcenter.domain.impl

import com.jdc.iotcontrolcenter.data.ApiRepository
import com.jdc.iotcontrolcenter.data.model.Lightbulb
import com.jdc.iotcontrolcenter.domain.LightbulbUseCase
import com.jdc.iotcontrolcenter.domain.SessionUseCase
import javax.inject.Inject
import com.jdc.iotcontrolcenter.data.Result

class LightbulbUseCaseImpl @Inject constructor(
    private val apiRepository: ApiRepository,
    private val sessionUseCase: SessionUseCase
) : LightbulbUseCase {

    /**
     * Lists all available lightbulbs.
     * @return a mutable list of lightbulbs, or an empty list if an error occurs.
     */
    override suspend fun listLightbulbs(): Result<List<Lightbulb>> {
        return  apiRepository.listAllLightbulbs(sessionUseCase.getToken())
    }

    /**
     * Updates the state of a lightbulb.
     * @param idLighbulb id of lightbulb to update
     * @param lightbulbValue new value of lightbulb
     * @return Lightbulb updated
     */
    override suspend fun updateLightbulb(idLighbulb: Int, lightbulbValue: String): Result<Lightbulb> {
        return apiRepository.updateLightbulb(sessionUseCase.getToken(), idLighbulb, lightbulbValue)

    }
}