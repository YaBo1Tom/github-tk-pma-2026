package com.example.a007_fragmenty

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.a007_fragmenty.databinding.FragmentPizzaBinding

class PizzaFragment : Fragment() {

    private lateinit var binding: FragmentPizzaBinding
    private val viewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPizzaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obnovení výběru (po návratu z dalšího kroku se fragment vytváří znovu)
        showPizza(viewModel.pizza)

        binding.rgPizza.setOnCheckedChangeListener { _, checkedId ->
            val pizza = when (checkedId) {
                binding.rbPepperoni.id -> Pizza.PEPPERONI
                binding.rbProsciutto.id -> Pizza.PROSCIUTTO
                else -> Pizza.MARGHERITA
            }
            viewModel.selectPizza(pizza)
            binding.ivPizza.setImageResource(pizza.image)
        }
    }

    private fun showPizza(pizza: Pizza) {
        binding.ivPizza.setImageResource(pizza.image)
        when (pizza) {
            Pizza.MARGHERITA -> binding.rbMargherita.isChecked = true
            Pizza.PEPPERONI -> binding.rbPepperoni.isChecked = true
            Pizza.PROSCIUTTO -> binding.rbProsciutto.isChecked = true
        }
    }
}
