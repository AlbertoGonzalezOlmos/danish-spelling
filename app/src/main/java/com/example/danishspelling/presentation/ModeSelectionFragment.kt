package com.example.danishspelling.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.danishspelling.R
import com.example.danishspelling.databinding.FragmentModeSelectionBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ModeSelectionFragment : Fragment() {

    private var _binding: FragmentModeSelectionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentModeSelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnChildMode.setOnClickListener {
            findNavController().navigate(R.id.action_to_child_mode)
        }

        binding.btnParentMode.setOnClickListener {
            findNavController().navigate(R.id.action_to_parent_mode)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
