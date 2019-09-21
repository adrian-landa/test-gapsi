package com.gapsi.productsearcher.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gapsi.productsearcher.R
import com.gapsi.productsearcher.ui.model.Product
import com.gapsi.productsearcher.util.Util
import kotlinx.android.synthetic.main.item_product.view.*

/**
 * @author Luis L.
 *         Description:
 *         created on 21/09/2019
 */
class SearchAdapter : ListAdapter<Product, SearchAdapter.ViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itm = getItem(position)
        itm?.let {
            holder.bind(it)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Product) {
            val formatted = Util.commaFormat(item.listPrice)
            val price = itemView.context.getString(R.string.template_currency, formatted)
            val emptyMarkets = itemView.context.getString(R.string.label_empty_market)
            itemView.tvProductName.text = item.name
            itemView.tvProductPrice.text = price
            itemView.tvProductUbication.text = if (item.isMarketPlace)item.marketPlace else emptyMarkets
            Glide.with(itemView)
                .load(item.thumbnail)
                .apply(RequestOptions().placeholder(R.drawable.ic_placeholder).error(R.drawable.ic_placeholder))
                .into(itemView.imgProductThumbnail)
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean =
                oldItem == newItem
        }
    }

}