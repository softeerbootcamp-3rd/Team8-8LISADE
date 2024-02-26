package com.softeer.togeduck.di

import com.softeer.togeduck.data.remote.datasource.ReserveStatusRemoteDataSource
import com.softeer.togeduck.data.remote.datasource.SeatRemoteDataSource
import com.softeer.togeduck.data.remote.datasource.UserRemoteDataSource
import com.softeer.togeduck.data.remote.service.ReserveStatusService
import com.softeer.togeduck.data.remote.service.SeatService
import com.softeer.togeduck.data.remote.service.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    @Singleton
    @Provides
    fun provideLoginRemoteDataSource(service: UserService): UserRemoteDataSource {
        return UserRemoteDataSource(service)
    }

    @Singleton
    @Provides
    fun provideReserveStatusRemoteDataSource(service: ReserveStatusService): ReserveStatusRemoteDataSource {
        return ReserveStatusRemoteDataSource(service)
    }

    @Singleton
    @Provides
    fun provideSeatRemoteDataSource(service: SeatService): SeatRemoteDataSource {
        return SeatRemoteDataSource(service)
    }
}