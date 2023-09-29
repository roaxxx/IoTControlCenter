package com.jdc.iotcontrolcenter.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdc.iotcontrolcenter.data.model.DHT11Data
import com.jdc.iotcontrolcenter.domain.Dht11SensorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.jdc.iotcontrolcenter.data.Result

@HiltViewModel
class Dht11SensorViewModel @Inject constructor(
    private val dht11SensorManager: Dht11SensorUseCase
): ViewModel() {

    val clearDhtDataModel = MutableLiveData<Result<Boolean>>()
    val dhtRecordModel = MutableLiveData<Result<List<DHT11Data>>>()


    fun getAllDhtRecordList(){
        viewModelScope.launch {
            dhtRecordModel.postValue(dht11SensorManager.getAllDht11Record())
        }
    }

    fun clearDhtSensorRecords(){
        viewModelScope.launch {
            clearDhtDataModel.postValue(dht11SensorManager.clearRecords())
        }
    }
}