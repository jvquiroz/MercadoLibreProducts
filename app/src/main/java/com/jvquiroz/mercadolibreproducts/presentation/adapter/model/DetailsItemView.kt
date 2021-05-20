package com.jvquiroz.mercadolibreproducts.presentation.adapter.model

/**
 * @see ViewType to hold an @see AdapterConstants.DETAILS
 */
data class DetailsItemView(
    val title: String,
    val price: Double,
    val currency: String,
    val permalink: String,
): ViewType {
    override fun getViewType(): Int {
        return AdapterConstants.DETAILS
    }
}