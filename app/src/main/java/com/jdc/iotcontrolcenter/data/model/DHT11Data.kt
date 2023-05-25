package com.jdc.iotcontrolcenter.data.model

data class DHT11Data(
    val measurementId: Int,
    val temperature: Int,
    val humidity: Int,
    val date: String,
    val time: String
)
