package com.jdc.iotcontrolcenter.domain.impl


import com.jdc.iotcontrolcenter.data.ApiRepository
import com.jdc.iotcontrolcenter.data.model.RequestLogin
import com.jdc.iotcontrolcenter.domain.LoginUseCase
import com.jdc.iotcontrolcenter.domain.SessionUseCase
import javax.inject.Inject
import com.jdc.iotcontrolcenter.data.Result

class LoginUseCaseImp @Inject constructor(
    private val sessionUseCase: SessionUseCase,
    private val ioTService: ApiRepository
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
    override suspend operator fun invoke(requestLogin: RequestLogin): Result<String> {
            val apiResponse: Result<String> = ioTService.loginInApi(requestLogin)
            if( apiResponse is Result.Success){
                sessionUseCase.setToken(apiResponse.data)
                return Result.Success("")
            }
        return apiResponse
    }

    override suspend fun getCurrentUser(): Result<String> {
        return ioTService.getCurrentUser(sessionUseCase.getToken())
    }
}