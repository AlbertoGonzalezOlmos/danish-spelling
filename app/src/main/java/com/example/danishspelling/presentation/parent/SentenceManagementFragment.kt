package com.example.danishspelling.presentation.parent

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.SeekBar
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.danishspelling.R
import com.example.danishspelling.databinding.FragmentSentenceManagementBinding
import com.example.danishspelling.presentation.parent.adapter.SentenceAdapter
import com.example.danishspelling.service.DanishSpellCheckService
import com.example.danishspelling.service.SpellCheckOutcome
import com.example.danishspelling.service.ocr.OCREngine
import com.example.danishspelling.util.SpellCheckHighlighter
import com.example.danishspelling.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

@AndroidEntryPoint
class SentenceManagementFragment : Fragment() {

    private var _binding: FragmentSentenceManagementBinding? = null
    private val binding get() = _binding!!

    private val args: SentenceManagementFragmentArgs by navArgs()
    private val viewModel: SentenceManagementViewModel by viewModels()
    private lateinit var adapter: SentenceAdapter

    @javax.inject.Inject
    lateinit var ocrEngine: OCREngine

    @javax.inject.Inject
    lateinit var spellCheckService: DanishSpellCheckService

    private var currentPhotoPath: String? = null

    private val cameraLauncher = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            currentPhotoPath?.let { path ->
                val file = File(path)
                if (file.exists()) {
                    val uri = FileProvider.getUriForFile(
                        requireContext(),
                        "${requireContext().packageName}.fileprovider",
                        file
                    )
                    processImageWithOCR(uri)
                } else {
                    requireContext().showToast("Error: Photo file not found")
                }
            } ?: run {
                requireContext().showToast("Error: Photo path is null")
            }
        } else {
            requireContext().showToast("Camera cancelled or failed")
        }
    }

    private val galleryLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { processImageWithOCR(it) }
    }

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            launchCamera()
        } else {
            requireContext().showToast(getString(R.string.camera_permission_required))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSentenceManagementBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Restore photo path if fragment was recreated
        savedInstanceState?.let {
            currentPhotoPath = it.getString(KEY_PHOTO_PATH)
        }

        viewModel.loadSentences(args.listId)
        setupRecyclerView()
        setupFab()
        observeViewModel()
    }

    private var isSelectionMode = false

    private lateinit var itemTouchHelper: androidx.recyclerview.widget.ItemTouchHelper

    private fun setupRecyclerView() {
        adapter = SentenceAdapter(
            onSentenceClick = { sentence ->
                if (isSelectionMode) {
                    adapter.toggleSelection(sentence.id)
                    updateBulkDeleteButton()
                }
            },
            onEditClick = { sentence ->
                showEditSentenceDialog(sentence)
            },
            onDeleteClick = { sentence ->
                showDeleteConfirmation(sentence.id, sentence.text)
            },
            onDragHandleTouch = { viewHolder ->
                // Start drag when handle is touched
                if (::itemTouchHelper.isInitialized) {
                    itemTouchHelper.startDrag(viewHolder)
                }
            }
        )
        binding.rvSentences.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSentences.adapter = adapter

        // Set up drag-and-drop
        itemTouchHelper = androidx.recyclerview.widget.ItemTouchHelper(
            com.example.danishspelling.presentation.parent.adapter.SentenceItemTouchHelper(
                adapter = adapter,
                onMoveFinished = { reorderedList ->
                    // Save new order to database
                    saveReorderedSentences(reorderedList)
                }
            )
        )
        itemTouchHelper.attachToRecyclerView(binding.rvSentences)
    }

    private fun saveReorderedSentences(reorderedList: List<com.example.danishspelling.data.local.entities.Sentence>) {
        viewModel.updateSentenceOrders(reorderedList)
    }

    private fun setupFab() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.fabAddSentence.setOnClickListener {
            showAddMethodDialog()
        }

        binding.btnBulkDelete.setOnClickListener {
            toggleSelectionMode()
        }

        binding.fabDeleteSelected.setOnClickListener {
            showBulkDeleteConfirmation()
        }

        binding.checkboxSelectAll.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                adapter.selectAll()
            } else {
                adapter.clearSelection()
            }
            updateBulkDeleteButton()
        }
    }

    private fun showAddMethodDialog() {
        val options = arrayOf("Manual Text Input", "Take Photo", "Choose from Gallery", "Add Single Sentence")
        AlertDialog.Builder(requireContext())
            .setTitle("Add Sentences")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> showManualTextInputDialog()
                    1 -> checkCameraPermissionAndLaunch()
                    2 -> launchGallery()
                    3 -> showAddSentenceDialog()
                }
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    private fun toggleSelectionMode() {
        isSelectionMode = !isSelectionMode
        adapter.setSelectionMode(isSelectionMode)

        if (isSelectionMode) {
            binding.btnBulkDelete.text = "Cancel"
            binding.fabAddSentence.hide()
            binding.checkboxSelectAll.visibility = android.view.View.VISIBLE
            binding.checkboxSelectAll.isChecked = false
        } else {
            binding.btnBulkDelete.text = "Select Multiple"
            binding.fabAddSentence.show()
            binding.fabDeleteSelected.hide()
            binding.checkboxSelectAll.visibility = android.view.View.GONE
            binding.checkboxSelectAll.isChecked = false
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
            .setMessage("Delete $selectedCount selected sentence(s)?")
            .setPositiveButton(getString(R.string.delete)) { _, _ ->
                val selectedIds = adapter.getSelectedItems()
                selectedIds.forEach { sentenceId ->
                    viewModel.deleteSentence(sentenceId)
                }
                requireContext().showToast("$selectedCount sentence(s) deleted")
                toggleSelectionMode()
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    private fun showAddSentenceDialog() {
        val input = EditText(requireContext()).apply {
            hint = getString(R.string.sentence_text)
            minLines = 2
            maxLines = 5
        }

        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.add_sentence))
            .setView(input)
            .setPositiveButton(getString(R.string.add)) { _, _ ->
                val sentenceText = input.text.toString().trim()
                if (sentenceText.isNotBlank()) {
                    viewModel.addSentence(args.listId, sentenceText)
                    requireContext().showToast("Sentence added!")
                } else {
                    requireContext().showToast(getString(R.string.error_empty_sentence))
                }
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    private fun showEditSentenceDialog(sentence: com.example.danishspelling.data.local.entities.Sentence) {
        val input = EditText(requireContext()).apply {
            hint = getString(R.string.sentence_text)
            minLines = 2
            maxLines = 5
            setText(sentence.text)
            // Move cursor to end of text
            setSelection(text.length)
        }

        AlertDialog.Builder(requireContext())
            .setTitle("Edit Sentence")
            .setView(input)
            .setPositiveButton("Save") { _, _ ->
                val newText = input.text.toString().trim()
                if (newText.isNotBlank()) {
                    viewModel.updateSentence(sentence.id, newText)
                    requireContext().showToast("Sentence updated!")
                } else {
                    requireContext().showToast(getString(R.string.error_empty_sentence))
                }
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    private fun showDeleteConfirmation(sentenceId: Long, sentenceText: String) {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.delete))
            .setMessage("Delete \"$sentenceText\"?")
            .setPositiveButton(getString(R.string.delete)) { _, _ ->
                viewModel.deleteSentence(sentenceId)
                requireContext().showToast("Sentence deleted")
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.sentences.collect { sentences ->
                adapter.submitList(sentences)
            }
        }
    }


    private fun showManualTextInputDialog() {
        val dialogView = LayoutInflater.from(requireContext())
            .inflate(R.layout.dialog_edit_text, null)
        val textInput = dialogView.findViewById<EditText>(R.id.et_text_input)

        AlertDialog.Builder(requireContext())
            .setTitle("Manual Text Input")
            .setView(dialogView)
            .setPositiveButton(getString(R.string.ok)) { _, _ ->
                val text = textInput.text.toString().trim()
                if (text.isNotBlank()) {
                    processBulkText(text)
                } else {
                    requireContext().showToast("No text entered")
                }
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    private fun processBulkText(text: String) {
        val sentences = text.split("\n")
            .map { it.trim() }
            .filter { it.isNotBlank() && it.length > 1 }

        if (sentences.isNotEmpty()) {
            viewModel.addSentences(args.listId, sentences)
            requireContext().showToast("Added ${sentences.size} sentence(s)")
        } else {
            requireContext().showToast("No valid sentences found")
        }
    }

    private fun launchGallery() {
        galleryLauncher.launch("image/*")
    }

    private fun checkCameraPermissionAndLaunch() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                launchCamera()
            }
            else -> {
                permissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    private fun launchCamera() {
        val photoFile = try {
            createImageFile()
        } catch (ex: IOException) {
            requireContext().showToast("Error creating image file")
            return
        }

        currentPhotoPath = photoFile.absolutePath

        val photoUri = FileProvider.getUriForFile(
            requireContext(),
            "${requireContext().packageName}.fileprovider",
            photoFile
        )

        cameraLauncher.launch(photoUri)
    }

    private fun createImageFile(): File {
        val storageDir = requireContext().cacheDir
        return File.createTempFile(
            "OCR_${System.currentTimeMillis()}_",
            ".jpg",
            storageDir
        )
    }

    private fun processImageWithOCR(uri: Uri) {
        try {
            requireContext().showToast("Loading image...")

            // Load bitmap from URI to handle both camera and gallery sources
            val originalBitmap = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                val source = android.graphics.ImageDecoder.createSource(requireContext().contentResolver, uri)
                // Force software bitmap to allow manipulation
                android.graphics.ImageDecoder.decodeBitmap(source) { decoder, _, _ ->
                    decoder.allocator = android.graphics.ImageDecoder.ALLOCATOR_SOFTWARE
                    decoder.isMutableRequired = true
                }
            } else {
                @Suppress("DEPRECATION")
                MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)
            }

            // Ensure bitmap is mutable and in a config we can work with
            val mutableBitmap = if (originalBitmap.isMutable && originalBitmap.config != null) {
                originalBitmap
            } else {
                originalBitmap.copy(Bitmap.Config.ARGB_8888, true)
            }

            showImageEditDialog(mutableBitmap)

        } catch (e: FileNotFoundException) {
            requireContext().showToast("Error: Image file not found")
        } catch (e: SecurityException) {
            requireContext().showToast("Error: No permission to read image")
        } catch (e: Exception) {
            requireContext().showToast("Error loading image: ${e.javaClass.simpleName} - ${e.message}")
        }
    }

    private fun showImageEditDialog(originalBitmap: Bitmap) {
        val dialogView = LayoutInflater.from(requireContext())
            .inflate(R.layout.dialog_edit_image, null)

        val cropImageView = dialogView.findViewById<com.example.danishspelling.presentation.widget.CropImageView>(R.id.crop_image_view)
        val brightnessSeekBar = dialogView.findViewById<SeekBar>(R.id.sb_brightness)
        val contrastSeekBar = dialogView.findViewById<SeekBar>(R.id.sb_contrast)
        val resetButton = dialogView.findViewById<com.google.android.material.button.MaterialButton>(R.id.btn_reset)
        val processButton = dialogView.findViewById<com.google.android.material.button.MaterialButton>(R.id.btn_process)

        var currentBitmap = originalBitmap.copy(originalBitmap.config, true)
        cropImageView.setBitmap(currentBitmap)

        val updateImage = {
            val brightness = (brightnessSeekBar.progress - 100) / 100f
            val contrast = contrastSeekBar.progress / 100f
            currentBitmap = applyColorAdjustments(originalBitmap, brightness, contrast)
            cropImageView.setBitmap(currentBitmap)
        }

        brightnessSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) updateImage()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        contrastSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) updateImage()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setCancelable(false)
            .create()

        resetButton.setOnClickListener {
            brightnessSeekBar.progress = 100
            contrastSeekBar.progress = 100
            currentBitmap = originalBitmap.copy(originalBitmap.config, true)
            cropImageView.setBitmap(currentBitmap)
            cropImageView.resetCropRect()
        }

        processButton.setOnClickListener {
            dialog.dismiss()
            val cropRect = cropImageView.getCropRect()
            val bitmapToProcess = if (cropRect.width() > 0 && cropRect.height() > 0) {
                Bitmap.createBitmap(
                    currentBitmap,
                    cropRect.left,
                    cropRect.top,
                    cropRect.width(),
                    cropRect.height()
                )
            } else {
                currentBitmap
            }
            performOCR(bitmapToProcess)
        }

        dialog.show()
    }

    private fun applyColorAdjustments(bitmap: Bitmap, brightness: Float, contrast: Float): Bitmap {
        val adjustedBitmap = bitmap.copy(bitmap.config, true)
        val canvas = android.graphics.Canvas(adjustedBitmap)
        val paint = android.graphics.Paint()

        val colorMatrix = android.graphics.ColorMatrix()

        // Apply brightness
        colorMatrix.set(floatArrayOf(
            1f, 0f, 0f, 0f, brightness * 255,
            0f, 1f, 0f, 0f, brightness * 255,
            0f, 0f, 1f, 0f, brightness * 255,
            0f, 0f, 0f, 1f, 0f
        ))

        // Apply contrast
        val contrastMatrix = android.graphics.ColorMatrix()
        val scale = contrast
        val translate = (1f - scale) * 127.5f
        contrastMatrix.set(floatArrayOf(
            scale, 0f, 0f, 0f, translate,
            0f, scale, 0f, 0f, translate,
            0f, 0f, scale, 0f, translate,
            0f, 0f, 0f, 1f, 0f
        ))

        colorMatrix.postConcat(contrastMatrix)

        paint.colorFilter = android.graphics.ColorMatrixColorFilter(colorMatrix)
        canvas.drawBitmap(bitmap, 0f, 0f, paint)

        return adjustedBitmap
    }

    /**
     * Preprocess bitmap for better OCR accuracy:
     * - Upscale small images so ML Kit has more detail to work with
     */
    private fun preprocessForOCR(bitmap: Bitmap): Bitmap {
        val minDimension = 1500
        return if (bitmap.width < minDimension && bitmap.height < minDimension) {
            val scale = minDimension.toFloat() / minOf(bitmap.width, bitmap.height)
            Bitmap.createScaledBitmap(
                bitmap,
                (bitmap.width * scale).toInt(),
                (bitmap.height * scale).toInt(),
                true
            )
        } else {
            bitmap
        }
    }

    /**
     * Fix common OCR misrecognitions of Danish special characters.
     * Safe substitutions: characters that don't exist in Danish text
     * but are visually similar to æ, ø, å.
     */
    private fun fixDanishCharacters(text: String): String {
        return text
            // ö → ø (German/Swedish, not used in Danish)
            .replace('ö', 'ø')
            .replace('Ö', 'Ø')
            // ä → æ (German/Swedish, not used in Danish)
            .replace('ä', 'æ')
            .replace('Ä', 'Æ')
            // à/á/â → å (accented a's are not Danish, likely misread å)
            .replace('à', 'å')
            .replace('á', 'å')
            .replace('â', 'å')
            .replace('À', 'Å')
            .replace('Á', 'Å')
            .replace('Â', 'Å')
            // œ → æ (French ligature, likely misread æ)
            .replace('œ', 'æ')
            .replace('Œ', 'Æ')
            // ó/ò/ô → ø (accented o's are not Danish, likely misread ø)
            .replace('ó', 'ø')
            .replace('ò', 'ø')
            .replace('ô', 'ø')
            .replace('Ó', 'Ø')
            .replace('Ò', 'Ø')
            .replace('Ô', 'Ø')
    }

    private fun performOCR(bitmap: Bitmap) {
        requireContext().showToast(getString(R.string.ocr_processing))

        val preprocessed = preprocessForOCR(bitmap)

        ocrEngine.recognizeText(
            bitmap = preprocessed,
            onSuccess = { extractedText ->
                if (extractedText.isNotBlank()) {
                    val reformatted = fixDanishCharacters(extractedText)
                        .replace("\n", " ")
                        .split(".")
                        .map { it.trim() }
                        .filter { it.isNotBlank() }
                        .joinToString("\n") { "$it." }
                    showTextEditDialog(reformatted, autoSpellCheck = true)
                } else {
                    requireContext().showToast(getString(R.string.ocr_no_text))
                }
            },
            onFailure = { e ->
                requireContext().showToast("OCR failed: ${e.message}")
            }
        )
    }

    private fun showTextEditDialog(extractedText: String, autoSpellCheck: Boolean = false) {
        val dialogView = LayoutInflater.from(requireContext())
            .inflate(R.layout.dialog_edit_text, null)
        val textInput = dialogView.findViewById<EditText>(R.id.et_text_input)
        val btnSpellCheck = dialogView.findViewById<com.google.android.material.button.MaterialButton>(R.id.btn_spell_check)
        val pbSpellCheck = dialogView.findViewById<android.widget.ProgressBar>(R.id.pb_spell_check)
        val tvResults = dialogView.findViewById<android.widget.TextView>(R.id.tv_spell_check_results)

        textInput.setText(extractedText)

        fun runSpellCheck() {
            val text = textInput.text.toString()
            if (text.isBlank()) {
                requireContext().showToast("No text to check")
                return
            }
            btnSpellCheck.isEnabled = false
            pbSpellCheck.visibility = View.VISIBLE
            tvResults.visibility = View.GONE
            SpellCheckHighlighter.clearHighlights(textInput)

            viewLifecycleOwner.lifecycleScope.launch {
                when (val outcome = spellCheckService.checkText(text)) {
                    is SpellCheckOutcome.Success -> {
                        pbSpellCheck.visibility = View.GONE
                        btnSpellCheck.isEnabled = true
                        val results = outcome.results
                        if (results.isNotEmpty()) {
                            SpellCheckHighlighter.applyHighlights(textInput, results)
                            tvResults.text = SpellCheckHighlighter.buildSuggestionsSummary(results)
                        } else {
                            tvResults.text = "No spelling issues found."
                        }
                        tvResults.visibility = View.VISIBLE
                    }
                    is SpellCheckOutcome.Error -> {
                        pbSpellCheck.visibility = View.GONE
                        btnSpellCheck.isEnabled = true
                        tvResults.text = outcome.message
                        tvResults.visibility = View.VISIBLE
                    }
                }
            }
        }

        btnSpellCheck.setOnClickListener { runSpellCheck() }

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Review Extracted Text")
            .setView(dialogView)
            .setPositiveButton(getString(R.string.ok)) { _, _ ->
                val text = textInput.text.toString().trim()
                if (text.isNotBlank()) {
                    processBulkText(text)
                } else {
                    requireContext().showToast("No text to add")
                }
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()

        // Auto-run spell check when dialog is shown after OCR
        if (autoSpellCheck) {
            dialog.setOnShowListener { runSpellCheck() }
            // If already showing, just run it
            runSpellCheck()
        }
    }

    override fun onPause() {
        super.onPause()
        // Generate TTS clips asynchronously when navigating away
        viewModel.generateTTSClipsAsync()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        currentPhotoPath?.let {
            outState.putString(KEY_PHOTO_PATH, it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val KEY_PHOTO_PATH = "photo_path"
    }
}
