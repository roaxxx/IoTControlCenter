package com.jdc.iotcontrolcenter.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdc.iotcontrolcenter.data.model.DHT11Data
import com.jdc.iotcontrolcenter.domain.impl.Dht11SensorUseCaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class Dht11SensorViewModel @Inject constructor(private val dht11SensorManager: Dht11SensorUseCaseImpl): ViewModel() {
    val dhtSesorLastRecordObservable = MutableLiveData<DHT11Data>()
    val dhtRecordListObservable = MutableLiveData<MutableList<DHT11Data>>()


    fun getLastRecord(){
        viewModelScope.launch {
            dhtSesorLastRecordObservable.postValue(dht11SensorManager.getSensorLatestData())
        }
    }

    fun getAllDhtRecordList(){
        viewModelScope.launch {
            dhtRecordListObservable.postValue(dht11SensorManager.getAllDHTRecords())
        }
    }

    fun clearDhtSensorRecords(){
        viewModelScope.launch { dht11SensorManager.clearRecords() }
    }
}