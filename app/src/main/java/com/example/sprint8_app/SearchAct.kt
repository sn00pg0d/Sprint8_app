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
import androidx.recyclerview.widget.RecyclerView

// identify ArrayList of songs
val song: ArrayList<Track> = arrayListOf(
    Track(
        "Smells Like Teen Spirit",
        "Nirvana",
        "5:01",
        "https://is5-ssl.mzstatic.com/image/thumb/Music115/v4/7b/58/c2/7b58c21a-2b51-2bb2-e59a-9bb9b96ad8c3/00602567924166.rgb.jpg/100x100bb.jpg"
    ), Track(
        "Billie Jean",
        "Michael Jackson",
        "4:35",
        "https://is5-ssl.mzstatic.com/image/thumb/Music125/v4/3d/9d/38/3d9d3811-71f0-3a0e-1ada-3004e56ff852/827969428726.jpg/100x100bb.jpg"
    ), Track(
        "Whole Lotta Love",
        "Led Zeppelin",
        "5:33",
        "https://is2-ssl.mzstatic.com/image/thumb/Music62/v4/7e/17/e3/7e17e33f-2efa-2a36-e916-7f808576cf6b/mzm.fyigqcbs.jpg/100x100bb.jpg"
    ), Track(
        "Sweet Child O'Mine",
        "Guns N' Roses",
        "5:03",
        "https://is5-ssl.mzstatic.com/image/thumb/Music125/v4/a0/4d/c4/a04dc484-03cc-02aa-fa82-5334fcb4bc16/18UMGIM24878.rgb.jpg/100x100bb.jpg"
    )
)

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
        val recyclerView = findViewById<RecyclerView>(R.id.playlistRV)
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
                resetSearch.visibility = clearButtonVisibility(s)
                //recyclerView.visibility = clearButtonVisibility(s)
            }

            override fun afterTextChanged(s: Editable?) {

            }
        }

        inputSearch.addTextChangedListener(simpleTextWatcher)

        // add Adapter to RecyclerView
        val songsTrackSearchAdapter = TrackSearchAdapter(song)
        recyclerView.adapter = songsTrackSearchAdapter

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

