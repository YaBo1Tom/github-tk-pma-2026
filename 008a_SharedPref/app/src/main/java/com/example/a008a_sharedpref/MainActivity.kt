package com.example.a008a_sharedpref

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.a008a_sharedpref.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnDashboard.setOnClickListener { showFragment(DashboardFragment()) }
        binding.btnSettings.setOnClickListener { showFragment(SettingsFragment()) }

        // Pokud se aplikace zapíná poprvé, načteme SettingsFragment
        if (savedInstanceState == null) {
            showFragment(SettingsFragment())
        }
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
