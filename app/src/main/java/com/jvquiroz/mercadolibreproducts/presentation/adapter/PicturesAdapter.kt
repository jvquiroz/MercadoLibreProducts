package com.jvquiroz.mercadolibreproducts.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jvquiroz.mercadolibreproducts.R
import com.jvquiroz.mercadolibreproducts.databinding.ItemViewPictureBinding
import com.jvquiroz.mercadolibreproducts.presentation.util.ImageUtils

/**
 * Delegate adapter to display a Product's pictures as a carousel.
 */
class PicturesAdapter : RecyclerView.Adapter<PicturesAdapter.PictureViewHolder>() {

    var urls: List<String> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class PictureViewHolder(
        view: ItemViewPictureBinding,
    ) : RecyclerView.ViewHolder(view.root) {

        private val binding = ItemViewPictureBinding.bind(view.root)
        val image = binding.pictureImage
        val container = binding.pictureContainer
        val label = binding.pageLabel
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureViewHolder {
        return PictureViewHolder(
            ItemViewPictureBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int = urls?.size

    override fun onBindViewHolder(holder: PictureViewHolder, position: Int) {
        holder.label.text = "${position + 1} / ${urls.size}"
        ImageUtils.load(
            imageView = holder.image,
            placeholder = R.drawable.ic_image,
            url = urls[position],
            shimmerLayout = holder.container
        )
    }
}