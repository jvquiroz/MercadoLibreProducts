package com.jvquiroz.mercadolibreproducts.data.cache

import android.text.TextUtils
import com.google.gson.Gson
import com.jvquiroz.mercadolibreproducts.data.domain.model.Attribute
import com.jvquiroz.mercadolibreproducts.data.domain.model.Product
import com.jvquiroz.mercadolibreproducts.data.domain.util.ModelMapper
import java.text.SimpleDateFormat

/**
 * Transform @see ProductCache to @see Product and the other way around.
 */
class ProductCacheMapper : ModelMapper<ProductCache, Product> {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    override fun mapToModel(model: ProductCache): Product {
        return Product(
            id = model.id,
            thumbnail = model.thumbnail,
            price = model.price,
            permalink = model.permalink,
            currency = model.currency,
            title = model.title,
            pictures = model.pictures.split(","),
            lastUpdated = if (TextUtils.isEmpty(model.lastUpdated)) {
                null
            } else {
                dateFormat.parse(model.lastUpdated)
            },
            attributes = if (TextUtils.isEmpty(model.attributes)) {
                null
            } else {
                Gson().fromJson(model.attributes, Array<Attribute>::class.java).asList()
            }
        )
    }

    override fun mapFromModel(domainModel: Product): ProductCache {
        return ProductCache(
            id = domainModel.id,
            title = domainModel.title,
            currency = domainModel.currency,
            permalink = domainModel.permalink,
            price = domainModel.price,
            thumbnail = domainModel.thumbnail,
            lastUpdated = domainModel.lastUpdated?.let { dateFormat.format(it) },
            pictures = domainModel.pictures?.joinToString(","),
            attributes = Gson().toJson(domainModel.attributes)
        )
    }

    fun toList(initial: List<ProductCache>): List<Product> {
        return initial.map { mapToModel(it) }
    }

    fun fromList(initial: List<Product>): List<ProductCache> {
        return initial.map { mapFromModel(it) }
    }
}