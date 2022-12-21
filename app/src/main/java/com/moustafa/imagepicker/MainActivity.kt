package com.moustafa.imagepicker

import android.os.Bundle
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.moustafa.imagepicker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val MAX_ITEMS = 5 // max number of allowed items to be selected

    private val singleImagePicker =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            // Callback is invoked after the user selects a media item or closes the
            // photo picker.
            uri?.let {
                val selectedImages = StringBuilder()
                selectedImages.append(uri.toString())
                binding.selectedImagesUri.text = selectedImages
            }
        }

    private val multipleImagePicker =
        registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(MAX_ITEMS)) { selectedUris ->
            // Callback is invoked after the user selects media items or closes the
            if (selectedUris.isNotEmpty()) {
                val selectedImages = StringBuilder()
                for (uri in selectedUris) {
                    selectedImages.append(uri.toString()).append("\n")
                }
                binding.selectedImagesUri.text = selectedImages
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setButtonClicks()
    }

    private fun setButtonClicks() {
        binding.singleImagePick.setOnClickListener {
            singleImagePicker.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }

        /**
         * you can select only 5 images because of value for MAX_ITEMS
         * */
        binding.multiImagePick.setOnClickListener {
            multipleImagePicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

    }

}