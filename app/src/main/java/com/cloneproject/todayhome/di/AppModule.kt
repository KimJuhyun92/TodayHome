package com.cloneproject.todayhome.di

import com.cloneproject.todayhome.data.datasource.local.TodayHomeLocalSource
import com.cloneproject.todayhome.data.datasource.remote.TodayHomeRemoteSource
import com.cloneproject.todayhome.data.datasource.remote.api.ServiceApi
import com.cloneproject.todayhome.data.repository.TodayHomeRepository
import com.orhanobut.logger.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideBaseUrl() = ""

    @Singleton
    @Provides
    fun provideMoshi(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    @Singleton
    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else {
        OkHttpClient
            .Builder()
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi, BASE_URL: String): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ServiceApi = retrofit.create(ServiceApi::class.java)

    @Provides
    @Singleton
    fun provideTodayHomeRemoteDataSource(api: ServiceApi): TodayHomeRemoteSource {
        return TodayHomeRemoteSource(api)
    }

    @Provides
    @Singleton
    fun provideTodayHomeLocalDataSource(): TodayHomeLocalSource {
        return TodayHomeLocalSource()
    }

    @Provides
    @Singleton
    fun provideTodayHomeRepository(remoteDataSource: TodayHomeRemoteSource, localDataSource: TodayHomeLocalSource): TodayHomeRepository {
        return TodayHomeRepository(remoteDataSource, localDataSource)
    }
}