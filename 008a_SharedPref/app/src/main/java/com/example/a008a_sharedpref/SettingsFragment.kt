package com.example.a008a_sharedpref

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.a008a_sharedpref.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private var counter = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Získání SharedPreferences (vytvoří "trezor" s názvem GameProfile)
        val sharedPref = requireActivity().getSharedPreferences("GameProfile", Context.MODE_PRIVATE)

        // 2. Načtení uložených dat ihned po otevření obrazovky
        binding.etNickname.setText(sharedPref.getString("NICKNAME", ""))
        binding.switchHardcoreMode.isChecked = sharedPref.getBoolean("HARDCORE", false)

        // 3. Uložení po kliknutí na tlačítko
        binding.btnSave.setOnClickListener {
            val editor = sharedPref.edit()
            editor.putString("NICKNAME", binding.etNickname.text.toString())
            editor.putBoolean("HARDCORE", binding.switchHardcoreMode.isChecked)

            // Simulace těžké práce - zablokuje hlavní vlákno, aplikace na 3 s zamrzne
            Thread.sleep(3000)

            // Synchronní zápis na disk
            editor.commit()

            Toast.makeText(requireContext(), "Uloženo pomocí SharedPreferences", Toast.LENGTH_SHORT).show()
        }

        // Navazující úkol: počítadlo kliknutí nezávislé na ukládání
        binding.btnCounter.setOnClickListener {
            counter++
            binding.tvCounter.text = "Počet kliknutí: $counter"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
