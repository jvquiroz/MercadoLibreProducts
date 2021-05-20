package com.jvquiroz.mercadolibreproducts.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jvquiroz.mercadolibreproducts.data.domain.model.Product
import com.jvquiroz.mercadolibreproducts.data.network.model.Resource
import com.jvquiroz.mercadolibreproducts.data.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productsRepository: ProductsRepository,
    private val coroutineDispatcher: CoroutineDispatcher
): ViewModel() {

    var query: MutableLiveData<String> = MutableLiveData("")

    private val _productList =  MutableLiveData<Resource<List<Product?>>>()
    val productList: LiveData<Resource<List<Product?>>>
        get() = _productList

    private val _product =  MutableLiveData<Resource<Product>>()
    val product: LiveData<Resource<Product>>
        get() = _product

    fun getSearchResults(searchTerms: String) {
        CoroutineScope(coroutineDispatcher).launch {
            _productList.postValue(Resource.Loading())
            _productList.postValue(productsRepository.search(searchTerms))
        }
    }

    fun getDetails(id: String) {
        CoroutineScope(coroutineDispatcher).launch {
            _product.postValue(Resource.Loading())
            _product.postValue(productsRepository.get(id))
        }

    }
}