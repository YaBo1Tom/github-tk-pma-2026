package com.example.a008a_sharedpref

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.a008a_sharedpref.databinding.FragmentDashboardBinding

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

        // Výpis uložených dat ze SharedPreferences
        val sharedPref = requireActivity().getSharedPreferences("GameProfile", Context.MODE_PRIVATE)
        val name = sharedPref.getString("NICKNAME", "").orEmpty()
        val hardcore = sharedPref.getBoolean("HARDCORE", false)

        binding.tvNickname.text = "Přezdívka: " + name.ifEmpty { "(nezadáno)" }
        binding.tvHardcore.text = "Hardcore režim: " + if (hardcore) "zapnutý" else "vypnutý"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
