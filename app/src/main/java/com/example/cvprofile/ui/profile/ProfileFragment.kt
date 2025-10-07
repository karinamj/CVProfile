package com.example.cvprofile.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.net.toUri
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import com.example.cvprofile.ProfileActivity
import com.example.cvprofile.R
import com.example.cvprofile.data.local.entity.ExperienceEntity
import com.example.cvprofile.data.local.entity.SkillEntity
import com.example.cvprofile.data.local.entity.UserEntity
import com.example.cvprofile.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        observeData()

        binding.btnEditProfile.setOnClickListener {
            val intent = Intent(requireContext(), ProfileActivity::class.java)
            startActivity(intent)
        }

        viewModel.loadUser()
        return binding.root
    }

    private fun observeData() {
        viewModel.user.observe(viewLifecycleOwner) { user ->
            user?.let {
                showUserInfo(it)
            }
        }

        viewModel.skills.observe(viewLifecycleOwner) { skills ->
            showSkills(skills)
        }

        viewModel.experiences.observe(viewLifecycleOwner) { experiences ->
            showExperiences(experiences)
        }
    }

    private fun showUserInfo(user: UserEntity){
        binding.apply {
            profileName.text = user.name ?: ""
            profileRole.text = user.profession ?: ""
            profileInfo.text = user.description ?: ""

            if (!user.profileImagePath.isNullOrEmpty()) {
                profileImage.load(user.profileImagePath!!.toUri()) {
                    placeholder(R.drawable.ic_person)
                    error(R.drawable.ic_person)
                }
            } else {
                profileImage.setImageResource(R.drawable.ic_person)
            }
        }
    }

    private fun showSkills(skills: List<SkillEntity>) {
        val skillsContainer: LinearLayout = binding.skillsContainer
        skillsContainer.removeAllViews()

        if (skills.isEmpty()) {
            val emptyText = TextView(requireContext()).apply {
                text = "Aún no se han agregado habilidades."
                setPadding(16, 8, 16, 8)
            }
            skillsContainer.addView(emptyText)
            return
        }

        for (skill in skills) {
            val skillView = TextView(requireContext()).apply {
                text = skill.name
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

    private fun showExperiences(experiencias: List<ExperienceEntity>) {
        binding.experienciaContainer.removeAllViews()
        if (experiencias.isEmpty()) {
            val emptyText = TextView(requireContext()).apply {
                text = "Aún no se han agregado experiencias."
                setPadding(16, 8, 16, 8)
            }
            binding.experienciaContainer.addView(emptyText)
            return
        }

        for (exp in experiencias) {
            val expView = TextView(requireContext()).apply {
                text = HtmlCompat.fromHtml(
                    "<b>${exp.position}</b> - ${exp.companyName}<br>${exp.startDate} - ${exp.endDate ?: "Present"}<br>${exp.description ?: ""}",
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                )
                textSize = 14f
                setTextColor(resources.getColor(android.R.color.black, null))
                setPadding(8, 8, 8, 8)
            }
            binding.experienciaContainer.addView(expView)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadUser()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
