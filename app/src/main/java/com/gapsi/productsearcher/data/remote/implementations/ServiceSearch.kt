package com.gapsi.productsearcher.data.remote.implementations

import android.content.Context
import com.gapsi.productsearcher.constants.Json
import com.gapsi.productsearcher.constants.Web
import com.gapsi.productsearcher.enums.ExceptionType
import com.gapsi.productsearcher.data.remote.api.APIProducts
import com.gapsi.productsearcher.data.remote.interfaces.IServiceSearch
import com.gapsi.productsearcher.retrofit.NoInternetException
import com.gapsi.productsearcher.retrofit.RetrofitFactory
import com.gapsi.productsearcher.ui.model.Product
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException


class ServiceSearch(val context: Context, private val compositeDisposable: CompositeDisposable) : IServiceSearch {


    private val service: APIProducts = RetrofitFactory.makeService(Web.URL_BASE, APIProducts::class.java, context)

    override fun getProducts(
        value: String,
        page: Int,
        onResponse: (payload: List<Product>?, searchWord: String) -> Unit,
        onException: (type: ExceptionType, code: Int?, message: String?) -> Unit
    ) {
        compositeDisposable.add(
            service.getProducts(value = value, page = page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { response ->
                        val jsonResponse = JSONObject(response.string())
                        val data = jsonResponse.getJSONObject(Json.LABEL_PLP_RESULTS)
                        val records = data.getJSONArray(Json.LABEL_RECORDS)
                        val products: ArrayList<Product> = ArrayList()
                        for (i in 0 until records.length()) {
                            val record = records.getJSONObject(i)
                            val isMarketPlace = record.getBoolean(Json.LABEL_PRODUCT_IS_MARKET)
                            val marketPlace =
                                if (isMarketPlace) record.getString(Json.LABEL_PRODUCT_MARKET) else "No Disponible"
                            products.add(
                                Product(
                                    id = record.getString(Json.LABEL_PRODUCT_ID),
                                    name = record.getString(Json.LABEL_PRODUCT_NAME),
                                    listPrice = record.getDouble(Json.LABEL_PRODUCT_PRICE).toFloat(),
                                    isMarketPlace = isMarketPlace,
                                    marketPlace = marketPlace,
                                    thumbnail = record.getString(Json.LABEL_PRODUCT_THUMBNAIL)
                                )
                            )
                        }
                        onResponse.invoke(products,value)

                    },
                    { errorResponse ->
                        when (errorResponse) {
                            is HttpException -> onException.invoke(
                                ExceptionType.HTTP,
                                errorResponse.code(),
                                errorResponse.message
                            )
                            is SocketTimeoutException -> onException.invoke(
                                ExceptionType.TIME_OUT,
                                null,
                                errorResponse.message
                            )
                            is NoInternetException -> onException.invoke(
                                ExceptionType.NO_INTERNET,
                                null,
                                errorResponse.message
                            )
                            is IOException -> onException.invoke(ExceptionType.IO, null, errorResponse.message)
                        }
                    }
                )
        )

    }


}