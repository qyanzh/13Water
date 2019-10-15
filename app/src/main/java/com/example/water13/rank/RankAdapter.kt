package com.example.water13.rank

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.water13.databinding.RankListItemBinding
import com.example.water13.source.RankResponse

class RankAdapter(private val context: Context, private val clickListener: ItemListener) :
    ListAdapter<RankResponse, RankAdapter.ViewHolder>(RankDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(context, item,clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }


    class ViewHolder private constructor(val binding: RankListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(context: Context, item: RankResponse,clickListener: ItemListener) {
            binding.rank = item
            binding.clickListener = clickListener
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

class ItemListener(val clickListener: (gamerId: Int) -> Unit) {
    fun onClick(gamerId: Int) {
        clickListener(gamerId)
    }
}
