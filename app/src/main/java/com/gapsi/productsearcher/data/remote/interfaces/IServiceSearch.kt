package com.gapsi.productsearcher.data.remote.interfaces

import com.gapsi.productsearcher.enums.ExceptionType
import com.gapsi.productsearcher.ui.model.Product

interface IServiceSearch {
    fun getProducts(
        value: String,
        page: Int = 1,
        onResponse: (
            payload: List<Product>?,
            searchWord: String
        ) -> Unit,
        onException: (
            type: ExceptionType,
            code: Int?,
            message: String?
        ) -> Unit
    )

}