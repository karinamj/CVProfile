package com.example.cvprofile.ui.edit.experience

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cvprofile.adapter.ExperienceAdapter
import com.example.cvprofile.databinding.FragmentExperienceBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ExperienceFragment : Fragment() {

    private lateinit var binding: FragmentExperienceBinding
    private val viewModel: ExperienceViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExperienceBinding.inflate(inflater, container, false)

        val adapter = ExperienceAdapter { experience ->
            viewModel.deleteExperience(experience)
        }

        binding.recyclerExperience.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerExperience.adapter = adapter

        binding.btnAddExperience.setOnClickListener {
            val company = binding.etCompanyName.text.toString()
            val position = binding.etPosition.text.toString()
            val start = binding.etStartDate.text.toString()
            val end = binding.etEndDate.text.toString().ifEmpty { null }
            val desc = binding.etDescription.text.toString().ifEmpty { null }

            if (company.isNotBlank() && position.isNotBlank() && start.isNotBlank()) {
                viewModel.addExperience(company, position, start, end, desc)
                binding.etCompanyName.text.clear()
                binding.etPosition.text.clear()
                binding.etStartDate.text.clear()
                binding.etEndDate.text.clear()
                binding.etDescription.text.clear()
            }
        }

        viewModel.experiences.observe(viewLifecycleOwner) { experience -> adapter.setData(experience) }

        return binding.root
    }
}