package com.example.a005_aplikaceobjednavkav2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a005_aplikaceobjednavkav2.databinding.ActivitySummaryBinding

class SummaryActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySummaryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //enableEdgeToEdge()

        // Inicializace ViewBindingu
        binding = ActivitySummaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ochrana před překryvem systémovými lištami
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            // Přidá vnitřní okraj (padding) odpovídající velikosti horní a spodní systémové lišty
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        // Příjmutí dat z intentu
        val pizzaName = intent.getStringExtra("EXTRA_PIZZA_NAME") ?: "Neznámá pizza"
        val totalPrice = intent.getStringExtra("EXTRA_TOTAL_PRICE") ?: "Celkem: 0 Kč"

        val hasCheese = intent.getBooleanExtra("EXTRA_HAS_CHEESE", false)
        val hasOlives = intent.getBooleanExtra("EXTRA_HAS_OLIVES", false)
        val hasPeppers = intent.getBooleanExtra("EXTRA_HAS_PEPPERS", false)
        val hasHam = intent.getBooleanExtra("EXTRA_HAS_HAM", false)

        // Sestavení textu pro příplatkové přísady
        var extrasText = "Příplatkové přísady:\n"
        var hasAnyExtra = false

        if (hasCheese) {
            extrasText += "- Extra sýr\n"
            hasAnyExtra = true
        }

        if (hasOlives) {
            extrasText += "- Extra olivy\n"
            hasAnyExtra = true
        }

        if (hasPeppers) {
            extrasText += "- Extra feferonky\n"
            hasAnyExtra = true
        }

        if (hasHam) {
            extrasText += "- Extra šunka\n"
            hasAnyExtra = true
        }

        // Pokud uživatel nezaškrtl nic
        if (!hasAnyExtra) {
            extrasText += "- žádná"
        }

        // Zobrazení dat v textových polích
        binding.tvSelectedBike.text = "Vybraná pizza: $pizzaName"
        binding.tvExtras.text = extrasText
        binding.tvFinalPrice.text = totalPrice

        // Obsluha tlačítka Zpět pro návrat na úpravu objednávky
        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}
