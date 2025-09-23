package com.example.cvprofile.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import com.example.cvprofile.R
import com.example.cvprofile.databinding.FragmentProfileBinding
import com.example.cvprofile.model.Experiencia
import com.example.cvprofile.model.Portafolio
import com.example.cvprofile.model.Usuario

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        // 1. Datos de ejemplo
        val usuario = Usuario(
            nombre = "Pedro Pérez",
            correo = "pedro.perez@mail.com",
            foto = R.drawable.ic_person
        )
        val portafolio = obtenerDatosPortafolio()

        // 2. Configurar UI
        configurarUsuario(usuario, portafolio)
        mostrarHabilidades(portafolio)
        mostrarExperiencias(portafolio)

        return view
    }

    private fun configurarUsuario(usuario: Usuario, portafolio: Portafolio) {
        val profileImage: ImageView = binding.profileImage
        val profileName: TextView = binding.profileName
        val profileRole: TextView = binding.profileRole
        val profileInfo: TextView = binding.profileInfo

        profileImage.setImageResource(usuario.foto)
        profileName.text = usuario.nombre
        profileRole.text = portafolio.titulo
        profileInfo.text = portafolio.descripcion
    }

    private fun mostrarHabilidades(portafolio: Portafolio) {
        val skillsContainer: LinearLayout = binding.skillsContainer
        skillsContainer.removeAllViews()

        for (skill in portafolio.habilidades) {
            val skillView = TextView(requireContext()).apply {
                text = skill
                setPadding(16, 8, 16, 8)
                setBackgroundResource(R.drawable.skill_chip)
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply { setMargins(8, 8, 8, 8) }
            }
            skillsContainer.addView(skillView)
        }
    }

    private fun mostrarExperiencias(portafolio: Portafolio) {
        binding.experienciaContainer.removeAllViews()

        for (exp in portafolio.experiencias) {
            val expView = TextView(requireContext()).apply {
                text = HtmlCompat.fromHtml(
                    "<b>${exp.cargo}</b> - ${exp.empresa}<br>${exp.fechaInicio} - ${exp.fechaFin}",
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                )
                textSize = 14f
                setTextColor(resources.getColor(android.R.color.black, null))
            }
            binding.experienciaContainer.addView(expView)
        }
    }

    private fun obtenerDatosPortafolio(): Portafolio {
        return Portafolio(
            titulo = "Desarrollador Frontend",
            descripcion = "Desarrollador Frontend con 5 años de experiencia en React," +
                    " TypeScript y diseño UX/UI. Especializado en crear interfaces web modernas y responsive.",
            habilidades = listOf("React", "TypeScript", "Node.js", "TailwindCSS"),
            experiencias = listOf(
                Experiencia("TechCorp", "Frontend Developer", "2022", "Presente"),
                Experiencia("DesignStudio", "UI/UX Designer", "2020", "2022"),
                Experiencia("PickStudio", "Designer", "2018", "2020")
            ),
            proyectos = emptyList(),
            videos = emptyList(),
            enlaces = emptyList()
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
