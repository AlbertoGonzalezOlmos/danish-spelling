package com.example.danishspelling.presentation.parent.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.danishspelling.data.local.entities.Sentence
import com.example.danishspelling.databinding.ItemSentenceBinding

class SentenceAdapter(
    private val onSentenceClick: (Sentence) -> Unit,
    private val onEditClick: (Sentence) -> Unit,
    private val onDeleteClick: (Sentence) -> Unit,
    private val onDragHandleTouch: (RecyclerView.ViewHolder) -> Unit = {}
) : ListAdapter<Sentence, SentenceAdapter.SentenceViewHolder>(SentenceDiffCallback()) {

    private var isSelectionMode = false
    private val selectedItems = mutableSetOf<Long>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SentenceViewHolder {
        val binding = ItemSentenceBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SentenceViewHolder(binding, onSentenceClick, onEditClick, onDeleteClick, onDragHandleTouch)
    }

    override fun onBindViewHolder(holder: SentenceViewHolder, position: Int) {
        holder.bind(getItem(position), isSelectionMode, selectedItems.contains(getItem(position).id))
    }

    fun setSelectionMode(enabled: Boolean) {
        isSelectionMode = enabled
        if (!enabled) {
            selectedItems.clear()
        }
        notifyDataSetChanged()
    }

    fun toggleSelection(sentenceId: Long) {
        if (selectedItems.contains(sentenceId)) {
            selectedItems.remove(sentenceId)
        } else {
            selectedItems.add(sentenceId)
        }
        notifyDataSetChanged()
    }

    fun getSelectedItems(): Set<Long> = selectedItems.toSet()

    fun hasSelection(): Boolean = selectedItems.isNotEmpty()

    fun selectAll() {
        selectedItems.clear()
        currentList.forEach { sentence ->
            selectedItems.add(sentence.id)
        }
        notifyDataSetChanged()
    }

    fun clearSelection() {
        selectedItems.clear()
        notifyDataSetChanged()
    }

    private var dragItems: MutableList<Sentence>? = null
    private var attachedRecyclerView: RecyclerView? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        attachedRecyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        attachedRecyclerView = null
    }

    override fun getItemCount(): Int = dragItems?.size ?: super.getItemCount()

    override fun getItem(position: Int): Sentence {
        return dragItems?.get(position) ?: super.getItem(position)
    }

    fun onItemMove(fromPosition: Int, toPosition: Int) {
        if (dragItems == null) {
            dragItems = currentList.toMutableList()
        }
        val items = dragItems!!
        val movedItem = items.removeAt(fromPosition)
        items.add(toPosition, movedItem)
        notifyItemMoved(fromPosition, toPosition)
    }

    fun finishDrag(): List<Sentence>? {
        val items = dragItems?.toList() ?: return null
        dragItems = null

        // Sync the internal list to match the drag result immediately,
        // with animations disabled. Both submitList calls below are
        // synchronous fast-paths (null list and empty-to-new list).
        val rv = attachedRecyclerView
        val animator = rv?.itemAnimator
        rv?.itemAnimator = null
        super.submitList(null)
        super.submitList(items)
        rv?.post { rv.itemAnimator = animator }

        return items
    }

    class SentenceViewHolder(
        private val binding: ItemSentenceBinding,
        private val onSentenceClick: (Sentence) -> Unit,
        private val onEditClick: (Sentence) -> Unit,
        private val onDeleteClick: (Sentence) -> Unit,
        private val onDragHandleTouch: (RecyclerView.ViewHolder) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            // Set up drag handle touch listener once
            binding.ivDragHandle.setOnTouchListener { _, event ->
                if (event.action == android.view.MotionEvent.ACTION_DOWN) {
                    onDragHandleTouch(this)
                    true
                } else {
                    false
                }
            }
        }

        fun bind(sentence: Sentence, isSelectionMode: Boolean, isSelected: Boolean) {
            binding.tvSentence.text = sentence.text
            binding.tvDifficulty.text = "Difficulty: ${sentence.difficulty}/5"

            // Show/hide checkbox and drag handle based on selection mode
            if (isSelectionMode) {
                binding.checkboxSelect.visibility = android.view.View.VISIBLE
                binding.checkboxSelect.isChecked = isSelected
                binding.ivDragHandle.visibility = android.view.View.GONE
                binding.btnEdit.visibility = android.view.View.GONE
                binding.btnDelete.visibility = android.view.View.GONE
            } else {
                binding.checkboxSelect.visibility = android.view.View.GONE
                binding.ivDragHandle.visibility = android.view.View.VISIBLE
                binding.btnEdit.visibility = android.view.View.VISIBLE
                binding.btnDelete.visibility = android.view.View.VISIBLE
            }

            binding.root.setOnClickListener {
                onSentenceClick(sentence)
            }

            binding.checkboxSelect.setOnClickListener {
                onSentenceClick(sentence)
            }

            binding.btnEdit.setOnClickListener {
                onEditClick(sentence)
            }

            binding.btnDelete.setOnClickListener {
                onDeleteClick(sentence)
            }
        }
    }

    private class SentenceDiffCallback : DiffUtil.ItemCallback<Sentence>() {
        override fun areItemsTheSame(oldItem: Sentence, newItem: Sentence): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Sentence, newItem: Sentence): Boolean {
            return oldItem.text == newItem.text
                    && oldItem.difficulty == newItem.difficulty
                    && oldItem.isActive == newItem.isActive
                    && oldItem.incorrectCount == newItem.incorrectCount
                    && oldItem.hints == newItem.hints
        }
    }
}
