package com.jvquiroz.mercadolibreproducts.di

import android.content.Context
import com.jvquiroz.mercadolibreproducts.App
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers.IO
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context) : App {
        return app as App
    }

    @Provides
    @Singleton
    fun provideIOCoroutineDispatcher(): CoroutineDispatcher {
        return IO
    }
}