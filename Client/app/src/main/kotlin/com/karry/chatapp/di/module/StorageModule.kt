package com.karry.chatapp.di.module

import android.content.Context
import com.karry.common.storage.SecurePreferencesStorage
import com.karry.common.storage.Storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StorageModule {

    @Provides
    @Singleton
    fun provideStorage(@ApplicationContext context: Context): Storage {
        return SecurePreferencesStorage(context)
    }
}