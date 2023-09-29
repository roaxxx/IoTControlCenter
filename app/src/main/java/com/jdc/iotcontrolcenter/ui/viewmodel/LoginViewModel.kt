package com.jdc.iotcontrolcenter.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdc.iotcontrolcenter.data.model.RequestLogin
import com.jdc.iotcontrolcenter.domain.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.jdc.iotcontrolcenter.data.Result

@HiltViewModel
class LoginViewModel  @Inject constructor( private val loginUseCase: LoginUseCase) : ViewModel() {
    val loginResultModel = MutableLiveData<Result<String>>()
    val usernameObservable = MutableLiveData<Result<String>>()

    fun authenticateOnline(requestLogin: RequestLogin){
        viewModelScope.launch {
            val authenticationResponse = loginUseCase.invoke(requestLogin)
            loginResultModel.postValue(authenticationResponse)
        }
    }

    fun getSessionUser(): Unit {
        viewModelScope.launch {
            val authenticatedUsername = loginUseCase.getCurrentUser()
            usernameObservable.postValue(authenticatedUsername)
        }
    }
}