package com.example.cvprofile.ui.photo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.cvprofile.R
import com.example.cvprofile.databinding.FragmentPhotosBinding
import com.example.cvprofile.model.Proyecto

class PhotoFragment : Fragment() {

    private var _binding: FragmentPhotosBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhotosBinding.inflate(inflater, container, false)

        val proyectos = obtenerProyectos()

        // Cargar proyectos dinámicamente
        for (proyecto in proyectos) {
            val cardView = layoutInflater.inflate(R.layout.item_proyecto, binding.proyectosContainer, false)

            val imagen: ImageView = cardView.findViewById(R.id.proyectoImagen)
            val titulo: TextView = cardView.findViewById(R.id.proyectoTitulo)
            val descripcion: TextView = cardView.findViewById(R.id.proyectoDescripcion)

            imagen.setImageResource(proyecto.imagen)
            titulo.text = proyecto.titulo
            descripcion.text = proyecto.descripcion

            binding.proyectosContainer.addView(cardView)
        }

        return binding.root
    }

    private fun obtenerProyectos(): List<Proyecto> {
        return listOf(
            Proyecto(
                "E-commerce Platform",
                "Plataforma completa de comercio electrónico desarrollada con React y Node.js",
                R.drawable.proyecto1_background
            ),
            Proyecto(
                "Task Management App",
                "Aplicación móvil para gestión de tareas con React Native",
                R.drawable.proyecto2_background
            ),
            Proyecto(
                "Proyecto 3",
                "Otro proyecto interesante",
                R.drawable.proyecto3_background
            ),
            Proyecto(
                "Proyecto 4",
                "Un cuarto proyecto para mostrar",
                R.drawable.proyecto4_background
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
