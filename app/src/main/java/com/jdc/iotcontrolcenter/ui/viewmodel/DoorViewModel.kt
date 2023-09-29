package com.jdc.iotcontrolcenter.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdc.iotcontrolcenter.data.model.Door
import com.jdc.iotcontrolcenter.domain.DoorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.jdc.iotcontrolcenter.data.Result

@HiltViewModel
class DoorViewModel @Inject constructor(
    private val doorManager: DoorUseCase
): ViewModel() {

    val isUpdateDoorObservable = MutableLiveData<Result<Door>>()

    fun updateDoorOnline(idDoor: Int, newDoorState: String) {
        viewModelScope.launch {
            isUpdateDoorObservable.postValue(doorManager.updateDoorstate(idDoor, newDoorState))
        }
    }
}