package com.example.danishspelling.presentation.parent

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.danishspelling.R
import com.example.danishspelling.databinding.FragmentListManagementBinding
import com.example.danishspelling.domain.model.DifficultyLevel
import com.example.danishspelling.presentation.parent.adapter.ListManagementAdapter
import com.example.danishspelling.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListManagementFragment : Fragment() {

    private var _binding: FragmentListManagementBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ListManagementViewModel by viewModels()
    private lateinit var adapter: ListManagementAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListManagementBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupFab()
        observeViewModel()
    }

    private var isSelectionMode = false

    private fun setupRecyclerView() {
        adapter = ListManagementAdapter(
            onListClick = { practiceList ->
                if (isSelectionMode) {
                    adapter.toggleSelection(practiceList.id)
                    updateBulkDeleteButton()
                } else {
                    val action = ListManagementFragmentDirections
                        .actionToSentences(practiceList.id)
                    findNavController().navigate(action)
                }
            },
            onDeleteClick = { practiceList ->
                showDeleteConfirmation(practiceList.id, practiceList.name)
            }
        )

        binding.rvLists.layoutManager = LinearLayoutManager(requireContext())
        binding.rvLists.adapter = adapter
    }

    private fun setupFab() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.fabAddList.setOnClickListener {
            showAddListDialog()
        }

        binding.btnBulkDelete.setOnClickListener {
            toggleSelectionMode()
        }

        binding.fabDeleteSelected.setOnClickListener {
            showBulkDeleteConfirmation()
        }
    }

    private fun toggleSelectionMode() {
        isSelectionMode = !isSelectionMode
        adapter.setSelectionMode(isSelectionMode)

        if (isSelectionMode) {
            binding.btnBulkDelete.text = "Cancel"
            binding.fabAddList.hide()
        } else {
            binding.btnBulkDelete.text = "Select Multiple"
            binding.fabAddList.show()
            binding.fabDeleteSelected.hide()
        }
    }

    private fun updateBulkDeleteButton() {
        if (adapter.hasSelection()) {
            binding.fabDeleteSelected.show()
        } else {
            binding.fabDeleteSelected.hide()
        }
    }

    private fun showBulkDeleteConfirmation() {
        val selectedCount = adapter.getSelectedItems().size
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.delete))
            .setMessage("Delete $selectedCount selected list(s) and all their sentences?")
            .setPositiveButton(getString(R.string.delete)) { _, _ ->
                val selectedIds = adapter.getSelectedItems()
                selectedIds.forEach { listId ->
                    viewModel.deleteList(listId)
                }
                requireContext().showToast("$selectedCount list(s) deleted")
                toggleSelectionMode()
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    private fun showAddListDialog() {
        val dialogView = LayoutInflater.from(requireContext())
            .inflate(R.layout.dialog_add_list, null)

        val nameInput = dialogView.findViewById<EditText>(R.id.et_list_name)
        val descriptionInput = dialogView.findViewById<EditText>(R.id.et_list_description)
        val difficultySpinner = dialogView.findViewById<Spinner>(R.id.spinner_difficulty)

        // Set up difficulty spinner
        val difficulties = DifficultyLevel.values().map { it.name }
        difficultySpinner.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            difficulties
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.add_list))
            .setView(dialogView)
            .setPositiveButton(getString(R.string.add)) { _, _ ->
                val name = nameInput.text.toString().trim()
                val description = descriptionInput.text.toString().trim()
                val difficulty = DifficultyLevel.values()[difficultySpinner.selectedItemPosition]

                if (name.isNotBlank()) {
                    viewModel.addList(name, description, difficulty)
                    requireContext().showToast("List added!")
                } else {
                    requireContext().showToast("List name cannot be empty")
                }
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    private fun showDeleteConfirmation(listId: Long, listName: String) {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.delete))
            .setMessage("Delete \"$listName\" and all its sentences?")
            .setPositiveButton(getString(R.string.delete)) { _, _ ->
                viewModel.deleteList(listId)
                requireContext().showToast("List deleted")
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.practiceLists.collect { lists ->
                adapter.submitList(lists)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
