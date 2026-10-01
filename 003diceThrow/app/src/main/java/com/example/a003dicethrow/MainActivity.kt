package com.example.a003dicethrow

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private val diceFaces = listOf("⚀", "⚁", "⚂", "⚃", "⚄", "⚅")

    // Runs the delayed animation steps on the main (UI) thread
    private val handler = Handler(Looper.getMainLooper())

    // How many random faces are shown before the final result, and the delay between them
    private val rollSteps = 10
    private val rollDelayMs = 250L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Odsazení obsahu od systémových lišt.
        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.llMain)
        ) { view, insets ->
            val systemBars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars()
            )

            view.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }

        val tvDice = findViewById<TextView>(R.id.tvDice)
        val btnRoll = findViewById<Button>(R.id.btnRoll)

        btnRoll.setOnClickListener {
            rollDice(tvDice, btnRoll)
        }
    }

    // Shows a quick series of random dice faces, then after one more 250 ms delay, the final result
    private fun rollDice(tvDice: TextView, btnRoll: Button) {
        // Block further clicks until the animation finishes
        btnRoll.isEnabled = false
        var stepsDone = 0

        lateinit var rollStep: Runnable
        rollStep = Runnable {
            tvDice.text = diceFaces.random()
            stepsDone++

            if (stepsDone < rollSteps) {
                handler.postDelayed(rollStep, rollDelayMs)
            } else {
                // All flicker steps done: wait once more, then show the final result
                handler.postDelayed({
                    tvDice.text = diceFaces.random()
                    btnRoll.isEnabled = true
                }, rollDelayMs)
            }
        }
        handler.post(rollStep)
    }
}
