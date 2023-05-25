package com.jdc.iotcontrolcenter.data.model

data class Notification(
    val notificationId: Int,
    val alarmId: String,
    val notificationDate: String,
    val notificationTime: String,
    val trigger: Int
)
