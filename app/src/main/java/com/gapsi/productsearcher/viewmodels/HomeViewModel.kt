package com.gapsi.productsearcher.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.gapsi.productsearcher.ui.model.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class HomeViewModel(private val context: Context) : ViewModel(){
    val loading: MutableLiveData<Boolean> = MutableLiveData()
    val resultProducts: MutableLiveData<List<Product>> = MutableLiveData()

    private val job = Job()
    private val mainThread = CoroutineScope(job + Dispatchers.Main)
    private val ioThread = CoroutineScope(job + Dispatchers.IO)

    fun search(value:String){

        Log.i("TAG_LOG", "Search")
        val result:ArrayList<Product> = ArrayList()
        result.add(Product("1","Chocolate",500f,600f,true,"Soriana","https://ss625.liverpool.com.mx/sm/1088074048.jpg"))
        result.add(Product("2","Freca",500f,600f,true,"Soriana","https://ss625.liverpool.com.mx/sm/1088074048.jpg"))
        result.add(Product("3","Cerveza",500f,600f,false,"Soriana","https://ss625.liverpool.com.mx/sm/1088074048.jpg"))
        result.add(Product("4","Internet",500f,600f,true,"Soriana","https://ss625.liverpool.com.mx/sm/1088074048.jpg"))
        result.add(Product("5","Chocolate",500f,600f,false,"Soriana","https://ss625.liverpool.com.mx/sm/1088074048.jpg"))
        resultProducts.value = result
    }




}
