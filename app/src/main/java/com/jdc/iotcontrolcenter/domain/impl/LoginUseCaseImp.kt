package com.jdc.iotcontrolcenter.domain.impl

import android.util.Log
import com.jdc.iotcontrolcenter.data.ApiRespository
import com.jdc.iotcontrolcenter.data.model.RequestLogin
import com.jdc.iotcontrolcenter.domain.LoginUseCase
import java.io.IOException
import javax.inject.Inject

class LoginUseCaseImp @Inject constructor(
    private val apiRespository: ApiRespository,
    private val sessionUseCaseImpl: SessionUseCaseImpl
) : LoginUseCase {
    companion object {
        val USER_CREDENTIAL_ERROR = "INVALID_CREDENTIAL_ERR"
        val SERVER_CONNECTION_ERROR = "SERVER_CONN_ERR"
    }

    /**
     * Performs user login.
     * @param requestLogin the user login data.
     * @return the authentication token if the login is successful, otherwise
     * returns an error message.
     */
    override suspend operator fun invoke(requestLogin: RequestLogin): String {
        return try {
            val apiResponse = apiRespository.loginInApi(requestLogin)
            if (apiResponse != null) {
                sessionUseCaseImpl.setToken(apiResponse)
                apiResponse!!
            } else {
                USER_CREDENTIAL_ERROR
            }
        } catch (e: IOException) {
            Log.i("http", "No connected to server")
            SERVER_CONNECTION_ERROR
        }
    }
}