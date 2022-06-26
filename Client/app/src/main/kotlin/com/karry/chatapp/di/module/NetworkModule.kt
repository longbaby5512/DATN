package com.karry.chatapp.di.module

import com.karry.common.BuildConfig
import com.karry.common.Constants
import com.karry.domain.repositories.AccountRepository
import com.karry.remote.api.AuthApi
import com.karry.remote.repositories.AccountRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit


@Module
@InstallIn(ViewModelComponent::class)
object NetworkModule {
    @Provides
    @ViewModelScoped
    internal fun provideHttpLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor { message ->
            Timber.tag("HttpLogging").i(message)
        }.apply {
            level = if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY
            else
                HttpLoggingInterceptor.Level.BASIC
        }
    }

    /**
     * Provides [OkHttpClient] instance
     */
    @Provides
    @ViewModelScoped
    internal fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .retryOnConnectionFailure(true)
            .build()
    }

    /**
     * Provides [AuthApi] instance
     */
    @Provides
    @ViewModelScoped
    internal fun provideAuthApi(okHttpClient: OkHttpClient): AuthApi {
        return Retrofit.Builder()
            .baseUrl(Constants.AUTH_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApi::class.java)
    }

    @Provides
    @ViewModelScoped
    fun provideAccountRepository(api: AuthApi): AccountRepository {
        return AccountRepositoryImpl(api)
    }
}