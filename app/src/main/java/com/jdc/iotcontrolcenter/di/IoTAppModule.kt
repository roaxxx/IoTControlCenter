package com.jdc.iotcontrolcenter.di

import com.jdc.iotcontrolcenter.data.ApiRepository
import com.jdc.iotcontrolcenter.data.services.network.IoTService
import com.jdc.iotcontrolcenter.domain.*
import com.jdc.iotcontrolcenter.domain.impl.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class IoTAppModule {
    @Binds
    abstract fun bindApiRepository(
        ioTService: IoTService
    ): ApiRepository

    @Binds
    abstract fun bindAlarmUseCase(
        AlarmUseCaseImpl: AlarmUseCaseImpl
    ): AlarmUseCase

    @Binds
    abstract fun bindDht11SensorUseCase(
        dht11SensorUseCaseImpl: Dht11SensorUseCaseImpl
    ): Dht11SensorUseCase

    @Binds
    abstract fun bindDoorUseCase(
        doorUseCaseImpl: DoorUseCaseImpl
    ): DoorUseCase

    @Binds
    abstract fun bindIoTInformationUseCase(
        ioTInformationUseCaseImpl: IoTInformationUseCaseImpl
    ): IoTInformationUseCase

    @Binds
    abstract fun bindLoginUseCase(
        loginUseCaseImp: LoginUseCaseImp
    ): LoginUseCase

    @Binds
    abstract fun bindNotificationUseCase(
        notificationUseCaseImpl: NotificationUseCaseImpl
    ): NotificationUseCase

    @Binds
    abstract fun bindLightbulbUseCase(
        lightbulbUseCaseImpl: LightbulbUseCaseImpl
    ): LightbulbUseCase

    @Binds
    abstract fun bindSessionUseCase(
        sessionUseCaseImpl: SessionUseCaseImpl
    ): SessionUseCase


}