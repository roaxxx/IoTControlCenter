package com.jdc.iotcontrolcenter.domain

import com.jdc.iotcontrolcenter.data.model.Notification

interface NotificationUseCase {

    suspend fun getAllNotifications(): MutableList<Notification>

    suspend fun deleteAllNotifications(): Boolean
}