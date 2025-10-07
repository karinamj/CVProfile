package com.example.cvprofile.ui.edit.video

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.cvprofile.data.local.entity.VideoEntity
import com.example.cvprofile.databinding.FragmentVideoEditBinding
import java.io.File

class VideoEditFragment : Fragment() {

    private var _binding: FragmentVideoEditBinding? = null
    private val binding get() = _binding!!
    private val viewModel: VideoViewModel by viewModels()

    private val SELECT_VIDEO_REQUEST = 101
    private var selectedVideoUri: Uri? = null
    private var mediaController: MediaController? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVideoEditBinding.inflate(inflater, container, false)

        setupSelectVideo()
        setupSaveVideo()
        observeVideo()
        setupTextChangeListener()

        updateSaveButtonState()

        return binding.root
    }

    private fun setupSelectVideo() {
        binding.btnSelectVideo.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "video/*"
            }
            startActivityForResult(intent, SELECT_VIDEO_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SELECT_VIDEO_REQUEST && resultCode == Activity.RESULT_OK) {
            selectedVideoUri = data?.data
            if (selectedVideoUri != null) {
                binding.btnSelectVideo.text = "Video seleccionado"
                Toast.makeText(requireContext(), "Video seleccionado", Toast.LENGTH_SHORT).show()
            } else {
                binding.btnSelectVideo.text = "Seleccionar Video"
            }
            updateSaveButtonState()
        }
    }

    private fun setupSaveVideo() {
        binding.btnSaveVideo.setOnClickListener {
            val title = binding.etVideoTitle.text.toString().trim()
            val description = binding.etVideoDescription.text.toString().trim()

            if (title.isEmpty() || selectedVideoUri == null) return@setOnClickListener

            // Guardar el video en la carpeta local
            val inputStream = requireContext().contentResolver.openInputStream(selectedVideoUri!!)
            val file = File(requireContext().filesDir, "video.mp4")
            inputStream?.use { input ->
                file.outputStream().use { output ->
                    input.copyTo(output)
                }
            }

            val video = VideoEntity(
                id = 1, // Reemplaza el anterior
                title = title,
                url = file.absolutePath,
                description = description
            )

            viewModel.insertVideo(video)
            viewModel.loadVideo()

            // Resetear campos y botones
            binding.etVideoTitle.text?.clear()
            binding.etVideoDescription.text?.clear()
            selectedVideoUri = null
            binding.btnSelectVideo.text = "Seleccionar Video"

            updateSaveButtonState()

            Toast.makeText(requireContext(), "Video guardado correctamente.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupTextChangeListener() {
        binding.etVideoTitle.addTextChangedListener {
            updateSaveButtonState()
        }
    }

    private fun updateSaveButtonState() {
        // Solo habilita el guardar si hay video seleccionado y título no vacío
        val isEnabled = !binding.etVideoTitle.text.isNullOrBlank() && selectedVideoUri != null
        binding.btnSaveVideo.isEnabled = isEnabled
    }

    private fun observeVideo() {
        viewModel.video.observe(viewLifecycleOwner) { video ->
            if (video != null) {
                binding.tvVideoTitle.text = video.title ?: "Sin título"
                binding.tvVideoDescription.text = video.description ?: ""

                if (mediaController == null) {
                    mediaController = MediaController(requireContext())
                }

                binding.videoView.setMediaController(mediaController)

                binding.btnPlayVideo.setOnClickListener {
                    if (!video.url.isNullOrBlank()) {
                        binding.videoView.setVideoPath(video.url)
                        binding.videoView.requestFocus()
                        binding.videoView.start()
                    } else {
                        Toast.makeText(requireContext(), "No hay video disponible.", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                binding.tvVideoTitle.text = ""
                binding.tvVideoDescription.text = ""
            }
        }

        viewModel.loadVideo()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediaController = null
        _binding = null
    }
}
