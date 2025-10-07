package com.example.cvprofile.ui.web

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.cvprofile.R

class WebFragment : Fragment() {

    private val viewModel: WebViewModel by viewModels()

    private lateinit var btnWebsite: Button
    private lateinit var btnLinkedin: Button
    private lateinit var btnGithub: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_web, container, false)

        btnWebsite = view.findViewById(R.id.btnWebsite)
        btnLinkedin = view.findViewById(R.id.btnLinkedin)
        btnGithub = view.findViewById(R.id.btnGithub)

        setupObservers()

        return view
    }

    private fun setupObservers() {
        viewModel.user.observe(viewLifecycleOwner) { user ->
            btnWebsite.setOnClickListener {
                val website = user?.website?.takeIf { it.isNotBlank() }
                if (website != null) openLink(website)
                else showMessage("No hay sitio web disponible.")
            }

            btnLinkedin.setOnClickListener {
                val linkedin = user?.linkedin?.takeIf { it.isNotBlank() }
                if (linkedin != null) openLink(linkedin)
                else showMessage("No hay perfil de LinkedIn disponible.")
            }

            btnGithub.setOnClickListener {
                val github = user?.github?.takeIf { it.isNotBlank() }
                if (github != null) openLink(github)
                else showMessage("No hay perfil de GitHub disponible.")
            }
        }

        // Cargar usuario al iniciar
        viewModel.loadUser()
    }

    private fun openLink(url: String?) {
        if (url.isNullOrBlank()) {
            showMessage("El enlace no está disponible.")
            return
        }

        // Asegurar que tenga esquema válido
        val fixedUrl = if (!url.startsWith("http://") && !url.startsWith("https://")) {
            "https://$url"
        } else {
            url
        }

        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(fixedUrl))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
            showMessage("No se pudo abrir el enlace.")
        }
    }


    private fun showMessage(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }
}
