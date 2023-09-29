package com.jdc.iotcontrolcenter.domain.impl

import android.util.Log
import com.jdc.iotcontrolcenter.data.ApiRepository
import com.jdc.iotcontrolcenter.data.model.Notification
import com.jdc.iotcontrolcenter.domain.NotificationUseCase
import com.jdc.iotcontrolcenter.domain.SessionUseCase
import okio.IOException
import javax.inject.Inject
import com.jdc.iotcontrolcenter.data.Result

class NotificationUseCaseImpl @Inject constructor(
    private val apiRepository: ApiRepository,
    private val sessionUseCase: SessionUseCase
) : NotificationUseCase {

    /**
     * Retrieves all notifications.
     * @return A mutable list of notifications.
     */
    override suspend fun getAllNotifications(): Result<List<Notification>> {
        return  apiRepository.listAllNotifiactions(sessionUseCase.getToken())
    }

    /**
     * Deletes all notifications.
     * @return True if the deletion was successful, false otherwise.
     */
    override suspend fun deleteAllNotifications(): Result<Boolean> {
        return apiRepository.deleteAllNotifications(sessionUseCase.getToken())
    }
}