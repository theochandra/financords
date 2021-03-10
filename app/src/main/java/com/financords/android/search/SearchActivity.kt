package com.financords.android.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.financords.android.R
import com.financords.android.databinding.ActivitySearchBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding

    private val searchAdapter = SearchAdapter()
    private val viewModel: SearchViewModel by viewModels {
        SearchViewModel.Factory(assets, Dispatchers.IO)
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil
            .setContentView(this, R.layout.activity_search)
//        viewModel = ViewModelProvider(this)
//            .get(SearchViewModel::class.java)

        binding.viewModel = viewModel
        binding.rvData.adapter = searchAdapter
        binding.etSearch.requestFocus()

        // start with empty query view
        searchAdapter.submitList(emptyList())

        onTextTyped()
        viewModel.searchResult.observe(this, {
            handleSearchResult(it)
        })
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    private fun onTextTyped() {
        binding.etSearch.doAfterTextChanged {
            lifecycleScope.launch {
                viewModel.queryChannel.send(it.toString())
            }
        }
    }

    private fun handleSearchResult(it: SearchResult) {
        when (it) {
            is ValidResult -> {
                searchAdapter.submitList(it.result)
            }
            is ErrorResult -> {
                searchAdapter.submitList(emptyList())
            }
            is EmptyResult -> {
                searchAdapter.submitList(emptyList())
            }
            is EmptyQuery -> {
                searchAdapter.submitList(emptyList())
            }
            is TerminalError -> {
                // Something wen't terribly wrong!
                println("Our Flow terminated unexpectedly, so we're bailing!")
                Toast.makeText(
                    this,
                    "Unexpected error in SearchRepository!",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

}