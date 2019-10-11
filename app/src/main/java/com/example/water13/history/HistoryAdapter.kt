package com.example.water13.history

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.water13.component.loadPoker
import com.example.water13.component.pokerStringToFileName
import com.example.water13.databinding.HistoryListItemBinding
import com.example.water13.source.HistoryListResponse.Data
import kotlinx.android.synthetic.main.cards_vertical.view.*

class HistoryAdapter(private val context: Context, private val clickListener: ItemListener) :
    ListAdapter<Data, HistoryAdapter.ViewHolder>(HistoryDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(context, item, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }


    class ViewHolder private constructor(val binding: HistoryListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(context: Context, item: Data, clickListener: ItemListener) {
            binding.history = item
            binding.clickListener = clickListener
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
                loadPoker(context, pokerStringToFileName(item.card.joinToString(" ")), viewList)
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = HistoryListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

}


class HistoryDiffCallback : DiffUtil.ItemCallback<Data>() {

    override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
        return oldItem.id == newItem.id
    }


    override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
        return oldItem == newItem
    }

}

class ItemListener(val clickListener: (history: Data) -> Unit) {
    fun onClick(history: Data) {
        clickListener(history)
    }
}

