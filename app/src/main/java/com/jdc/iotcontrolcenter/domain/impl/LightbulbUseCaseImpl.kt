package com.jdc.iotcontrolcenter.domain.impl

import android.util.Log
import com.jdc.iotcontrolcenter.data.ApiRespository
import com.jdc.iotcontrolcenter.data.model.Lightbulb
import com.jdc.iotcontrolcenter.domain.LightbulbUseCase
import okio.IOException
import javax.inject.Inject

class LightbulbUseCaseImpl @Inject constructor(
    private val apiRepository: ApiRespository,
    private val sessionUseCaseImpl: SessionUseCaseImpl
) : LightbulbUseCase {

    /**
     * Lists all available lightbulbs.
     * @return a mutable list of lightbulbs, or an empty list if an error occurs.
     */
    override suspend fun listLightbulbs(): MutableList<Lightbulb> {
        return try {
            apiRepository.listAllLightbulbs(sessionUseCaseImpl.getToken()).toMutableList()
        } catch (e: IOException) {
            Log.e("okhttplistlightbubls", "$e")
            emptyList<Lightbulb>().toMutableList()
        }
    }

    /**
     * Updates the state of a lightbulb.
     * @param lightbulb the lightbulb to update.
     * @return `true` if the update was successful, `false` if an error occurred.
     */
    override suspend fun updateLightbulb(lightbulb: Lightbulb): Boolean {
        return try {
            apiRepository.updateLightbulb(sessionUseCaseImpl.getToken(),lightbulb)
        } catch (e: IOException) {
            Log.e("okhttpupdatelightbubls", "$e")
            return false
        }
    }
}