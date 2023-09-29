package com.jdc.iotcontrolcenter.domain

import com.jdc.iotcontrolcenter.data.model.Lightbulb
import com.jdc.iotcontrolcenter.data.Result

interface LightbulbUseCase {

    suspend fun listLightbulbs(): Result<List<Lightbulb>>

    suspend fun updateLightbulb(idLighbulb: Int, lightbulbValue: String): Result<Lightbulb>
}