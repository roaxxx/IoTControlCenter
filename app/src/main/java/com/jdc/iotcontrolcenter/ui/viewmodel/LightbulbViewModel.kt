package com.jdc.iotcontrolcenter.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdc.iotcontrolcenter.data.model.Lightbulb
import com.jdc.iotcontrolcenter.domain.LightbulbUseCase
import com.jdc.iotcontrolcenter.data.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LightbulbViewModel @Inject constructor(
    private val lightbulbUseCase: LightbulbUseCase
): ViewModel()  {
    val lightbulListViewModel = MutableLiveData<Result<List<Lightbulb>>>()
    val isLightbulbUpdated = MutableLiveData<Result<Lightbulb>>()

    fun getLightbulbsList(){
        viewModelScope.launch {
            lightbulListViewModel.postValue(lightbulbUseCase.listLightbulbs())
        }
    }

    fun updateLightbulbState(idLightbulb: Int, lightbulbValue: String){
        viewModelScope.launch {
            isLightbulbUpdated.postValue(
                lightbulbUseCase.updateLightbulb(idLightbulb, lightbulbValue)
            )
        }
    }
}