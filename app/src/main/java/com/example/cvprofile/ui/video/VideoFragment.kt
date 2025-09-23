package com.example.cvprofile.ui.video

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.example.cvprofile.R
import com.example.cvprofile.databinding.FragmentVideoBinding
import com.example.cvprofile.model.Video

class VideoFragment : Fragment() {
    private var _binding: FragmentVideoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVideoBinding.inflate(inflater, container, false)

        val videos = obtenerVideos()

        // Generar lista dinámica
        for (video in videos) {
            val cardView = layoutInflater.inflate(R.layout.item_video, binding.videosContainer, false)

            val titulo = cardView.findViewById<TextView>(R.id.videoTitulo)
            val descripcion = cardView.findViewById<TextView>(R.id.videoDescripcion)
            val btnReproducir = cardView.findViewById<Button>(R.id.btnReproducirVideo)

            titulo.text = video.titulo
            descripcion.text = video.descripcion

            btnReproducir.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, video.url.toUri())
                startActivity(intent)
            }

            binding.videosContainer.addView(cardView)
        }

        return binding.root
    }

    private fun obtenerVideos(): List<Video> {
        return listOf(
            Video(
                titulo = "Presentación Personal",
                url = "https://www.youtube.com/watch?v=H711PVXy2T4",
                descripcion = """
                    Este video presenta una introducción detallada sobre mi perfil profesional, 
                    destacando mi formación académica, experiencia laboral y los valores que guían mi trabajo. 
                    También incluye una demostración de algunos de mis proyectos más relevantes, 
                    mostrando habilidades en desarrollo de software, gestión de proyectos y resolución de problemas.  
            
                    Está dirigido a reclutadores, colegas y posibles clientes que deseen conocer mi forma de trabajar, 
                    las tecnologías que manejo y cómo aporto valor en equipos multidisciplinarios. 
                    Además, comparto ejemplos prácticos de logros alcanzados, 
                    así como una visión de mis objetivos a futuro en el ámbito profesional.  
                """.trimIndent()
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
