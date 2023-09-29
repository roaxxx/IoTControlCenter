package com.jdc.iotcontrolcenter.domain

import com.jdc.iotcontrolcenter.data.model.RequestLogin
import com.jdc.iotcontrolcenter.data.Result

interface LoginUseCase  {

    suspend operator fun  invoke(requestLogin: RequestLogin): Result<String>

    suspend fun getCurrentUser(): Result<String>

}