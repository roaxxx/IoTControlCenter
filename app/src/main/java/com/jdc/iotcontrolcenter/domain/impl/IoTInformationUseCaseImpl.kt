package com.jdc.iotcontrolcenter.domain.impl

import com.jdc.iotcontrolcenter.data.ApiRepository
import com.jdc.iotcontrolcenter.data.model.IoTInformationDTO
import com.jdc.iotcontrolcenter.domain.IoTInformationUseCase
import com.jdc.iotcontrolcenter.domain.SessionUseCase
import javax.inject.Inject
import com.jdc.iotcontrolcenter.data.Result

class IoTInformationUseCaseImpl @Inject constructor(
    private val apiRepository : ApiRepository,
    private val sessionUseCase: SessionUseCase
): IoTInformationUseCase {
    override suspend fun getIoTInfo(): Result<IoTInformationDTO> {
        return apiRepository.getIotInformation(sessionUseCase.getToken())
    }
}