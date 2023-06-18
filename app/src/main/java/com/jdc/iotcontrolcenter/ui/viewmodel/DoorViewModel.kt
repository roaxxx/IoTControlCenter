package com.jdc.iotcontrolcenter.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdc.iotcontrolcenter.data.model.Door
import com.jdc.iotcontrolcenter.domain.impl.DoorUseCaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DoorViewModel @Inject constructor(private val doorManager: DoorUseCaseImpl): ViewModel() {

    val doorListObservable = MutableLiveData<MutableList<Door>>()
    val isUpdateDoorObservable = MutableLiveData<Boolean>()

    fun getAllDoorsOnline() {
        viewModelScope.launch {
            doorListObservable.postValue(doorManager.getAllDoors())
        }
    }

    fun updateDoorOnline(door: Door) {
        viewModelScope.launch {
            isUpdateDoorObservable.postValue(false)
            isUpdateDoorObservable.postValue(doorManager.updateDoorstate(door))
        }
    }
}