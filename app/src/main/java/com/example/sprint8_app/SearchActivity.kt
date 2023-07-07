package com.example.sprint8_app

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchActivity : AppCompatActivity() {

    companion object {
        private const val SEARCH_TEXT = "SearchText"
    }

    private val iTunesBaseUrl = "https://itunes.apple.com/"
    private val retrofit =
        Retrofit.Builder().baseUrl(iTunesBaseUrl).addConverterFactory(GsonConverterFactory.create()).build()

    private val itunesService = retrofit.create(ITunesApi::class.java)
    private var searchText: String = ""
    private lateinit var inputSearch: EditText
    private val tracks = ArrayList<Track>()

    // add Adapter
    private val adapter = TrackSearchAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val resetSearch = findViewById<ImageView>(R.id.clear_button)
        val backButton = findViewById<ImageView>(R.id.back_button)
        val notFoundPlaceholder: ImageView = findViewById<ImageView>(R.id.not_found_placeholder)
        val serverErrorPlaceholder: ImageView = findViewById<ImageView>(R.id.server_error_placeholder)
        val notFoundText:TextView = findViewById<TextView>(R.id.not_found_text)
        val serverErrorText: TextView = findViewById<TextView>(R.id.server_error_text)
        val refreshButton: Button = findViewById(R.id.refresh_button)
        val recyclerView = findViewById<RecyclerView>(R.id.playlistRV)

        inputSearch = findViewById(R.id.searchView)
        resetSearch.visibility = allStuffVisibility(false)

        //connect Track to Adapter
        adapter.tracks = tracks

        //connect RecycleView to Adapter
        recyclerView.adapter = adapter

        fun showMessage(text: String, additionalMessage: String) {
            if (text.isNotEmpty()) {
                tracks.clear()
                adapter.notifyDataSetChanged()

                if (additionalMessage.isNotEmpty()) {
                    Toast.makeText(applicationContext, additionalMessage, Toast.LENGTH_LONG).show()
                }
            }
        }

        inputSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                itunesService.searchTracks(inputSearch.text.toString()).enqueue(object : Callback<TrackResponse> {
                    override fun onResponse(call: Call<TrackResponse>, response: Response<TrackResponse>) {
                        if (response.code() == 200) {
                            tracks.clear()
                            if (response.body()?.results?.isNotEmpty() == true) {
                                tracks.addAll(response.body()?.results!!)
                                recyclerView.visibility = allStuffVisibility(true)
                                recyclerView.isFocusable = true
                                adapter.notifyDataSetChanged()
                            }
                            if (tracks.isEmpty()) {
                                showMessage(getString(R.string.not_found), getString(R.string.not_found))
                                notFoundText.visibility = allStuffVisibility(true)
                                notFoundPlaceholder.visibility = allStuffVisibility(true)
                                serverErrorText.visibility = allStuffVisibility(false)
                                serverErrorPlaceholder.visibility = allStuffVisibility(false)
                                refreshButton.visibility = allStuffVisibility(false)
                            }

                        } else {
                            showMessage(getString(R.string.server_offline), response.code().toString())
                            serverErrorText.visibility = allStuffVisibility(true)
                            notFoundText.visibility = allStuffVisibility(false)
                            refreshButton.visibility = allStuffVisibility(true)
                            serverErrorPlaceholder.visibility = allStuffVisibility(true)
                        }
                    }

                    override fun onFailure(call: Call<TrackResponse>, t: Throwable) {
                        serverErrorText.visibility = allStuffVisibility(true)
                        notFoundText.visibility = allStuffVisibility(false)
                        notFoundPlaceholder.visibility = allStuffVisibility(false)
                        refreshButton.visibility = allStuffVisibility(true)
                        serverErrorPlaceholder.visibility = allStuffVisibility(true)

                    }
                })
            }
            false
        }

        refreshButton.setOnClickListener {
            updateButton()
        }

        backButton.setOnClickListener {
            finish()
        }

        resetSearch.setOnClickListener {
            inputSearch.setText("")
            tracks.clear()
            recyclerView.removeAllViews()
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(resetSearch.windowToken, 0)
            resetSearch.visibility = allStuffVisibility(false)
            refreshButton.visibility = allStuffVisibility(false)
            recyclerView.visibility = allStuffVisibility(false)
            notFoundPlaceholder.visibility = allStuffVisibility(false)
            notFoundText.visibility = allStuffVisibility(false)
            serverErrorPlaceholder.visibility = allStuffVisibility(false)
            serverErrorText.visibility = allStuffVisibility(false)

        }

        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (inputSearch.text.isEmpty()) {
                    resetSearch.visibility = allStuffVisibility(false)
                } else {
                    resetSearch.visibility = allStuffVisibility(true)
                }

            }

            override fun afterTextChanged(s: Editable?) {

            }
        }

        inputSearch.addTextChangedListener(simpleTextWatcher)

    }

    private fun updateButton() {
        tracks.clear()
        val recyclerView = findViewById<RecyclerView>(R.id.playlistRV)
        val resetSearch = findViewById<ImageView>(R.id.clear_button)
        val refreshButton: Button = findViewById(R.id.refresh_button)
        val serverErrorText: TextView = findViewById<TextView>(R.id.server_error_text)
        val notFoundText:TextView = findViewById<TextView>(R.id.not_found_text)
        val notFoundPlaceholder: ImageView = findViewById<ImageView>(R.id.not_found_placeholder)
        val serverErrorPlaceholder: ImageView = findViewById<ImageView>(R.id.server_error_placeholder)
        itunesService.searchTracks(inputSearch.text.toString()).enqueue(object : Callback<TrackResponse> {
            override fun onResponse(call: Call<TrackResponse>, response: Response<TrackResponse>) {
                if (response.code() == 200) {
                    tracks.clear()
                    if (response.body()?.results?.isNotEmpty() == true) {
                        tracks.addAll(response.body()?.results!!)
                        recyclerView.visibility = allStuffVisibility(true)
                        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.hideSoftInputFromWindow(resetSearch.windowToken, 0)
                        refreshButton.visibility = allStuffVisibility((false))
                        serverErrorText.visibility = allStuffVisibility(false)
                        notFoundPlaceholder.visibility = allStuffVisibility(false)
                        serverErrorPlaceholder.visibility = allStuffVisibility(false)
                        adapter.notifyDataSetChanged()
                    } else {
                        tracks.clear()
                        recyclerView.visibility = allStuffVisibility(false)
                        notFoundText.visibility = allStuffVisibility(true)
                        notFoundPlaceholder.visibility = allStuffVisibility(true)
                        refreshButton.visibility = allStuffVisibility(false)
                        serverErrorText.visibility = allStuffVisibility(false)
                        serverErrorPlaceholder.visibility = allStuffVisibility(false)
                    }
                } else {
                    tracks.clear()
                    serverErrorText.visibility = allStuffVisibility(true)
                    notFoundPlaceholder.visibility = allStuffVisibility(false)
                    refreshButton.visibility = allStuffVisibility(true)
                    serverErrorPlaceholder.visibility = allStuffVisibility(true)
                    Toast.makeText(applicationContext, R.string.check_net, Toast.LENGTH_LONG).show()

                }
            }

            override fun onFailure(call: Call<TrackResponse>, t: Throwable) {
                tracks.clear()
                serverErrorText.visibility = allStuffVisibility(true)
                notFoundPlaceholder.visibility = allStuffVisibility(false)
                refreshButton.visibility = allStuffVisibility(true)
                serverErrorPlaceholder.visibility = allStuffVisibility(true)
                Toast.makeText(applicationContext, R.string.check_net, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun allStuffVisibility(s: Boolean): Int {
        return if (!s) {
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
