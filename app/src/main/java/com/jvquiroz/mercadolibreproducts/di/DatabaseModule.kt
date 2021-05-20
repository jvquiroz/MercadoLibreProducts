package com.jvquiroz.mercadolibreproducts.di

import androidx.room.Room
import com.jvquiroz.mercadolibreproducts.App
import com.jvquiroz.mercadolibreproducts.data.cache.DbConstants
import com.jvquiroz.mercadolibreproducts.data.cache.ProductDAO
import com.jvquiroz.mercadolibreproducts.data.cache.ProductDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideProductDatabase(app: App): ProductDatabase {
        return Room.databaseBuilder(
            app,
            ProductDatabase::class.java,
            DbConstants.DB
        ).build()
    }

    @Provides
    fun provideSearchResultsDAO(appDatabase: ProductDatabase): ProductDAO {
        return appDatabase.productDao()
    }
}