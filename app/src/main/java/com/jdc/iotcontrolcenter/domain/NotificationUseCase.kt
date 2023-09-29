package com.jdc.iotcontrolcenter.domain

import com.jdc.iotcontrolcenter.data.model.Notification
import com.jdc.iotcontrolcenter.data.Result

interface NotificationUseCase {

    suspend fun getAllNotifications(): Result<List<Notification>>

    suspend fun deleteAllNotifications(): Result<Boolean>
}