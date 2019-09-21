package com.gapsi.productsearcher.util

import androidx.paging.PagedList
import com.gapsi.productsearcher.constants.App
import java.text.DecimalFormat

object Util {
    /**
     * Method used to format a quantity to a money format
     * @param amount quantity to format
     * @return String with money format
     */
    fun commaFormat(amount: Float): String {
        val formatter = DecimalFormat("###,###,##0.00" )
        return formatter.format(amount)
    }

    fun buildPagedConfig(pageSize: Int = App.PAGING_ITEMS, initialLoad: Int = pageSize * 3, placeholder:Boolean = false): PagedList.Config {
        return PagedList.Config.Builder()
            .setEnablePlaceholders(placeholder)
            .setPageSize(pageSize)
            .setPrefetchDistance(10)
            .setInitialLoadSizeHint(initialLoad)
            .build()
    }
}