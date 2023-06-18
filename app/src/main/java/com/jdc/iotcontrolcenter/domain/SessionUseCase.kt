package com.jdc.iotcontrolcenter.domain

interface SessionUseCase {

    fun getToken(): String

    fun setToken(token: String)

}