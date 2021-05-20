package com.jvquiroz.mercadolibreproducts.data.repository

import com.jvquiroz.mercadolibreproducts.data.domain.model.Product
import com.jvquiroz.mercadolibreproducts.data.network.model.Resource

/**
 * Data access functions
 */
interface ProductsRepository {
    suspend fun search(query: String) : Resource<List<Product?>>
    suspend fun get(query: String) : Resource<Product>
}