package com.example.cvprofile.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cvprofile.ui.edit.experience.ExperienceFragment
import com.example.cvprofile.ui.edit.project.ProjectFragment
import com.example.cvprofile.ui.edit.skill.SkillFragment
import com.example.cvprofile.ui.edit.user.UserEditFragment

class ProfilePagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 3 // número de secciones

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> UserEditFragment()     // sección usuario
            1 -> ExperienceFragment()   // sección experiencia
            2 -> SkillFragment() // sección habilidades
            else -> UserEditFragment()
        }
    }
}