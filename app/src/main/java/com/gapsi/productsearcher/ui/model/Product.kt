package com.gapsi.productsearcher.ui.model

import android.provider.MediaStore

data class Product(
    val id: String,
    val name: String,
    val listPrice: Float,
    val isMarketPlace: Boolean,
    val marketPlace: String,
    val thumbnail: String
    )