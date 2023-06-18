package com.jdc.iotcontrolcenter.domain

import com.jdc.iotcontrolcenter.data.model.Lightbulb

interface LightbulbUseCase {

    suspend fun listLightbulbs(): MutableList<Lightbulb>

    suspend fun updateLightbulb(lightbulb: Lightbulb): Boolean
}