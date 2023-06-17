package com.jdc.iotcontrolcenter.domain

import android.util.Log
import com.jdc.iotcontrolcenter.IoTControlCenterApplication
import com.jdc.iotcontrolcenter.data.ApiRespository
import com.jdc.iotcontrolcenter.data.model.RequestLogin
import java.io.IOException
import javax.inject.Inject

class Login @Inject constructor(private val apiRespository :ApiRespository) {
    companion object {
        val USER_CREDENTIAL_ERROR = "INVALID_CREDENTIAL_ERR"
        val SERVER_CONNECTION_ERROR = "SERVER_CONN_ERR"
    }

    suspend operator fun  invoke(requestLogin: RequestLogin): String {
        return try{
            val apiResponse = apiRespository.loginInApi(requestLogin)
            if(apiResponse != null){
                apiResponse!!
            }else{
                USER_CREDENTIAL_ERROR
            }
        }catch (e : IOException){
            Log.i("http","No connected to server")
            SERVER_CONNECTION_ERROR
        }
    }

}