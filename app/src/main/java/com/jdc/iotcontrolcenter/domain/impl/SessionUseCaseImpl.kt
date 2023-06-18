package com.jdc.iotcontrolcenter.domain.impl

import com.jdc.iotcontrolcenter.IoTControlCenterApplication
import com.jdc.iotcontrolcenter.data.preferences.PreferencesProvider
import com.jdc.iotcontrolcenter.domain.SessionUseCase
import javax.inject.Inject

class SessionUseCaseImpl @Inject constructor() : SessionUseCase {

    override fun getToken(): String {
        return PreferencesProvider.string(IoTControlCenterApplication.CONTEXT, "JWT_TOKEN")!!
    }

    override fun setToken(token: String) {
        PreferencesProvider.set(IoTControlCenterApplication.CONTEXT, "JWT_TOKEN", "Bearer $token")
    }
}