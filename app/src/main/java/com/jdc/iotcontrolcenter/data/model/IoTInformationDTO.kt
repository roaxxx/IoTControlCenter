package com.jdc.iotcontrolcenter.data.model

data class IoTInformationDTO(
    val lastRecord: DHT11Data,
    val alarms:     List<Alarm>,
    val doors:      List<Door>
)
