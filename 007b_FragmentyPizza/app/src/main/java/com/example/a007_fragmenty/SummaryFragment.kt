package com.example.a007_fragmenty

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.a007_fragmenty.databinding.FragmentSummaryBinding

class SummaryFragment : Fragment() {

    private lateinit var binding: FragmentSummaryBinding
    private val viewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSummaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvSelectedPizza.text = getString(R.string.summary_pizza, viewModel.pizza.label)

        binding.tvExtras.text = if (viewModel.extras.isEmpty()) {
            getString(R.string.summary_extras, "- žádná")
        } else {
            getString(
                R.string.summary_extras,
                viewModel.extras.joinToString("\n") { "- ${it.label}" }
            )
        }

        binding.btnOrder.setOnClickListener {
            Toast.makeText(requireContext(), R.string.order_sent, Toast.LENGTH_SHORT).show()
        }
    }
}
