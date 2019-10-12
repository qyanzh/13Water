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
import com.example.water13.source.HistoryDetailResponse.Detail
import kotlinx.android.synthetic.main.cards_vertical.view.*

class DetailAdapter(private val context: Context) :
    ListAdapter<Detail, DetailAdapter.ViewHolder>(DetailDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(context, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }


    class ViewHolder private constructor(val binding: DetailListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(context: Context, item: Detail) {
            binding.detail = item
            binding.include.apply {
                val viewList =
                    mutableListOf<ImageView>().apply {
                        add(card0)
                        add(card1)
                        add(card2)
                        add(card3)
                        add(card4)
                        add(card5)
                        add(card6)
                        add(card7)
                        add(card8)
                        add(card9)
                        add(card10)
                        add(card11)
                        add(card12)
                    }
                loadPoker(context, item.card.joinToString(" ").toCardImages(), viewList)
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = DetailListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

}


class DetailDiffCallback : DiffUtil.ItemCallback<Detail>() {

    override fun areItemsTheSame(oldItem: Detail, newItem: Detail): Boolean {
        return oldItem.playerId == newItem.playerId
    }

    override fun areContentsTheSame(oldItem: Detail, newItem: Detail): Boolean {
        return oldItem == newItem
    }

}
