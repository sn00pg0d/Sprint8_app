package com.example.sprint8_app

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class SearchAct : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val inputSearch = findViewById<EditText>(R.id.searchView)
        val layoutSearch = findViewById<LinearLayout>(R.id.linearLayout)
        val resetSearch = findViewById<ImageView>(R.id.clear_button)
        val settingsButton = findViewById<Button>(R.id.settings_button)

        settingsButton.setOnClickListener {
            val displayIntent = Intent(Intent(this, SettingsActivity::class.java))
            startActivity(displayIntent)
        }

        resetSearch.setOnClickListener {
            inputSearch.setText("")
        }

        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // ждите ответа в следующей серии
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    // ждите ответа в следующей серии
                } else {
                    // ждите ответа в следующей серии
                }
                resetSearch.visibility = clearButtonVisibility(s)
            }

            override fun afterTextChanged(s: Editable?) {
                // ждите ответа в следующей серии
            }
        }
        inputSearch.addTextChangedListener(simpleTextWatcher)
    }

    private fun clearButtonVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }
}