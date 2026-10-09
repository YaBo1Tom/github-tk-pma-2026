package com.example.a004aplikace_objednavka

import android.os.Bundle
import android.widget.Toast
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

    // Dosud přidané řádky objednávky a jejich celková cena
    private val orderLines = mutableListOf<String>()
    private var orderTotal = 0

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

        binding.btnAdd.setOnClickListener { addToSummary() }
        binding.btnReset.setOnClickListener { resetOrder() }
        binding.btnOrder.setOnClickListener {
            Toast.makeText(this, R.string.order_confirmed, Toast.LENGTH_SHORT).show()
        }
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

    // Přidá vybranou pizzu a přísady do souhrnu (původní řádky zůstávají) a přepočítá celkovou cenu
    private fun addToSummary() {
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

        items.forEach { (name, price) -> orderLines.add(getString(R.string.summary_line, name, price)) }
        orderTotal += items.sumOf { it.second }
        binding.tvSummary.text = (orderLines + getString(R.string.summary_total, orderTotal)).joinToString("\n")
    }

    // Smaže souhrn a vrátí formulář do výchozího stavu
    private fun resetOrder() {
        orderLines.clear()
        orderTotal = 0
        binding.tvSummary.setText(R.string.summary_empty)
        binding.rgPizza.check(R.id.rbMargherita)
        binding.cbCheese.isChecked = false
        binding.cbOlives.isChecked = false
        binding.cbPeppers.isChecked = false
        binding.cbHam.isChecked = false
    }
}
