package com.jvquiroz.mercadolibreproducts.presentation.adapter

import android.icu.text.NumberFormat
import android.icu.util.Currency
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jvquiroz.mercadolibreproducts.databinding.ItemViewProductDetailTitleBinding
import com.jvquiroz.mercadolibreproducts.presentation.adapter.model.DetailsItemView
import com.jvquiroz.mercadolibreproducts.presentation.adapter.model.ViewType
import com.jvquiroz.mercadolibreproducts.presentation.adapter.model.ViewTypeDelegateAdapter

/**
 * Delegate adapter to display a Product's name and price.
 */
class ProductTitleDelegateAdapter : ViewTypeDelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return AttributeViewHolder(
            ItemViewProductDetailTitleBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as AttributeViewHolder
        holder.bind(item as DetailsItemView)
    }

    inner class AttributeViewHolder(view: ItemViewProductDetailTitleBinding) :
        RecyclerView.ViewHolder(view.root) {
        private val binding = ItemViewProductDetailTitleBinding.bind(view.root)
        private val name = binding.nameLabel
        private val price = binding.priceLabel

        fun bind(item: DetailsItemView) {
            val numberFormat = NumberFormat.getInstance()
            numberFormat.currency = Currency.getInstance(item.currency)
            name.text = item.title
            price.text = "${numberFormat.currency.symbol} ${numberFormat.format(item.price)}"
        }
    }
}