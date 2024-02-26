package com.softeer.togeduck.di

import com.softeer.togeduck.data.local.datasource.UserDataStore
import com.softeer.togeduck.data.remote.datasource.UserRemoteDataSource
import com.softeer.togeduck.data.remote.datasource.ReserveStatusRemoteDataSource
import com.softeer.togeduck.data.remote.datasource.SeatRemoteDataSource
import com.softeer.togeduck.data.repository.ReserveStatusRepository
import com.softeer.togeduck.data.repository.SeatRepository
import com.softeer.togeduck.data.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Singleton
    @Provides
    fun provideUserRepository(
        userRemoteDataSource: UserRemoteDataSource, userDataSource: UserDataStore
    ): UserRepository {
        return UserRepository(userRemoteDataSource, userDataSource)
    }

    @Singleton
    @Provides
    fun provideReserveStatusRepository(
        reserveStatusRemoteDataSource: ReserveStatusRemoteDataSource,
    ): ReserveStatusRepository {
        return ReserveStatusRepository(reserveStatusRemoteDataSource)
    }

    @Singleton
    @Provides
    fun provideSeatRepository(
        seatRemoteDataSource: SeatRemoteDataSource,
    ): SeatRepository {
        return SeatRepository(seatRemoteDataSource)
    }
}