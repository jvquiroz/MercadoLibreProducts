package com.jvquiroz.mercadolibreproducts.presentation.adapter.model

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Generic adapter to hold a list of delegate adapters.
 */
interface ViewTypeDelegateAdapter {

    fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder

    fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType)
}