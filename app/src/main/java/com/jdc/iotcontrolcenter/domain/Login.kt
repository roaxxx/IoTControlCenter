package com.jdc.iotcontrolcenter.domain

import android.util.Log
import com.jdc.iotcontrolcenter.IoTControlCenterApplication
import com.jdc.iotcontrolcenter.data.ApiRespository
import com.jdc.iotcontrolcenter.data.model.RequestLogin
import java.io.IOException

class Login {

    private val apiRespository = ApiRespository()

    suspend operator fun  invoke(requestLogin: RequestLogin): String {
        return try{
            val apiResponse = apiRespository.loginInApi(requestLogin)
            if(apiResponse != null){
                apiResponse!!
            }else{
                IoTControlCenterApplication.USER_CREDENTIAL_ERROR
            }
        }catch (e : IOException){
            Log.i("http","No connected to server")
            IoTControlCenterApplication.SERVER_CONNECTION_ERROR
        }
    }

}