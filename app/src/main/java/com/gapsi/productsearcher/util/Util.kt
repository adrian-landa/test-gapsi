package com.gapsi.productsearcher.util

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.paging.PagedList
import com.gapsi.productsearcher.constants.App
import com.gapsi.productsearcher.constants.Web
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

    fun buildPagedConfig(pageSize: Int = Web.PAGING_ITEMS, initialLoad: Int = pageSize * 3, placeholder:Boolean = false): PagedList.Config {
        return PagedList.Config.Builder()
            .setEnablePlaceholders(placeholder)
            .setPageSize(pageSize)
            .setPrefetchDistance(10)
            .setInitialLoadSizeHint(initialLoad)
            .build()
    }

    fun hideSoftKeyboard(activity: Activity) {
        val v = activity.currentFocus
        val inputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        v?.let {
            val windowToken = it.windowToken
            if (windowToken != null) {
                inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
            }
        }
    }
}