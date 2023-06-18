package com.jdc.iotcontrolcenter.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdc.iotcontrolcenter.data.model.Notification
import com.jdc.iotcontrolcenter.domain.impl.NotificationUseCaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(private val notificationUseCaseImpl: NotificationUseCaseImpl): ViewModel(){
    val noticationLisObservable = MutableLiveData<MutableList<Notification>>()
    val isDeleteNotificationsObservable = MutableLiveData<Boolean>()

    fun getAllNotifications(){
        viewModelScope.launch {
            noticationLisObservable.postValue(notificationUseCaseImpl.getAllNotifications())
        }
    }

    fun deleteAllNotifications(){
        viewModelScope.launch {
            isDeleteNotificationsObservable.postValue(notificationUseCaseImpl.deleteAllNotifications())
        }
    }
}