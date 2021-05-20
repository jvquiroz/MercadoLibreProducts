package com.jvquiroz.mercadolibreproducts.presentation.adapter.model

/**
 * @see ViewType to hold an @see AdapterConstants.PICTURES
 */
data class PictureItemView(
    val urls: List<String>
): ViewType {
    override fun getViewType(): Int {
        return AdapterConstants.PICTURES
    }
}