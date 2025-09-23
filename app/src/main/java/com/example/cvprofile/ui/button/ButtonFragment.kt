package com.example.cvprofile.ui.button

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.cvprofile.R
import com.example.cvprofile.model.Contacto

class ButtonFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_button, container, false)

        val containerBotones = view.findViewById<LinearLayout>(R.id.containerBotones)
        val containerInfo = view.findViewById<LinearLayout>(R.id.containerInfo)

        // Lista de acciones
        val acciones = listOf(
            Triple("Descargar HV", R.drawable.ic_download) {
                abrirEnlace("https://tusitio.com/hv.pdf")
            },
            Triple("Enviar Email", R.drawable.ic_email) {
                abrirEnlace("mailto:pedro_perez@email.com")
            },
            Triple("Llamar Ahora", R.drawable.ic_phone) {
                abrirEnlace("tel:+573101234567")
            },
            Triple("Conectar en LinkedIn", R.drawable.ic_link) {
                abrirEnlace("https://www.linkedin.com/in/tuusuario/")
            }
        )

        // Crear botones dinámicos
        for ((titulo, icono, accion) in acciones) {
            val button = Button(requireContext()).apply {
                text = titulo
                isAllCaps = false
                setBackgroundResource(R.drawable.btn_outlined)
                setCompoundDrawablesWithIntrinsicBounds(icono, 0, 0, 0)
                compoundDrawablePadding = 16
                setOnClickListener { accion() }
            }
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(0, 12, 0, 12)
            button.layoutParams = params
            containerBotones.addView(button)
        }

        // Información de contacto
        val contacto = Contacto(
            correo = "pedro_perez@email.com",
            telefono = "+57 310 1234567",
            direccion = "Santiago de Cali, Colombia",
            disponibilidad = "Disponible para proyectos"
        )

        val infoList = listOf(
            Pair(R.drawable.ic_email, contacto.correo),
            Pair(R.drawable.ic_phone, contacto.telefono),
            Pair(R.drawable.ic_location, contacto.direccion),
            Pair(R.drawable.ic_check, contacto.disponibilidad)
        )

        for ((icono, texto) in infoList) {
            val textView = TextView(requireContext()).apply {
                text = texto
                setCompoundDrawablesWithIntrinsicBounds(icono, 0, 0, 0)
                compoundDrawablePadding = 16
                setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            }
            containerInfo.addView(textView)
        }

        return view
    }

    private fun abrirEnlace(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}
