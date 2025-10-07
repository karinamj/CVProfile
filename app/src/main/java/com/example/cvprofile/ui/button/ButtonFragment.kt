package com.example.cvprofile.ui.button

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.cvprofile.databinding.FragmentButtonBinding
import com.example.cvprofile.ui.profile.ProfileViewModel
import java.io.File

class ButtonFragment : Fragment() {

    private var _binding: FragmentButtonBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentButtonBinding.inflate(inflater, container, false)
        setupObservers()
        setupStaticButtons()
        return binding.root
    }

    private fun setupObservers() {
        viewModel.user.observe(viewLifecycleOwner) { user ->
            binding.tvEmail.text = if (user?.email.isNullOrBlank()) "Correo no disponible" else user?.email
            binding.tvPhone.text = if (user?.phone.isNullOrBlank()) "Teléfono no disponible" else user?.phone
            binding.tvAddress.text = if (user?.address.isNullOrBlank()) "Dirección no disponible" else user?.address
            binding.tvAvailability.text = if (user?.availability.isNullOrBlank()) "Disponibilidad no especificada" else user?.availability

            binding.btnDownloadCv.setOnClickListener {
                if (!user?.resumePath.isNullOrEmpty()) downloadResume()
                else showUnavailable()
            }
            binding.btnSendEmail.setOnClickListener {
                if (!user?.email.isNullOrEmpty()) sendEmail()
                else showUnavailable()
            }
            binding.btnCall.setOnClickListener {
                if (!user?.phone.isNullOrEmpty()) callPhone()
                else showUnavailable()
            }
            binding.btnLinkedin.setOnClickListener {
                if (!user?.linkedin.isNullOrEmpty()) openLinkedin()
                else showUnavailable()
            }
        }

        viewModel.loadUser()
    }

    private fun setupStaticButtons() {
        binding.btnDownloadCv.isEnabled = true
        binding.btnSendEmail.isEnabled = true
        binding.btnCall.isEnabled = true
        binding.btnLinkedin.isEnabled = true
    }

    private fun downloadResume() {
        val resumePath = viewModel.user.value?.resumePath

        if (resumePath.isNullOrBlank()) {
            showMessage("No hay hoja de vida disponible.")
            return
        }

        val file = File(resumePath)
        if (!file.exists()) {
            showMessage("El archivo no existe en el dispositivo.")
            return
        }

        try {
            val uri = FileProvider.getUriForFile(
                requireContext(),
                "${requireContext().packageName}.provider",
                file
            )

            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(uri, "application/pdf")
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            if (intent.resolveActivity(requireContext().packageManager) != null) {
                startActivity(intent)
            } else {
                showMessage("No se encontró una app para abrir el PDF.")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            showMessage("No se pudo abrir la hoja de vida.")
        }
    }


    private fun sendEmail() {
        val email = viewModel.user.value?.email
        if (!email.isNullOrBlank()) {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:$email")
            }
            startActivity(intent)
        } else showMessage("Correo no disponible.")
    }

    private fun callPhone() {
        val phone = viewModel.user.value?.phone
        if (!phone.isNullOrBlank()) {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$phone")
            }
            startActivity(intent)
        } else showMessage("Teléfono no disponible.")
    }

    private fun openLinkedin() {
        val link = viewModel.user.value?.linkedin
        if (!link.isNullOrBlank()) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            startActivity(intent)
        } else showMessage("Perfil de LinkedIn no disponible.")
    }

    private fun showMessage(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    private fun showUnavailable() {
        Toast.makeText(requireContext(), "Información no disponible", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
