package com.softeer.togeduck.di

import com.softeer.togeduck.data.remote.service.ArticleDetailService
import com.softeer.togeduck.data.remote.service.HomeService
import com.softeer.togeduck.data.remote.service.RegionListService
import com.softeer.togeduck.data.remote.service.ReserveStatusService
import com.softeer.togeduck.data.remote.service.SeatService
import com.softeer.togeduck.data.remote.service.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {
    @Provides
    @Singleton
    fun provideLoginService(retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Provides
    @Singleton
    fun provideHomeService(retrofit: Retrofit): HomeService {
        return retrofit.create(HomeService::class.java)
    }

    @Provides
    @Singleton
    fun provideReserveStatusService(retrofit: Retrofit): ReserveStatusService {
        return retrofit.create(ReserveStatusService::class.java)
    }

    @Provides
    @Singleton
    fun provideSeatService(retrofit: Retrofit): SeatService {
        return retrofit.create(SeatService::class.java)
    }


    @Provides
    @Singleton
    fun provideArticleDetailService(retrofit: Retrofit): ArticleDetailService {
        return retrofit.create(ArticleDetailService::class.java)
    }

    @Provides
    @Singleton
    fun provideRegionListService(retrofit: Retrofit): RegionListService {
        return retrofit.create(RegionListService::class.java)
    }


}