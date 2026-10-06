package com.example.danishspelling.presentation.child

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.danishspelling.databinding.FragmentRewardBinding
import com.example.danishspelling.util.toPercentageString
import com.example.danishspelling.util.toStarEmoji
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RewardFragment : Fragment() {

    private var _binding: FragmentRewardBinding? = null
    private val binding get() = _binding!!

    private val args: RewardFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRewardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Display reward information
        binding.tvStarsEarned.text = args.totalStars.toStarEmoji()
        binding.tvStarsCount.text = "You earned ${args.totalStars} stars!"
        binding.tvAccuracy.text = "Accuracy: ${args.accuracy.toPercentageString()}"

        binding.btnContinue.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
