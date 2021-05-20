package com.jvquiroz.mercadolibreproducts.data.network.model

/**
 * Data transfer object to hold the result of a search
 */
data class ProductListDTO(
    val query: String,
    val results: List<ProductDTO>
)