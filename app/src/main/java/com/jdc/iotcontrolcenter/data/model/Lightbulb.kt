package com.jdc.iotcontrolcenter.data.model

data class Lightbulb(
    val idLightbulbs: Int,
    val ubication: String,
    val bulbControlerType: String,
    var bulbValue: String
    )
