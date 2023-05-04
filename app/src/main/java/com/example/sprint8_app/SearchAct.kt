package com.example.sprint8_app

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class SearchAct : AppCompatActivity() {

    companion object {
        const val SEARCH_TEXT = "SearchText"
    }

    private var searchText: String = ""
    private lateinit var inputSearch: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val resetSearch = findViewById<ImageView>(R.id.clear_button)
        val backButton = findViewById<ImageView>(R.id.back_button)
        inputSearch = findViewById(R.id.searchView)

        backButton.setOnClickListener {
            finish()
        }

        resetSearch.setOnClickListener {
            inputSearch.setText("")
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(resetSearch.windowToken, 0)
        }

        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {

                } else {

                }

                resetSearch.visibility = clearButtonVisibility(s)

            }

            override fun afterTextChanged(s: Editable?) {
                searchText = inputSearch.text.toString()
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SEARCH_TEXT, searchText)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        searchText = savedInstanceState.getString(SEARCH_TEXT).toString()
        inputSearch.setText(searchText)
    }
}
