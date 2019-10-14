package com.example.water13.history.details

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.water13.component.toCardImages
import com.example.water13.component.loadPoker
import com.example.water13.databinding.DetailListItemBinding
import com.example.water13.databinding.RankListItemBinding
import com.example.water13.source.HistoryDetailResponse.Detail
import com.example.water13.source.RankResponse
import kotlinx.android.synthetic.main.cards_vertical.view.*

class RankAdapter(private val context: Context) :
    ListAdapter<RankResponse, RankAdapter.ViewHolder>(RankDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(context, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }


    class ViewHolder private constructor(val binding: RankListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(context: Context, item: RankResponse) {
            binding.rank = item
            binding.position = adapterPosition
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RankListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

}


class RankDiffCallback : DiffUtil.ItemCallback<RankResponse>() {

    override fun areItemsTheSame(oldItem: RankResponse, newItem: RankResponse): Boolean {
        return oldItem.player_id == newItem.player_id
    }

    override fun areContentsTheSame(oldItem: RankResponse, newItem: RankResponse): Boolean {
        return oldItem == newItem
    }

}
