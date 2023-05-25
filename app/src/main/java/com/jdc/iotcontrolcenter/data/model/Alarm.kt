package com.jdc.iotcontrolcenter.data.model

data class Alarm(
    val idAlarm: String,
    val alarmName: String,
    var alarmState: String,
    var condition: Int
)
