package com.jvquiroz.mercadolibreproducts.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.jvquiroz.mercadolibreproducts.R
import com.jvquiroz.mercadolibreproducts.data.domain.model.Product
import com.jvquiroz.mercadolibreproducts.data.network.model.Resource
import com.jvquiroz.mercadolibreproducts.databinding.FragmentProductDetailsBinding
import com.jvquiroz.mercadolibreproducts.presentation.ProductViewModel
import com.jvquiroz.mercadolibreproducts.presentation.adapter.ProductDetailsAdapter
import com.jvquiroz.mercadolibreproducts.presentation.adapter.model.AttributesItemView
import com.jvquiroz.mercadolibreproducts.presentation.adapter.model.DetailsItemView
import com.jvquiroz.mercadolibreproducts.presentation.adapter.model.PictureItemView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductDetailsFragment : BaseFragment() {

    private lateinit var productDetailsAdapter: ProductDetailsAdapter

    //View binding
    private var _binding: FragmentProductDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: ProductDetailsFragmentArgs by navArgs()
    private val viewModel: ProductViewModel by hiltNavGraphViewModels(
        R.id.nav_graph
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun clearBindings() {
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productDetailsAdapter = ProductDetailsAdapter()
        viewModel.product.observe(viewLifecycleOwner, Observer {
            handleFetchResponse(it)
        })

        binding.toolbar.setTitle(R.string.product_detail_title)

        //recyclerview
        binding.detailsList.layoutManager = LinearLayoutManager(context)
        binding.detailsList.adapter = productDetailsAdapter
        fetchDetails()
    }

    private fun handleFetchResponse(resource: Resource<Product>?) {
        resource.let { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.loadingView.root.visibility = View.VISIBLE
                }

                is Resource.Success -> {
                    productDetailsAdapter.items.clear()
                    resource.data?.let { product ->
                        productDetailsAdapter.titleInfo =
                            DetailsItemView(title = product.title,
                                price = product.price,
                                permalink = product.permalink,
                                currency = product.currency)

                        productDetailsAdapter.pictures = PictureItemView(product.pictures)

                        productDetailsAdapter.attributes =
                            product.attributes?.map { AttributesItemView(it.name, it.value) }
                    }
                    binding.loadingView.root.visibility = View.GONE
                }
                else -> {
                    binding.loadingView.root.visibility = View.GONE
                    displayErrorMessage(
                        view = binding.loadingView.root,
                        message = requireContext().getString(R.string.error_fetch_failed),
                        duration = Snackbar.LENGTH_LONG)
                }
            }
        }
    }

    private fun fetchDetails() {
        viewModel.getDetails(args.productId)
    }
}