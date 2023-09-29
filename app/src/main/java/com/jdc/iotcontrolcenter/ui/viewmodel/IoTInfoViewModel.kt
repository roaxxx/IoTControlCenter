package com.jdc.iotcontrolcenter.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdc.iotcontrolcenter.data.model.IoTInformationDTO
import com.jdc.iotcontrolcenter.domain.IoTInformationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.jdc.iotcontrolcenter.data.Result

@HiltViewModel
class IoTInfoViewModel @Inject constructor(
    private val ioTInformationUseCase: IoTInformationUseCase
): ViewModel()  {
    val ioTInfoObservable = MutableLiveData<Result<IoTInformationDTO>>()
    fun getIoTInformation(){
        viewModelScope.launch {
            try{
                ioTInfoObservable.postValue(ioTInformationUseCase.getIoTInfo())
            }catch(e :Exception ){
                Log.i("http","IotInfo $e")
            }
        }
    }
}