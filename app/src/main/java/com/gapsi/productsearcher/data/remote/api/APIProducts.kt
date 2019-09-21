package com.gapsi.productsearcher.data.remote.api

import com.gapsi.productsearcher.constants.Web
import io.reactivex.Flowable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface APIProducts {
    @GET(Web.URL_SERVICE_SEARCH_PRODUCTS)
    fun getProducts(@Query(value = Web.PARAM_SEARCH) value: String = "",
                    @Query(value = Web.PARAM_PAGE)page:Int = 1,
                    @Query(value = Web.PARAM_FORCE) force: Boolean = true,
                    @Query(value = Web.PARAM_ITEMS)items:Int = Web.PAGING_ITEMS): Flowable<ResponseBody>
}