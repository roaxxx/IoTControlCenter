package com.jdc.iotcontrolcenter.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdc.iotcontrolcenter.data.model.Lightbulb
import com.jdc.iotcontrolcenter.domain.LightbulbManager
import kotlinx.coroutines.launch
import javax.inject.Inject

class LightbulbViewModel @Inject constructor(private val lightbulbManager: LightbulbManager): ViewModel()  {
    val lightbulListViewModel = MutableLiveData<MutableList<Lightbulb>>()
    val isLightbulbUpdated = MutableLiveData<Boolean>()

    fun getLightbulbsList(){
        viewModelScope.launch {
            lightbulListViewModel.postValue(lightbulbManager.listLightbulbs())
        }
    }

    fun updateLightbulbState(lightbulb: Lightbulb){
        viewModelScope.launch {
            isLightbulbUpdated.postValue(false)
            isLightbulbUpdated.postValue(lightbulbManager.updateLightbulb(lightbulb))
        }
    }
}