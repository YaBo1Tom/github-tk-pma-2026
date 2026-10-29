package com.example.a007_fragmenty

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.a007_fragmenty.databinding.FragmentExtrasBinding

class ExtrasFragment : Fragment() {

    private lateinit var binding: FragmentExtrasBinding
    private val viewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExtrasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindExtra(binding.cbCheese, Extra.CHEESE)
        bindExtra(binding.cbOlives, Extra.OLIVES)
        bindExtra(binding.cbPeppers, Extra.PEPPERS)
        bindExtra(binding.cbHam, Extra.HAM)
    }

    // Obnoví stav checkboxu z ViewModelu a hlídá jeho změny
    private fun bindExtra(checkBox: CheckBox, extra: Extra) {
        checkBox.isChecked = extra in viewModel.extras
        checkBox.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setExtra(extra, isChecked)
        }
    }
}
