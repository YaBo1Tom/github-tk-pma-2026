package com.example.a004aplikace_objednavka

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a004aplikace_objednavka.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Ceny v Kč
    private val priceMargherita = 139
    private val pricePepperoni = 159
    private val priceProsciutto = 159
    private val priceCheese = 10
    private val priceOlives = 10
    private val pricePeppers = 20
    private val priceHam = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ošetření systémových lišt – použije se přímo binding.main nebo binding.root
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setLabels()

        // Změna obrázku podle vybrané pizzy
        binding.rgPizza.setOnCheckedChangeListener { _, checkedId ->
            val image = when (checkedId) {
                R.id.rbPepperoni -> R.drawable.pizza_pepperoni
                R.id.rbProsciutto -> R.drawable.pizza_prosciutto
                else -> R.drawable.pizza_margherita
            }
            binding.ivPizza.setImageResource(image)
        }

        binding.btnOrder.setOnClickListener { showSummary() }
    }

    // Popisky možností včetně cen
    private fun setLabels() {
        binding.rbMargherita.text = getString(R.string.label_pizza_price, getString(R.string.pizza_margherita), priceMargherita)
        binding.rbPepperoni.text = getString(R.string.label_pizza_price, getString(R.string.pizza_pepperoni), pricePepperoni)
        binding.rbProsciutto.text = getString(R.string.label_pizza_price, getString(R.string.pizza_prosciutto), priceProsciutto)
        binding.cbCheese.text = getString(R.string.label_extra_price, getString(R.string.extra_cheese), priceCheese)
        binding.cbOlives.text = getString(R.string.label_extra_price, getString(R.string.extra_olives), priceOlives)
        binding.cbPeppers.text = getString(R.string.label_extra_price, getString(R.string.extra_peppers), pricePeppers)
        binding.cbHam.text = getString(R.string.label_extra_price, getString(R.string.extra_ham), priceHam)
    }

    // Výpis objednávky: položky pod sebou s cenou a na konci celková cena
    private fun showSummary() {
        val (pizzaName, pizzaPrice) = when (binding.rgPizza.checkedRadioButtonId) {
            R.id.rbPepperoni -> getString(R.string.pizza_pepperoni) to pricePepperoni
            R.id.rbProsciutto -> getString(R.string.pizza_prosciutto) to priceProsciutto
            else -> getString(R.string.pizza_margherita) to priceMargherita
        }

        val items = mutableListOf(pizzaName to pizzaPrice)
        if (binding.cbCheese.isChecked) items.add(getString(R.string.extra_cheese) to priceCheese)
        if (binding.cbOlives.isChecked) items.add(getString(R.string.extra_olives) to priceOlives)
        if (binding.cbPeppers.isChecked) items.add(getString(R.string.extra_peppers) to pricePeppers)
        if (binding.cbHam.isChecked) items.add(getString(R.string.extra_ham) to priceHam)

        val lines = items.map { (name, price) -> getString(R.string.summary_line, name, price) }
        val total = items.sumOf { it.second }
        binding.tvSummary.text = (lines + getString(R.string.summary_total, total)).joinToString("\n")
    }
}
