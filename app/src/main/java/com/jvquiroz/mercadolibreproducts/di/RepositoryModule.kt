package com.jvquiroz.mercadolibreproducts.di

import com.jvquiroz.mercadolibreproducts.data.cache.ProductDAO
import com.jvquiroz.mercadolibreproducts.data.cache.ProductCacheMapper
import com.jvquiroz.mercadolibreproducts.data.network.MercadoLibreAPI
import com.jvquiroz.mercadolibreproducts.data.network.model.ProductDTOMapper
import com.jvquiroz.mercadolibreproducts.data.repository.ProductsRepositoryImp
import com.jvquiroz.mercadolibreproducts.data.repository.ProductsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRecipeRepository(
        api: MercadoLibreAPI,
        dtoMapper: ProductDTOMapper,
        productDAO: ProductDAO,
        cacheMapper: ProductCacheMapper
    ): ProductsRepository {
        return ProductsRepositoryImp(
            mercadoLibreAPI = api,
            dtoMapper = dtoMapper,
            productDAO = productDAO,
            cacheMapper = cacheMapper
        )
    }

    @Singleton
    @Provides
    fun provideDomainMapper(): ProductDTOMapper {
        return ProductDTOMapper()
    }

    @Singleton
    @Provides
    fun provideCacheMapper(): ProductCacheMapper {
        return ProductCacheMapper()
    }
}

