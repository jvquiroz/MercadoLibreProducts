package com.jvquiroz.mercadolibreproducts.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jvquiroz.mercadolibreproducts.databinding.ItemViewProductDetailAttributesBinding
import com.jvquiroz.mercadolibreproducts.presentation.adapter.model.AttributesItemView
import com.jvquiroz.mercadolibreproducts.presentation.adapter.model.ViewType
import com.jvquiroz.mercadolibreproducts.presentation.adapter.model.ViewTypeDelegateAdapter

/**
 * Delegate adapter to display an attribute label and value
 */
class ProductAttributesDelegateAdapter : ViewTypeDelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return AttributeViewHolder(
            ItemViewProductDetailAttributesBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as AttributeViewHolder
        holder.bind(item as AttributesItemView)
    }

    inner class AttributeViewHolder(view: ItemViewProductDetailAttributesBinding) :
        RecyclerView.ViewHolder(view.root) {
        private val binding = ItemViewProductDetailAttributesBinding.bind(view.root)
        private val label = binding.textView
        private val value = binding.textView2

        fun bind(item: AttributesItemView) {
            label.text = "${item.name}:"
            value.text = item.value
        }
    }
}