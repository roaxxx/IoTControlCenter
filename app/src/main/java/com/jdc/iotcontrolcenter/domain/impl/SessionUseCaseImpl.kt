package com.jdc.iotcontrolcenter.domain.impl

import com.jdc.iotcontrolcenter.IoTControlCenterApplication
import com.jdc.iotcontrolcenter.data.preferences.PreferencesProvider
import com.jdc.iotcontrolcenter.domain.SessionUseCase
import javax.inject.Inject

class SessionUseCaseImpl @Inject constructor() : SessionUseCase {

    /**
     * Retrieves the authentication token.
     * @return The authentication token.
     */
    override fun getToken(): String {
        return PreferencesProvider.string(IoTControlCenterApplication.CONTEXT, "JWT_TOKEN")!!
    }

    /**
     * Sets the authentication token.
     * @param token The authentication token to be set.
     */
    override fun setToken(token: String) {
        PreferencesProvider.set(IoTControlCenterApplication.CONTEXT, "JWT_TOKEN", "Bearer $token")
    }
}