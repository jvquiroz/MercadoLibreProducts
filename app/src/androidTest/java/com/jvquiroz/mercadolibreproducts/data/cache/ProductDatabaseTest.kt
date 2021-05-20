package com.jvquiroz.mercadolibreproducts.data.cache

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.jvm.Throws

@RunWith(AndroidJUnit4::class)
class ProductDatabaseTest : TestCase() {
    private lateinit var productCache: ProductCache
    private lateinit var productDao: ProductDAO
    private lateinit var db: ProductDatabase
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, ProductDatabase::class.java
        ).build()
        productDao = db.productDao()

        productCache = ProductCache(
            id = "id",
            title = "title",
            price = 10.0,
            currency = "COP",
            thumbnail = "thumbnail",
            pictures = "image1,image2",
            lastUpdated = dateFormat.format(Date()),
            attributes = "[]",
            permalink = "permalink"
        )
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun writeAndRead() = runBlocking {
        productDao.save(productCache)
        val product = productDao.loadResult(productCache.id)
        assertEquals(productCache, product)
    }

    @Test
    fun update() = runBlocking {
        productDao.save(productCache)
        var product = productDao.loadResult(productCache.id)
        assertEquals(productCache.lastUpdated, product.lastUpdated)

        val newDate = Date()
        product = ProductCache(
            id = product.id,
            permalink = product.permalink,
            attributes = product.attributes,
            lastUpdated = dateFormat.format(newDate),
            pictures = product.pictures,
            thumbnail = product.thumbnail,
            currency = product.currency,
            price = product.price,
            title = product.title
        )
        productDao.update(product)
        product = productDao.loadResult(productCache.id)
        assertEquals(dateFormat.format(newDate), product.lastUpdated)
    }

    @Test
    fun delete() {
        productDao.save(productCache)
        productDao.deleteAll()
        var product = productDao.loadResult(productCache.id)
        assertNull(product)
    }
}