package com.example.danishspelling.presentation.child

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.danishspelling.databinding.FragmentListSelectionBinding
import com.example.danishspelling.presentation.child.adapter.ListSelectionAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListSelectionFragment : Fragment() {

    private var _binding: FragmentListSelectionBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ListSelectionViewModel by viewModels()
    private lateinit var adapter: ListSelectionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListSelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        adapter = ListSelectionAdapter { practiceList ->
            showPracticeOptionsDialog(practiceList.id)
        }

        binding.rvLists.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvLists.adapter = adapter
    }

    private fun showPracticeOptionsDialog(listId: Long) {
        viewLifecycleOwner.lifecycleScope.launch {
            val (total, incorrect) = viewModel.getSentenceCounts(listId)

            val options = arrayOf(
                "All Words ($total)",
                "Incorrect Words Only ($incorrect)"
            )

            android.app.AlertDialog.Builder(requireContext())
                .setTitle("Choose Practice Mode")
                .setItems(options) { _, which ->
                    val incorrectOnly = which == 1

                    if (incorrectOnly && incorrect == 0) {
                        android.widget.Toast.makeText(
                            requireContext(),
                            "No incorrect words to practice",
                            android.widget.Toast.LENGTH_SHORT
                        ).show()
                        return@setItems
                    }

                    if (!incorrectOnly && total == 0) {
                        android.widget.Toast.makeText(
                            requireContext(),
                            "No words in this list to practice",
                            android.widget.Toast.LENGTH_SHORT
                        ).show()
                        return@setItems
                    }

                    val action = ListSelectionFragmentDirections
                        .actionToPractice(listId, randomOrder = true, incorrectOnly = incorrectOnly)
                    findNavController().navigate(action)
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
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
