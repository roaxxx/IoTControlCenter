package com.jdc.iotcontrolcenter.domain.impl

import android.util.Log
import com.jdc.iotcontrolcenter.data.ApiRespository
import com.jdc.iotcontrolcenter.data.model.Notification
import com.jdc.iotcontrolcenter.domain.NotificationUseCase
import okio.IOException
import javax.inject.Inject

class NotificationUseCaseImpl @Inject constructor(
    private val apiRepository: ApiRespository,
    private val sessionUseCaseImpl: SessionUseCaseImpl
) : NotificationUseCase {

    override suspend fun getAllNotifications(): MutableList<Notification> {
        return try {
            apiRepository.listAllNotifiactions(sessionUseCaseImpl.getToken()).toMutableList()
        } catch (e: IOException) {
            Log.e("httpGetNots", e.toString())
            emptyList<Notification>().toMutableList()
        }
    }

    override suspend fun deleteAllNotifications(): Boolean {
        return try {
            apiRepository.deleteAllNotifications(sessionUseCaseImpl.getToken())
        } catch (e: IOException) {
            Log.e("httpGetNots", e.toString())
            false
        }
    }
}