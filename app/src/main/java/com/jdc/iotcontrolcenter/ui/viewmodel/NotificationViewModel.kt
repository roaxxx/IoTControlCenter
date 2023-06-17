package com.jdc.iotcontrolcenter.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdc.iotcontrolcenter.data.model.Notification
import com.jdc.iotcontrolcenter.domain.NotificationManager
import kotlinx.coroutines.launch
import javax.inject.Inject

class NotificationViewModel @Inject constructor(private val notificationManager: NotificationManager): ViewModel(){
    val noticationLisObservable = MutableLiveData<MutableList<Notification>>()
    val isDeleteNotificationsObservable = MutableLiveData<Boolean>()

    fun getAllNotifications(){
        viewModelScope.launch {
            noticationLisObservable.postValue(notificationManager.getAllNotifications())
        }
    }

    fun deleteAllNotifications(){
        viewModelScope.launch {
            isDeleteNotificationsObservable.postValue(notificationManager.deleteAllNotifications())
        }
    }
}