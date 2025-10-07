package com.example.cvprofile.ui.video

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.MediaController
import android.widget.TextView
import android.widget.Toast
import android.widget.VideoView
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.cvprofile.R
import com.example.cvprofile.databinding.FragmentVideoBinding
import com.example.cvprofile.data.local.entity.VideoEntity
import java.io.File

class VideoFragment : Fragment() {

    private var _binding: FragmentVideoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: VideoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVideoBinding.inflate(inflater, container, false)

        loadVideos()

        return binding.root
    }

    private fun loadVideos() {
        // Observa el LiveData del ViewModel
        viewModel.video.observe(viewLifecycleOwner) { video ->
            binding.videosContainer.removeAllViews()

            if (video == null || video.url.isNullOrBlank()) {
                // Si no hay video o URL vacía
                val emptyText = TextView(requireContext()).apply {
                    text = "No hay videos disponibles."
                    setPadding(16, 16, 16, 16)
                }
                binding.videosContainer.addView(emptyText)
            } else {
                // Crear card con título, descripción y botón de reproducción
                val cardView = layoutInflater.inflate(R.layout.item_video, binding.videosContainer, false)
                val title = cardView.findViewById<TextView>(R.id.videoTitle)
                val description = cardView.findViewById<TextView>(R.id.videoDescription)
                val btnPlay = cardView.findViewById<Button>(R.id.btnPlayVideo)
                val videoView = cardView.findViewById<VideoView>(R.id.videoView)

                title.text = video.title ?: "Sin título"
                description.text = video.description ?: ""

                btnPlay.isEnabled = video.url.isNotBlank()
                btnPlay.setOnClickListener {
                    val videoFile = File(requireContext().filesDir, "video.mp4")
                    if (videoFile.exists()) {
                        val videoUri = FileProvider.getUriForFile(
                            requireContext(),
                            "${requireContext().packageName}.provider",
                            videoFile
                        )
                        videoView.setVideoURI(videoUri)
                        val mediaController = MediaController(requireContext())
                        videoView.setMediaController(mediaController)
                        videoView.requestFocus()
                        videoView.start()
                    } else {
                        Toast.makeText(requireContext(), "No hay video disponible", Toast.LENGTH_SHORT).show()
                    }
                }
                binding.videosContainer.addView(cardView)
            }
        }

        // Cargar datos desde la base de datos
        viewModel.loadVideo()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
