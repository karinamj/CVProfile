package com.example.cvprofile.ui.edit.user

import android.R
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.cvprofile.data.local.entity.UserEntity
import com.example.cvprofile.databinding.FragmentUserEditBinding
import com.example.cvprofile.util.SimpleTextWatcher
import androidx.core.net.toUri
import com.example.cvprofile.util.FileUtils
import java.io.File

class UserEditFragment : Fragment() {

    private lateinit var binding: FragmentUserEditBinding
    private lateinit var userEditViewModel: UserEditViewModel
    private var originalUser: UserEntity? = null
    private var profileImagePath: String? = null
    private var resumeFilePath: String? = null

    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            // Copia a almacenamiento interno
            val path = FileUtils.copyUriToInternalStorage(it, requireContext(), "profile.jpg")
            path?.let { p ->
                profileImagePath = p
                binding.imgProfile.setImageURI(File(p).toUri())
                // Guardar inmediatamente
                val user = originalUser?.copy(profileImagePath = p)
                    ?: UserEntity(profileImagePath = p, name = "", email = "", phone = "", address = "", availability = null, linkedin = "", github = "", website = "", resumePath = null, profession = "", description = "")
                saveUser(user)
            }
        }
    }

    private val pickResume = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            val path = FileUtils.copyUriToInternalStorage(it, requireContext(),"curriculum.pdf")
            path?.let { p ->
                resumeFilePath = p
                binding.btnUploadCV.text = "Hoja de vida cargada"
                val user = originalUser?.copy(resumePath = p)
                    ?: UserEntity(resumePath = p, name = "", email = "", phone = "", address = "", availability = null, linkedin = "", github = "", website = "", profileImagePath = null, profession = "", description = "")
                saveUser(user)
                Toast.makeText(requireContext(), "Hoja de vida cargada", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserEditBinding.inflate(inflater, container, false)
        userEditViewModel = ViewModelProvider(this)[UserEditViewModel::class.java]

        setupStatusDropdown();

        setupChangeListeners()

        binding.btnUploadPhoto.setOnClickListener {
            pickImage.launch("image/*")
        }

        binding.btnUploadCV.setOnClickListener {
            pickResume.launch("application/pdf")
        }

        binding.btnSaveUser.setOnClickListener {
            val user = UserEntity(
                id = originalUser?.id ?: 0,
                name = binding.etName.text.toString(),
                email = binding.etEmail.text.toString(),
                phone = binding.etPhone.text.toString(),
                address = binding.etAddress.text.toString(),
                availability = binding.etStatus.text.toString(),
                linkedin = binding.etLinkedin.text.toString(),
                github = binding.etGithub.text.toString(),
                website = binding.etWebsite.text.toString(),
                resumePath = resumeFilePath,
                profession = binding.etOccupation.text.toString(),
                description = binding.etDescription.text.toString(),
                profileImagePath = profileImagePath
            )

            saveUser(user)

            Toast.makeText(requireContext(), "Datos guardados", Toast.LENGTH_SHORT).show()
            binding.btnSaveUser.isEnabled = false
        }

        getUser(userEditViewModel)

        return binding.root
    }

    private fun setupStatusDropdown() {
        val items = listOf("Disponible", "Ocupado", "En entrevista", "No disponible")
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.simple_dropdown_item_1line,
            items
        )
        binding.etStatus.setAdapter(adapter)
        binding.etStatus.keyListener = null
    }


    fun saveUser(user: UserEntity){
        userEditViewModel.saveUser(user)
        originalUser = user
        Log.d("DB", "Usuario guardado: $user")
    }

    fun getUser(usuarioViewModel: UserEditViewModel){
        usuarioViewModel.getUser().observe(viewLifecycleOwner) { user ->
            user?.let {
                originalUser = it
                binding.etName.setText(it.name)
                binding.etEmail.setText(it.email)
                binding.etPhone.setText(it.phone)
                binding.etAddress.setText(it.address)
                binding.etStatus.setText(it.availability ?: "", false)
                binding.etLinkedin.setText(it.linkedin)
                binding.etGithub.setText(it.github)
                binding.etWebsite.setText(it.website)
                binding.etOccupation.setText(it.profession)
                binding.etDescription.setText(it.description)
                profileImagePath = it.profileImagePath
                resumeFilePath = it.resumePath

                it.profileImagePath?.let { path ->
                    val file = File(path)
                    if (file.exists()) {
                        binding.imgProfile.setImageURI(file.toUri())
                    }
                }

                it.resumePath?.let { path ->
                    binding.btnUploadCV.text = "Hoja de vida cargada"
                }

                // Deshabilitamos el botón inicialmente
                binding.btnSaveUser.isEnabled = false
            }
        }
    }

    private fun setupChangeListeners() {
        val watcher = SimpleTextWatcher {
            binding.btnSaveUser.isEnabled = hasChanges()
        }

        binding.etName.addTextChangedListener(watcher)
        binding.etEmail.addTextChangedListener(watcher)
        binding.etPhone.addTextChangedListener(watcher)
        binding.etAddress.addTextChangedListener(watcher)
        binding.etStatus.addTextChangedListener(watcher)
        binding.etLinkedin.addTextChangedListener(watcher)
        binding.etGithub.addTextChangedListener(watcher)
        binding.etWebsite.addTextChangedListener(watcher)
        binding.etOccupation.addTextChangedListener(watcher)
        binding.etDescription.addTextChangedListener(watcher)

    }

    private fun hasChanges(): Boolean {
        val currentUser = UserEntity(
            id = originalUser?.id ?: 0,
            name = binding.etName.text.toString(),
            email = binding.etEmail.text.toString(),
            phone = binding.etPhone.text.toString(),
            address = binding.etAddress.text.toString(),
            availability = binding.etStatus.text.toString(),
            linkedin = binding.etLinkedin.text.toString(),
            github = binding.etGithub.text.toString(),
            website = binding.etWebsite.text.toString(),
            resumePath = null,
            profession = binding.etOccupation.text.toString(),
            description = binding.etDescription.text.toString(),
            profileImagePath = null
        )

        return currentUser != originalUser
    }
}