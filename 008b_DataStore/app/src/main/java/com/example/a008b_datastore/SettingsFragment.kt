package com.example.a008b_datastore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.preferences.core.edit
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.a008b_datastore.databinding.FragmentSettingsBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

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

        // Načtení uložených dat (asynchronně přes Coroutines a Flow)
        viewLifecycleOwner.lifecycleScope.launch {
            val prefs = requireContext().dataStore.data.first()
            binding.etNickname.setText(prefs[NICKNAME_KEY] ?: "")
            binding.switchHardcoreMode.isChecked = prefs[HARDCORE_KEY] ?: false
        }

        binding.btnSave.setOnClickListener {
            val newName = binding.etNickname.text.toString()
            val newHardcore = binding.switchHardcoreMode.isChecked
            val store = requireContext().applicationContext.dataStore

            viewLifecycleOwner.lifecycleScope.launch {
                binding.progressBar.visibility = View.VISIBLE
                binding.btnSave.isEnabled = false // proti dvojitému kliknutí

                // Simulace těžké práce - na rozdíl od Thread.sleep neblokuje hlavní vlákno
                delay(3000)

                // Původní hodnoty si zapamatujeme pro akci "Zpět"
                val old = store.data.first()
                val oldName = old[NICKNAME_KEY] ?: ""
                val oldHardcore = old[HARDCORE_KEY] ?: false

                store.edit { prefs ->
                    prefs[NICKNAME_KEY] = newName
                    prefs[HARDCORE_KEY] = newHardcore
                }

                binding.progressBar.visibility = View.INVISIBLE
                binding.btnSave.isEnabled = true

                Snackbar.make(binding.root, "Uloženo pomocí DataStore", Snackbar.LENGTH_LONG)
                    .setAction("Zpět") {
                        // Vrátíme původní data ve formuláři i v DataStore
                        binding.etNickname.setText(oldName)
                        binding.switchHardcoreMode.isChecked = oldHardcore
                        viewLifecycleOwner.lifecycleScope.launch {
                            store.edit { prefs ->
                                prefs[NICKNAME_KEY] = oldName
                                prefs[HARDCORE_KEY] = oldHardcore
                            }
                            Snackbar.make(binding.root, "Změna vrácena zpět", Snackbar.LENGTH_SHORT).show()
                        }
                    }
                    .show()
            }
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
