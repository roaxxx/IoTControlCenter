package com.jdc.iotcontrolcenter.domain

import com.jdc.iotcontrolcenter.data.model.RequestLogin

interface LoginUseCase  {

    suspend operator fun  invoke(requestLogin: RequestLogin): String

}