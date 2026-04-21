package com.example.seriesexplorer.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.seriesexplorer.databinding.ItemShowBinding
import com.example.seriesexplorer.domain.model.Show

class ShowAdapter(private val onShowClick: (Show) -> Unit) :
    ListAdapter<Show, ShowAdapter.ShowViewHolder>(ShowDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowViewHolder {
        val binding = ItemShowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShowViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ShowViewHolder(private val binding: ItemShowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(show: Show) {
            binding.tvShowName.text = show.name
            binding.tvShowInfo.text = "${show.language} | ${show.status}"
            Glide.with(binding.ivShowPoster)
                .load(show.imageUrl)
                .into(binding.ivShowPoster)
            binding.root.setOnClickListener { onShowClick(show) }
        }
    }

    class ShowDiffCallback : DiffUtil.ItemCallback<Show>() {
        override fun areItemsTheSame(oldItem: Show, newItem: Show): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Show, newItem: Show): Boolean = oldItem == newItem
    }
}