package com.jvquiroz.mercadolibreproducts.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * RoomDatabase
 */
@Database(entities = [ProductCache::class], version = 1)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDAO
}

object DbConstants {
    const val DB = "products"
    const val TABLE_PRODUCTS = "product"
}