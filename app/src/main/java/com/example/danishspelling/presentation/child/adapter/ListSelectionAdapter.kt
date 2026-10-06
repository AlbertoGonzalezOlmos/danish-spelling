package com.example.danishspelling.presentation.child.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.danishspelling.data.local.entities.PracticeList
import com.example.danishspelling.databinding.ItemListSelectionBinding

class ListSelectionAdapter(
    private val onListClick: (PracticeList) -> Unit
) : ListAdapter<PracticeList, ListSelectionAdapter.ListViewHolder>(ListDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemListSelectionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ListViewHolder(binding, onListClick)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ListViewHolder(
        private val binding: ItemListSelectionBinding,
        private val onListClick: (PracticeList) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(practiceList: PracticeList) {
            binding.tvListName.text = practiceList.name
            binding.tvListDescription.text = practiceList.description

            // Set theme color
            try {
                binding.cardView.setCardBackgroundColor(Color.parseColor(practiceList.colorTheme))
            } catch (e: IllegalArgumentException) {
                // Use default color if parsing fails
            }

            binding.root.setOnClickListener {
                onListClick(practiceList)
            }
        }
    }

    private class ListDiffCallback : DiffUtil.ItemCallback<PracticeList>() {
        override fun areItemsTheSame(oldItem: PracticeList, newItem: PracticeList): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PracticeList, newItem: PracticeList): Boolean {
            return oldItem == newItem
        }
    }
}
