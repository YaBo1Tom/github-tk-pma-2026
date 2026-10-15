package com.example.a005_aplikaceobjednavkav2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a005_aplikaceobjednavkav2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        //enableEdgeToEdge()
        setContentView(binding.root)

        // Ošetření systémových lišt – použije se přímo binding.main nebo binding.root
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Zavoláme hned po startu, aby se zobrazila výchozí cena vybrané pizzy
        updatePrice()

        // Nastavení listeneru na změny pro RadioGroup (výběr pizzy)
        binding.rgPizza.setOnCheckedChangeListener { _, _ -> updatePrice() }

        // Nastavení listeneru na změny pro Checkboxy (příplatkové přísady)
        binding.cbCheese.setOnClickListener { updatePrice() }
        binding.cbOlives.setOnClickListener { updatePrice() }
        binding.cbPeppers.setOnClickListener { updatePrice() }
        binding.cbHam.setOnClickListener { updatePrice() }

        binding.btnOrder.setOnClickListener {
            // Zjišťujeme název vybrané pizzy
            val pizzaName = when (binding.rgPizza.checkedRadioButtonId) {
                binding.rbMargherita.id -> "Margherita"
                binding.rbPepperoni.id -> "Pepperoni"
                binding.rbProsciutto.id -> "Prosciutto"
                else -> "Neznámá pizza"
            }

            // Extrakce výsledné ceny (vezmeme ji rovnou z barevného textView)
            val currentPriceText = binding.tvTotalPrice.text.toString()

            // Vytvoření Intentu směřujícího na novou aktivitu (SummaryActivity)
            val intent = Intent(this, SummaryActivity::class.java)

            // Přibalení dat jako klíč-hodnota
            intent.putExtra("EXTRA_PIZZA_NAME", pizzaName)
            intent.putExtra("EXTRA_TOTAL_PRICE", currentPriceText)

            // Přidání informací o příplatkových přísadách (boolean hodnoty)
            intent.putExtra("EXTRA_HAS_CHEESE", binding.cbCheese.isChecked)
            intent.putExtra("EXTRA_HAS_OLIVES", binding.cbOlives.isChecked)
            intent.putExtra("EXTRA_HAS_PEPPERS", binding.cbPeppers.isChecked)
            intent.putExtra("EXTRA_HAS_HAM", binding.cbHam.isChecked)

            // Spuštění druhé aktivity
            startActivity(intent)
        }

        // Změna obrázku v závislosti na vybraném radiobuttonu
        binding.rbMargherita.setOnClickListener {
            binding.ivPizza.setImageResource(R.drawable.pizza_margherita)
        }

        binding.rbPepperoni.setOnClickListener {
            binding.ivPizza.setImageResource(R.drawable.pizza_pepperoni)
        }

        binding.rbProsciutto.setOnClickListener {
            binding.ivPizza.setImageResource(R.drawable.pizza_prosciutto)
        }

    }

    private fun updatePrice() {
        var totalPrice = 0

        // 1. Zjištění základní ceny vybrané pizzy
        totalPrice += when (binding.rgPizza.checkedRadioButtonId) {
            binding.rbMargherita.id -> 139
            binding.rbPepperoni.id -> 159
            binding.rbProsciutto.id -> 159
            else -> 0
        }

        // 2. Přičtení příplatkových přísad
        if (binding.cbCheese.isChecked) totalPrice += 10
        if (binding.cbOlives.isChecked) totalPrice += 10
        if (binding.cbPeppers.isChecked) totalPrice += 20
        if (binding.cbHam.isChecked) totalPrice += 10

        // 3. Zobrazení výsledku v barevném textview
        binding.tvTotalPrice.text = "Celkem: $totalPrice Kč"

    }
}
