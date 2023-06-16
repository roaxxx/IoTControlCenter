package com.jdc.iotcontrolcenter.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdc.iotcontrolcenter.data.model.RequestLogin
import com.jdc.iotcontrolcenter.domain.Login
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val loginManager = Login()
    val responseLoginModel = MutableLiveData<String>()
    val isLoading = MutableLiveData<Boolean>()

    fun authenticateOnline(requestLogin: RequestLogin){
        viewModelScope.launch {

            val authenticationResponse = loginManager.invoke(requestLogin)
            responseLoginModel.postValue(authenticationResponse)
        }
    }
}