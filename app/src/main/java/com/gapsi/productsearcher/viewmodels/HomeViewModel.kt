package com.gapsi.productsearcher.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gapsi.productsearcher.config.AppDB
import com.gapsi.productsearcher.data.dao.WordDAO
import com.gapsi.productsearcher.data.entites.Word
import com.gapsi.productsearcher.enums.ExceptionType
import com.gapsi.productsearcher.interfaces.ISearch
import com.gapsi.productsearcher.interfaces.IWebErrorListener
import com.gapsi.productsearcher.data.remote.implementations.ServiceSearch
import com.gapsi.productsearcher.data.remote.interfaces.IServiceSearch
import com.gapsi.productsearcher.ui.model.Product
import com.gapsi.productsearcher.util.WrapperEvent
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.StringBuilder

class HomeViewModel(private val context: Context) : ViewModel(), ISearch.RequestListener, IWebErrorListener {

    val loading: MutableLiveData<Boolean> = MutableLiveData()
    val resultProducts: MutableLiveData<List<Product>> = MutableLiveData()
    val historyWords: MutableLiveData<CharSequence> = MutableLiveData()
    val webError: MutableLiveData<WrapperEvent<String>> = MutableLiveData()
    private val disposable = CompositeDisposable()

    private val job = Job()
    private val mainThread = CoroutineScope(job + Dispatchers.Main)
    private val ioThread = CoroutineScope(job + Dispatchers.IO)
    private val remote: IServiceSearch = ServiceSearch(context, disposable)
    private val dao: WordDAO = AppDB.getInstance(context).wordDAO()

    fun search(value: CharSequence?) {
        value?.let { text ->
            val entry = text.trim()
            if (entry.isNotBlank()) {
                loading.value = true
                remote.getProducts(
                    value = text.toString(),
                    onResponse = this::onSearchResponse,
                    onException = this::handleException
                )
            } else {
                disposable.clear()
                loading.value = false
                resultProducts.value = ArrayList<Product>()
            }
        }
    }

    fun onHistoryClickListener() {
        ioThread.launch {
            val builder = StringBuilder()
            val list = dao.getAll().reversed()
            for (item in list) {
                builder.append(item.name + "\n")
            }
            mainThread.launch {
                historyWords.value = builder
            }
        }
    }

    override fun onSearchResponse(payload: List<Product>?, searchWord: String) {
        loading.value = false
        if (payload != null) {
            resultProducts.value = payload
        }
        ioThread.launch {
            dao.insert(Word(searchWord))
        }
    }

    override fun handleException(type: ExceptionType, code: Int?, message: String?) {
        loading.value = false
        webError.value = WrapperEvent(message ?: "Error Web")
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }


}
