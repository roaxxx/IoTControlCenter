package com.jdc.iotcontrolcenter.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdc.iotcontrolcenter.data.model.Notification
import com.jdc.iotcontrolcenter.domain.impl.NotificationUseCaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.jdc.iotcontrolcenter.data.Result

@HiltViewModel
class NotificationViewModel @Inject constructor(private val notificationUseCaseImpl: NotificationUseCaseImpl): ViewModel(){
    val notificationsModel = MutableLiveData<Result<List<Notification>>>()
    val isClearNotificationsModel = MutableLiveData<Result<Boolean>>()

    fun getAllNotifications(){
        viewModelScope.launch {
            notificationsModel.postValue(notificationUseCaseImpl.getAllNotifications())
        }
    }

    fun deleteAllNotifications(){
        viewModelScope.launch {
            isClearNotificationsModel.postValue(notificationUseCaseImpl.deleteAllNotifications())
        }
    }
}