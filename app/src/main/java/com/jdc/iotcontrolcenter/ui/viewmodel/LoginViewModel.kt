package com.jdc.iotcontrolcenter.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdc.iotcontrolcenter.data.model.RequestLogin
import com.jdc.iotcontrolcenter.domain.impl.LoginUseCaseImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel  @Inject constructor( private val loginUseCaseImp: LoginUseCaseImp) : ViewModel() {
    val responseLoginModel = MutableLiveData<String>()

    fun authenticateOnline(requestLogin: RequestLogin){
        viewModelScope.launch {
            val authenticationResponse = loginUseCaseImp.invoke(requestLogin)
            responseLoginModel.postValue(authenticationResponse)
        }
    }
}