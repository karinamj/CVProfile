package com.example.cvprofile.ui.edit.skill

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cvprofile.R
import com.example.cvprofile.adapter.SkillAdapter
import com.example.cvprofile.data.local.entity.SkillEntity
import com.example.cvprofile.databinding.FragmentSkillBinding

class SkillFragment : Fragment() {

    private var _binding: FragmentSkillBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SkillViewModel by viewModels()
    private lateinit var adapter: SkillAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSkillBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializa el RecyclerView
        adapter = SkillAdapter { skill ->
            viewModel.deleteSkill(skill)
            Toast.makeText(requireContext(), "Skill deleted", Toast.LENGTH_SHORT).show()
        }

        binding.rvSkills.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSkills.adapter = adapter

        // Observa las skills almacenadas
        viewModel.skills.observe(viewLifecycleOwner) { skills ->
            if (skills.isNullOrEmpty()) {
                binding.rvSkills.visibility = View.GONE
            } else {
                binding.rvSkills.visibility = View.VISIBLE
                adapter.submitList(skills)
            }
        }

        // Acción del botón para agregar nueva skill
        binding.btnAddSkill.setOnClickListener {
            val skillName = binding.etSkillName.text.toString().trim()
            if (skillName.isNotEmpty()) {
                viewModel.addSkill(skillName)
                binding.etSkillName.text?.clear()
                Toast.makeText(requireContext(),
                    "Habilidad agregada",
                    Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(),
                    "Por favor ingrese una habilidad",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}