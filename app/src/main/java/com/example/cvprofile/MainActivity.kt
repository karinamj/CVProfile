package com.example.cvprofile

import android.os.Bundle
import android.view.Menu
import android.widget.ImageButton
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.cvprofile.databinding.ActivityMainBinding
import com.example.cvprofile.ui.button.ButtonFragment
import com.example.cvprofile.ui.photo.PhotoFragment
import com.example.cvprofile.ui.profile.ProfileFragment
import com.example.cvprofile.ui.video.VideoFragment
import com.example.cvprofile.ui.web.WebFragment

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Botones del menú lateral
        // Botones del menú lateral
        findViewById<TextView>(R.id.btnPerfil).setOnClickListener {
            abrirFragment(ProfileFragment())
        }
        findViewById<TextView>(R.id.btnFotos).setOnClickListener {
            abrirFragment(PhotoFragment())
        }
        findViewById<TextView>(R.id.btnVideo).setOnClickListener {
            abrirFragment(VideoFragment())
        }
        findViewById<TextView>(R.id.btnWeb).setOnClickListener {
            abrirFragment(WebFragment())
        }
        findViewById<TextView>(R.id.btnBotones).setOnClickListener {
            abrirFragment(ButtonFragment())
        }


        // Fragmento inicial
        if (savedInstanceState == null) {
            abrirFragment(ProfileFragment())
        }
    }

    private fun abrirFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.contenedorFragmentos, fragment)
            .commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}