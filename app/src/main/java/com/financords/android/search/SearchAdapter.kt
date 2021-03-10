package com.financords.android.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.financords.android.databinding.SearchItemBinding

class SearchAdapter : ListAdapter<String, SearchViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
                    oldItem == newItem

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
                    oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = SearchItemBinding.inflate(layoutInflater, parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class SearchViewHolder(
        private val binding: SearchItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(text: String) {
        binding.tvSearchResult.text = text
    }

}