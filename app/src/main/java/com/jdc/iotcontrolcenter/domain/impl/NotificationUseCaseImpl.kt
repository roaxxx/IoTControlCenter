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

    /**
     * Retrieves all notifications.
     * @return A mutable list of notifications.
     */
    override suspend fun getAllNotifications(): MutableList<Notification> {
        return try {
            apiRepository.listAllNotifiactions(sessionUseCaseImpl.getToken()).toMutableList()
        } catch (e: IOException) {
            Log.e("httpGetNots", e.toString())
            emptyList<Notification>().toMutableList()
        }
    }

    /**
     * Deletes all notifications.
     * @return True if the deletion was successful, false otherwise.
     */
    override suspend fun deleteAllNotifications(): Boolean {
        return try {
            apiRepository.deleteAllNotifications(sessionUseCaseImpl.getToken())
        } catch (e: IOException) {
            Log.e("httpGetNots", e.toString())
            false
        }
    }
}