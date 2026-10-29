package com.example.a007_fragmenty

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

enum class Pizza(val label: String, val price: Int, val image: Int) {
    MARGHERITA("Margherita", 139, R.drawable.pizza_margherita),
    PEPPERONI("Pepperoni", 159, R.drawable.pizza_pepperoni),
    PROSCIUTTO("Prosciutto", 159, R.drawable.pizza_prosciutto)
}

enum class Extra(val label: String, val price: Int) {
    CHEESE("Extra sýr", 10),
    OLIVES("Extra olivy", 10),
    PEPPERS("Extra feferonky", 20),
    HAM("Extra šunka", 10)
}

// Sdílený stav objednávky - všechny fragmenty i MainActivity používají jednu instanci
class OrderViewModel : ViewModel() {

    var pizza = Pizza.MARGHERITA
        private set
    val extras = mutableSetOf<Extra>()

    private val _totalPrice = MutableLiveData(Pizza.MARGHERITA.price)
    val totalPrice: LiveData<Int> = _totalPrice

    fun selectPizza(pizza: Pizza) {
        this.pizza = pizza
        updatePrice()
    }

    fun setExtra(extra: Extra, selected: Boolean) {
        if (selected) extras.add(extra) else extras.remove(extra)
        updatePrice()
    }

    private fun updatePrice() {
        _totalPrice.value = pizza.price + extras.sumOf { it.price }
    }
}
