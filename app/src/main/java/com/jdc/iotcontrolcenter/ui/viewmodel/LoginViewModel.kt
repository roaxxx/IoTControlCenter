package com.jdc.iotcontrolcenter.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdc.iotcontrolcenter.data.model.RequestLogin
import com.jdc.iotcontrolcenter.domain.Login
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel  @Inject constructor( private val loginManager: Login) : ViewModel() {
    val responseLoginModel = MutableLiveData<String>()

    fun authenticateOnline(requestLogin: RequestLogin){
        viewModelScope.launch {

            val authenticationResponse = loginManager.invoke(requestLogin)
            responseLoginModel.postValue(authenticationResponse)
        }
    }
}