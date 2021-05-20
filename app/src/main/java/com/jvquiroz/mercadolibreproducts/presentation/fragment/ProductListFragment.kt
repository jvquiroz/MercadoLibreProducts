package com.jvquiroz.mercadolibreproducts.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.jvquiroz.mercadolibreproducts.R
import com.jvquiroz.mercadolibreproducts.data.domain.model.Product
import com.jvquiroz.mercadolibreproducts.data.network.model.Resource
import com.jvquiroz.mercadolibreproducts.databinding.FragmentProductListBinding
import com.jvquiroz.mercadolibreproducts.presentation.ProductViewModel
import com.jvquiroz.mercadolibreproducts.presentation.adapter.ProductListAdapter
import com.jvquiroz.mercadolibreproducts.presentation.hideKeyboard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Fragment for searching and displaying results in a list.
 */
class ProductListFragment : BaseFragment() {

    private lateinit var navController: NavController
    private lateinit var productListAdapter: ProductListAdapter

    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProductViewModel by hiltNavGraphViewModels(
        R.id.nav_graph
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        productListAdapter = ProductListAdapter {
            val action = ProductListFragmentDirections.actionMainFragmentToDetailFragment2(it.id)
            navController.navigate(action)
        }
        //Toolbar setup
        binding.toolbar.inflateMenu(R.menu.main_fragment)
        binding.toolbar.setTitle(R.string.app_name)
        val searchItem = binding.toolbar.menu?.findItem(R.id.action_search)
        val searchView:SearchView = searchItem?.actionView as SearchView
        searchView.queryHint = requireContext().getString(R.string.search_hint)
        searchView.setOnQueryTextListener(getOnQueryTextListener())

        //recyclerview
        binding.productsList.layoutManager = LinearLayoutManager(context)
        binding.productsList.adapter = productListAdapter
        binding.productsList.addItemDecoration(
            DividerItemDecoration(context,
                DividerItemDecoration.VERTICAL)
        )

        viewModel.productList.observe(viewLifecycleOwner, Observer {
            handleFetchResponse(it)
        })
    }

    private fun fetchResults(query: String) {
        viewModel.getSearchResults(query)
    }

    private fun handleFetchResponse(productList: Resource<List<Product?>>) {
        when (productList) {
            is Resource.Loading -> {
                binding.noResultsContainer.visibility = View.GONE
                binding.loadingView.root.visibility = View.VISIBLE
            }
            is Resource.Success -> {
                binding.noResultsContainer.visibility = View.GONE
                productListAdapter.productList = productList.data
                binding.loadingView.root.visibility = View.GONE
            }
            else -> {
                binding.noResultsContainer.visibility = View.VISIBLE
                binding.loadingView.root.visibility = View.GONE
                displayErrorMessage(
                    view = binding.loadingView.root,
                    message = requireContext().getString(R.string.error_fetch_failed),
                    duration = Snackbar.LENGTH_LONG)
            }
        }
    }

    private fun getOnQueryTextListener(): SearchView.OnQueryTextListener {
        return object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                hideKeyboard()
                query?.let { fetchResults(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.query.value = newText
                return true
            }
        }
    }

    override fun clearBindings() {
        _binding = null
    }
}
