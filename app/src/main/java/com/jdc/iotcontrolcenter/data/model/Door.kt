package com.jdc.iotcontrolcenter.data.model

data class Door(
    val idDoor: Int,
    val doorName: String,
    var doorState: String,
    val updateDate: String
)
