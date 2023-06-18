package com.jdc.iotcontrolcenter.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdc.iotcontrolcenter.data.model.Lightbulb
import com.jdc.iotcontrolcenter.domain.impl.LightbulbUseCaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LightbulbViewModel @Inject constructor(private val lightbulbUseCaseImpl: LightbulbUseCaseImpl): ViewModel()  {
    val lightbulListViewModel = MutableLiveData<MutableList<Lightbulb>>()
    val isLightbulbUpdated = MutableLiveData<Boolean>()

    fun getLightbulbsList(){
        viewModelScope.launch {
            lightbulListViewModel.postValue(lightbulbUseCaseImpl.listLightbulbs())
        }
    }

    fun updateLightbulbState(lightbulb: Lightbulb){
        viewModelScope.launch {
            isLightbulbUpdated.postValue(false)
            isLightbulbUpdated.postValue(lightbulbUseCaseImpl.updateLightbulb(lightbulb))
        }
    }
}