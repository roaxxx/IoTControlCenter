package com.jdc.iotcontrolcenter.domain

import com.jdc.iotcontrolcenter.data.ApiRespository
import com.jdc.iotcontrolcenter.data.model.RequestLogin
import java.io.IOException

class Login {

    private val apiRespository = ApiRespository()

    suspend operator fun  invoke(requestLogin: RequestLogin): String? {
        return try{
            apiRespository.loginInApi(requestLogin)
        }catch (e : IOException){
            null
        }
    }

}