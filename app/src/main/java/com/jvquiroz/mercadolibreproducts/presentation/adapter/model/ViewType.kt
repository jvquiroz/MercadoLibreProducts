package com.jvquiroz.mercadolibreproducts.presentation.adapter.model

/**
 * Each item in the RecyclerView must implement this interface.
 */
interface ViewType {
    fun getViewType(): Int
}