package com.jvquiroz.mercadolibreproducts.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jvquiroz.mercadolibreproducts.R
import com.jvquiroz.mercadolibreproducts.data.domain.model.Product
import com.jvquiroz.mercadolibreproducts.databinding.ItemViewProductBinding
import com.jvquiroz.mercadolibreproducts.presentation.util.ImageUtils

/**
 * Adapter to display search results.
 */
class ProductListAdapter(
    private val onItemClicked: (Product) -> Unit
) : RecyclerView.Adapter<ProductListAdapter.ResultViewHolder>() {

    var productList: List<Product?>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ResultViewHolder(
        view: ItemViewProductBinding,
        onItemClicked: (Int) -> Unit
    ) : RecyclerView.ViewHolder(view.root) {

        private val binding = ItemViewProductBinding.bind(view.root)
        val name = binding.productNameLabel
        val image = binding.productImage
        val shimmerLayout = binding.productImageContainer

        init {
            itemView.setOnClickListener {
                onItemClicked(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        return ResultViewHolder(
            ItemViewProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        ) { productList?.get(it)?.let(onItemClicked) }
    }

    override fun getItemCount(): Int {
        return productList?.size ?: 0
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        holder.name.text = productList!![position]?.title
        ImageUtils.load(
            imageView = holder.image,
            placeholder = R.drawable.ic_image,
            url = productList!![position]?.thumbnail!!,
            shimmerLayout = holder.shimmerLayout
        )
    }
}