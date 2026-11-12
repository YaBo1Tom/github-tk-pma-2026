package com.example.a008b_datastore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.a008b_datastore.databinding.FragmentDashboardBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Výpis uložených dat z DataStore (asynchronně)
        viewLifecycleOwner.lifecycleScope.launch {
            val prefs = requireContext().dataStore.data.first()
            val name = prefs[NICKNAME_KEY].orEmpty()
            val hardcore = prefs[HARDCORE_KEY] ?: false

            binding.tvNickname.text = "Přezdívka: " + name.ifEmpty { "(nezadáno)" }
            binding.tvHardcore.text = "Hardcore režim: " + if (hardcore) "zapnutý" else "vypnutý"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
