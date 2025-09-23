package com.example.cvprofile.ui.web

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.example.cvprofile.R
import com.example.cvprofile.model.EnlaceProfesional

class WebFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_web, container, false)

        val enlacesContainer = view.findViewById<LinearLayout>(R.id.enlacesContainer)

        // Lista dinámica de enlaces
        val enlaces = listOf(
            EnlaceProfesional(
                "Sitio Web Personal",
                "https://tusitioweb.com",
                android.R.drawable.ic_menu_view
            ),
            EnlaceProfesional(
                "Portfolio en LinkedIn",
                "https://www.linkedin.com/in/tuusuario/",
                android.R.drawable.ic_menu_share
            ),
            EnlaceProfesional(
                "Proyectos en GitHub",
                "https://github.com/tuusuario",
                android.R.drawable.ic_menu_manage
            )
        )

        // Crear botones dinámicos
        for (enlace in enlaces) {
            val button = Button(requireContext()).apply {
                text = " ${enlace.titulo}"
                isAllCaps = false
                setCompoundDrawablesWithIntrinsicBounds(0, 0, enlace.logo, 0)
                setBackgroundResource(R.drawable.btn_outlined)
                setOnClickListener {
                    abrirEnlace(enlace.url)
                }
            }

            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(0, 12, 0, 0)
            button.layoutParams = params

            enlacesContainer.addView(button)
        }

        return view
    }

    private fun abrirEnlace(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, url.toUri())
        startActivity(intent)
    }
}
