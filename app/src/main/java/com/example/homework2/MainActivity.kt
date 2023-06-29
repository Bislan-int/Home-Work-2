package com.example.homework2

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.homework2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getResultRandom()
    }

    private fun getResultRandom() {
        binding.customViewDrum.resultRandom.observe(this) {
            when (it) {
                "VOID" -> {
                    binding.image.visibility = View.GONE
                    binding.text.visibility = View.GONE
                }
                "ГОЛУБОЙ" -> {
                    binding.text.text = it
                    resultGoneOrVisible(true, R.color.light_blue)
                }
                "ЖЕЛТЫЙ" -> {
                    binding.text.text = it
                    resultGoneOrVisible(true, R.color.yellow)
                }
                "КРАСНЫЙ" -> {
                    binding.text.text = it
                    resultGoneOrVisible(true, R.color.red)
                }
                "ФИОЛЕТОВЫЙ" -> {
                    binding.text.text = it
                    resultGoneOrVisible(true, R.color.violet)
                }
                else -> {
                    Glide
                        .with(this)
                        .load(it)
                        .into(binding.image)

                    resultGoneOrVisible(false)
                }
            }
        }
    }

    private fun resultGoneOrVisible(isVisibleText: Boolean, color: Int? = null) {
        if (isVisibleText) {
            color?.let { binding.text.setTextColor(it) }
            binding.text.visibility = View.VISIBLE
            binding.image.visibility = View.GONE
        } else {
            binding.image.visibility = View.VISIBLE
            binding.text.visibility = View.GONE
        }
    }
}