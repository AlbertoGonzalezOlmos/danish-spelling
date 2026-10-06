package com.example.danishspelling.presentation.child

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.danishspelling.R
import com.example.danishspelling.databinding.FragmentPracticeBinding
import com.example.danishspelling.service.DanishTTSService
import com.example.danishspelling.util.hide
import com.example.danishspelling.util.show
import com.example.danishspelling.util.showKeyboard
import com.example.danishspelling.util.showToast
import com.example.danishspelling.util.toStarEmoji
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PracticeFragment : Fragment() {

    private var _binding: FragmentPracticeBinding? = null
    private val binding get() = _binding!!

    private val args: PracticeFragmentArgs by navArgs()
    private val viewModel: PracticeViewModel by viewModels()
    private var hasNavigatedToReward = false
    private var ttsErrorDialogShown = false

    @javax.inject.Inject
    lateinit var ttsService: DanishTTSService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPracticeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.startPracticeSession(args.listId, args.randomOrder, args.incorrectOnly)
        setupClickListeners()
        observeViewModel()
    }

    private fun setupClickListeners() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnListen.setOnClickListener {
            viewModel.speakCurrentSentence()
        }

        binding.btnShowAnswer.setOnClickListener {
            showAnswer()
        }

        binding.btnCorrect.setOnClickListener {
            viewModel.markAsCorrect()
            viewModel.moveToNextSentence()
        }

        binding.btnIncorrect.setOnClickListener {
            viewModel.markAsIncorrect()
            showAnswerAndFeedback()
        }

        binding.btnPrevious.setOnClickListener {
            viewModel.moveToPreviousSentence()
        }

        binding.btnSkip.setOnClickListener {
            viewModel.skipSentence()
        }
    }

    private fun showAnswer() {
        val currentSentence = viewModel.practiceState.value.currentSentence
        currentSentence?.let {
            binding.tvRevealedAnswer.text = it.text
            binding.tvRevealedAnswer.show()
        }
    }

    private fun showAnswerAndFeedback() {
        val currentSentence = viewModel.practiceState.value.currentSentence
        currentSentence?.let {
            binding.tvRevealedAnswer.text = it.text
            binding.tvRevealedAnswer.show()
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.practiceState.collect { state ->
                updateUI(state)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.ttsState.collect { state ->
                binding.btnListen.isEnabled = !state.isSpeaking
                state.errorMessage?.let { msg ->
                    if (!ttsErrorDialogShown) {
                        ttsErrorDialogShown = true
                        showTTSErrorDialog(msg)
                    }
                }
            }
        }
    }

    private fun updateUI(state: PracticeState) {
        when {
            state.isLoading -> {
                binding.progressBar.show()
                binding.contentLayout.hide()
            }
            state.isSessionComplete -> {
                if (!hasNavigatedToReward) {
                    hasNavigatedToReward = true
                    navigateToReward(state.sessionStats)
                }
            }
            state.currentSentence != null -> {
                binding.progressBar.hide()
                binding.contentLayout.show()

                // Update progress
                binding.tvProgress.text = getString(
                    R.string.progress_format,
                    state.currentIndex + 1,
                    state.totalSentences
                )

                // Update stars
                binding.tvTotalStars.text = getString(
                    R.string.total_stars,
                    state.sessionStats.totalStars
                )

                // Hide revealed answer on each new sentence
                binding.tvRevealedAnswer.hide()

                // Enable/disable Previous button
                binding.btnPrevious.isEnabled = state.currentIndex > 0
            }
            state.error != null -> {
                binding.progressBar.hide()
                requireContext().showToast(state.error)
            }
        }
    }

    private fun showTTSErrorDialog(message: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Text-to-Speech")
            .setMessage(message)
            .setPositiveButton("Open TTS Settings") { _, _ ->
                ttsErrorDialogShown = false
                try {
                    startActivity(ttsService.getTTSSettingsIntent())
                } catch (e: Exception) {
                    requireContext().showToast("Could not open TTS settings")
                }
            }
            .setNegativeButton("Dismiss") { _, _ ->
                ttsErrorDialogShown = false
            }
            .setOnDismissListener {
                ttsErrorDialogShown = false
            }
            .show()
    }

    private fun navigateToReward(stats: SessionStats) {
        val action = PracticeFragmentDirections.actionToReward(
            totalStars = stats.totalStars,
            accuracy = stats.averageAccuracy
        )
        findNavController().navigate(action)
    }

    override fun onResume() {
        super.onResume()
        // If TTS isn't working, force a full re-init after a short delay
        // to give the system time to register a newly enabled TTS engine.
        if (!ttsService.isInitialized.value) {
            view?.postDelayed({
                if (!ttsService.isInitialized.value) {
                    ttsErrorDialogShown = false
                    ttsService.reinitialize()
                }
            }, 1500)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
