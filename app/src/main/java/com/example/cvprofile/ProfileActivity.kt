package com.example.cvprofile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cvprofile.databinding.ActivityProfileBinding
import com.example.cvprofile.util.ProfilePagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = ProfilePagerAdapter(this)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Usuario"
                1 -> "Educación"
                2 -> "Experiencia"
                3 -> "Habilidades"
                4 -> "Proyectos"
                else -> ""
            }
        }.attach()

    }
}