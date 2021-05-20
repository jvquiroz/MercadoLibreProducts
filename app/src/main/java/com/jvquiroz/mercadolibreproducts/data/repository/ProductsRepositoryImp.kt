package com.jvquiroz.mercadolibreproducts.data.repository

import com.jvquiroz.mercadolibreproducts.data.cache.ProductDAO
import com.jvquiroz.mercadolibreproducts.data.cache.ProductCacheMapper
import com.jvquiroz.mercadolibreproducts.data.domain.model.Product
import com.jvquiroz.mercadolibreproducts.data.network.MercadoLibreAPI
import com.jvquiroz.mercadolibreproducts.data.network.model.ProductDTOMapper
import com.jvquiroz.mercadolibreproducts.data.network.model.Resource

/**
 * Implements @see MercadoLibreAPI
 */
class ProductsRepositoryImp(
    private val mercadoLibreAPI: MercadoLibreAPI,
    private val productDAO: ProductDAO,
    private val dtoMapper: ProductDTOMapper,
    private val cacheMapper: ProductCacheMapper
) : ProductsRepository {

    /**
     * Will fetch results according to @param query, clear the database and then store the results
     * as the new cache data.
     */
    override suspend fun search(query: String): Resource<List<Product?>> {
        return try {
            val dtoList = mercadoLibreAPI.search(query).results
            val domainList = dtoMapper.toDomainList(dtoList)
            val cacheList = cacheMapper.fromList(domainList)
            productDAO.deleteAll()
            productDAO.save(*cacheList.toTypedArray())
            Resource.Success(domainList)
        } catch (throwable: Throwable) {
            Resource.Error(throwable.message ?: "Error fetching result list from service")
        }
    }

    /**
     * Will get a Product details from the database cache, in case the data is not complete, the rest
     * of the information will be fetched from the service.
     */
    override suspend fun get(productId: String): Resource<Product> {
        return try {
            var productCache = productDAO.loadResult(productId)
            if (productCache.lastUpdated == null) {
                val productDTO = mercadoLibreAPI.getDetails(productId)
                val product = dtoMapper.mapToModel(productDTO)
                productCache = cacheMapper.mapFromModel(product)
                productDAO.update(productCache)
                Resource.Success(product)
            } else {
                Resource.Success(cacheMapper.mapToModel(productCache))
            }
        } catch (throwable: Throwable) {
            Resource.Error(throwable.message ?: "Error fetching product from database")
        }
    }
}