package com.example.danishspelling.presentation.parent.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.danishspelling.data.local.entities.PracticeList
import com.example.danishspelling.databinding.ItemListManagementBinding

class ListManagementAdapter(
    private val onListClick: (PracticeList) -> Unit,
    private val onDeleteClick: (PracticeList) -> Unit
) : ListAdapter<PracticeList, ListManagementAdapter.ListViewHolder>(ListDiffCallback()) {

    private var isSelectionMode = false
    private val selectedItems = mutableSetOf<Long>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemListManagementBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ListViewHolder(binding, onListClick, onDeleteClick)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(getItem(position), isSelectionMode, selectedItems.contains(getItem(position).id))
    }

    fun setSelectionMode(enabled: Boolean) {
        isSelectionMode = enabled
        if (!enabled) {
            selectedItems.clear()
        }
        notifyDataSetChanged()
    }

    fun toggleSelection(listId: Long) {
        if (selectedItems.contains(listId)) {
            selectedItems.remove(listId)
        } else {
            selectedItems.add(listId)
        }
        notifyDataSetChanged()
    }

    fun getSelectedItems(): Set<Long> = selectedItems.toSet()

    fun hasSelection(): Boolean = selectedItems.isNotEmpty()

    class ListViewHolder(
        private val binding: ItemListManagementBinding,
        private val onListClick: (PracticeList) -> Unit,
        private val onDeleteClick: (PracticeList) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(practiceList: PracticeList, isSelectionMode: Boolean, isSelected: Boolean) {
            binding.tvListName.text = practiceList.name
            binding.tvListDescription.text = practiceList.description
            binding.tvDifficulty.text = practiceList.difficultyLevel.name

            // Show/hide checkbox based on selection mode
            if (isSelectionMode) {
                binding.checkboxSelect.visibility = android.view.View.VISIBLE
                binding.checkboxSelect.isChecked = isSelected
                binding.btnDelete.visibility = android.view.View.GONE
            } else {
                binding.checkboxSelect.visibility = android.view.View.GONE
                binding.btnDelete.visibility = android.view.View.VISIBLE
            }

            binding.root.setOnClickListener {
                onListClick(practiceList)
            }

            binding.checkboxSelect.setOnClickListener {
                onListClick(practiceList)
            }

            binding.btnDelete.setOnClickListener {
                onDeleteClick(practiceList)
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
