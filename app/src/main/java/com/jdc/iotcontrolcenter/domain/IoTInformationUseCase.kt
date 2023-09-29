package com.jdc.iotcontrolcenter.domain

import com.jdc.iotcontrolcenter.data.model.IoTInformationDTO
import com.jdc.iotcontrolcenter.data.Result

interface IoTInformationUseCase {
    suspend fun getIoTInfo(): Result<IoTInformationDTO>
}