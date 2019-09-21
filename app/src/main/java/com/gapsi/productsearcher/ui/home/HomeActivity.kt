package com.gapsi.productsearcher.ui.home

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.gapsi.productsearcher.R
import com.gapsi.productsearcher.ui.adapters.SearchAdapter
import com.gapsi.productsearcher.util.getViewModel
import com.gapsi.productsearcher.viewmodels.HomeViewModel
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    private val viewmodel: HomeViewModel by lazy { getViewModel { HomeViewModel(applicationContext) } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setData()
        setListener()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun setData() {
        setToolbar()
        val adapter = SearchAdapter()
        recyclerResult.adapter = adapter
        recyclerResult.layoutManager = LinearLayoutManager(this)
        viewmodel.resultProducts.observe(this, Observer { list ->
            if (list.isEmpty()){
                recyclerResult.visibility = View.GONE
                emptyContainer.visibility = View.VISIBLE
            }else{
                recyclerResult.visibility = View.VISIBLE
                emptyContainer.visibility = View.GONE
            }
            adapter.submitList(list)
        })
    }

    private fun setListener() {

        Log.i("TAG_LOG", "SetListener")

        viewmodel.search("hola")
    }

    /**
     * Método que sincroniza el toolbar del layout con el del activity.
     */
    private fun setToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.let { actionBar ->
            actionBar.setDisplayHomeAsUpEnabled(false)
            actionBar.setDisplayShowTitleEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return false
    }
}