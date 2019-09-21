package com.gapsi.productsearcher.interfaces

import com.gapsi.productsearcher.ui.model.Product

interface ISearch {
    interface RequestListener {
        fun onSearchResponse(
            payload: List<Product>?,
            searchWord: String
        )


    }
}