package com.gapsi.productsearcher.ui.home

import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.gapsi.productsearcher.R
import com.gapsi.productsearcher.ui.adapters.SearchAdapter
import com.gapsi.productsearcher.util.Util
import com.gapsi.productsearcher.util.getViewModel
import com.gapsi.productsearcher.viewmodels.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
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
            if (list.isEmpty()) {
                recyclerResult.visibility = View.GONE
                emptyContainer.visibility = View.VISIBLE
            } else {
                recyclerResult.visibility = View.VISIBLE
                emptyContainer.visibility = View.GONE
            }
            adapter.submitList(list)
        })

        viewmodel.historyWords.observe(this, Observer {list->
            val bottomDialog = BottomSheetDialog(this@HomeActivity)
            val view = this@HomeActivity.layoutInflater.inflate(R.layout.bottom_dialog_template, null)
            if (view != null) {
                val title: TextView = view.findViewById(R.id.tvTemplateTitle)
                val body: TextView = view.findViewById(R.id.tvTemplateBody)
                title.setText(R.string.label_title_history)
                body.text = list
                bottomDialog.setContentView(view)
                bottomDialog.show()
            }

        })

        viewmodel.loading.observe(this, Observer { isLoading ->
            val visibility = if (isLoading) View.VISIBLE else View.GONE
            progress.visibility = visibility
        })

        viewmodel.webError.observe(this, Observer { error ->
            error.getContentIfNotHandled()?.let { message ->
                Toast.makeText(this@HomeActivity, message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setListener() {
        tieName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewmodel.search(text)
            }
        })
        recyclerResult.setOnTouchListener { _, _ ->
            Util.hideSoftKeyboard(this)
            false
        }
        parentContainer.setOnClickListener {
            Util.hideSoftKeyboard(this)
        }
        fabHistory.setOnClickListener {
            viewmodel.onHistoryClickListener()
        }


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
        if (item?.itemId == R.id.itmSearch) {
            tinName.visibility = View.VISIBLE
            return true
        }
        return false
    }
}