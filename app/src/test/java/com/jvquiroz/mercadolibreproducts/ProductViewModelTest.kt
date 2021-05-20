package com.jvquiroz.mercadolibreproducts

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jvquiroz.mercadolibreproducts.data.domain.model.Attribute
import com.jvquiroz.mercadolibreproducts.data.domain.model.Product
import com.jvquiroz.mercadolibreproducts.data.network.model.Resource
import com.jvquiroz.mercadolibreproducts.data.repository.ProductsRepository
import com.jvquiroz.mercadolibreproducts.presentation.ProductViewModel
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.core.IsInstanceOf
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.Date

class ProductViewModelTest {

    @get:Rule
    val instantExecutionRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var productsRepository: ProductsRepository
    private lateinit var viewModel: ProductViewModel
    private lateinit var product: Product

    private val testDispatcher = TestCoroutineDispatcher()
    private val searchQuery = "product"

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = ProductViewModel(productsRepository, testDispatcher)
        product = Product(
            id = "id",
            title = "title",
            price = 10.0,
            currency = "COP",
            thumbnail = "thumbnail",
            pictures = listOf("img1","img2"),
            lastUpdated = Date(),
            attributes = listOf(Attribute("name", "value")),
            permalink = "permalink"
        )

    }

    @Test
    fun testGetProductList() = runBlockingTest {
        Mockito.`when`(productsRepository.search(searchQuery)).thenReturn(
            Resource.Success(listOf(product))
        )
        viewModel.getSearchResults(searchQuery)
        Mockito.verify(productsRepository).search(searchQuery)
        Assert.assertEquals(product, viewModel.productList.value?.data?.get(0))
    }

    @Test
    fun testGetProductListFailed() = runBlockingTest {
        Mockito.`when`(productsRepository.search(searchQuery)).thenReturn(
            Resource.Error("")
        )
        viewModel.getSearchResults(searchQuery)
        Mockito.verify(productsRepository).search(searchQuery)
        Assert.assertThat(viewModel.productList.value, IsInstanceOf.instanceOf(Resource.Error::class.java))
    }

    @Test
    fun testGetProductDetails() = runBlockingTest {
        Mockito.`when`(productsRepository.get(searchQuery)).thenReturn(
            Resource.Success(product)
        )
        viewModel.getDetails(searchQuery)
        Mockito.verify(productsRepository).get(searchQuery)
        Assert.assertEquals(product, viewModel.product.value?.data)
    }

    @Test
    fun testGetProductDetailsFailed() = runBlockingTest {
        Mockito.`when`(productsRepository.get(searchQuery)).thenReturn(
            Resource.Error("")
        )
        viewModel.getDetails(searchQuery)
        Mockito.verify(productsRepository).get(searchQuery)
        Assert.assertThat(viewModel.product.value, IsInstanceOf.instanceOf(Resource.Error::class.java))
    }

}