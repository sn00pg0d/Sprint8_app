package com.example.sprint8_app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

private const val SEARCH_TEXT = "SearchText"

class SearchAct : AppCompatActivity() {

    private var searchText: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val inputSearch = findViewById<EditText>(R.id.searchView)
        //val layoutSearch = findViewById<LinearLayout>(R.id.linearLayout)
        val resetSearch = findViewById<ImageView>(R.id.clear_button)
        val settingsButton = findViewById<Button>(R.id.settings_button)
        val backButton = findViewById<Button>(R.id.back_button_search)

        backButton.setOnClickListener { // а в дизайне этого нет, пришлось приколхозить наобум
            finish()
        }

        settingsButton.setOnClickListener {
            val displayIntent = Intent(Intent(this, SettingsActivity::class.java))
            startActivity(displayIntent)
        }

        resetSearch.setOnClickListener {
            inputSearch.setText("")

            // не совсем понятно, где мы проходили это в спринте? копипастнул с инета:
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(resetSearch.windowToken, 0)
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

                if (inputSearch != null) {
                    backButton.visibility = backButtonVisibility(s)
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // ждите ответа в следующей серии
            }
        }
        inputSearch.addTextChangedListener(simpleTextWatcher)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val inputSearch = findViewById<EditText>(R.id.searchView)
        searchText = inputSearch.text.toString()
        outState.putString(SEARCH_TEXT, searchText)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val inputSearch = findViewById<EditText>(R.id.searchView)
        if (savedInstanceState != null) { // среда разработки настойчиво хочет убрать условие, почему?
            searchText = savedInstanceState.getString(SEARCH_TEXT).toString()
            inputSearch.setText(searchText)
        }
    }

    private fun backButtonVisibility(s: CharSequence?): Int {
        return if (!s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    private fun clearButtonVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }
}