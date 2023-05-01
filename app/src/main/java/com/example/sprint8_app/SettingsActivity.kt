package com.example.sprint8_app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val backButton = findViewById<ImageView>(R.id.back_button)
        val shareButton: TextView = findViewById(R.id.share_button)
        val supportButton: TextView = findViewById(R.id.support_button)
        val userAgreementButton: TextView = findViewById(R.id.user_agreement_button)
        val searchButton: Button = findViewById(R.id.buttonSearch)

        searchButton.setOnClickListener {
            val displayIntent = Intent(Intent(this, SearchAct::class.java))
            startActivity(displayIntent)
        }

        shareButton.setOnClickListener {
            val practicumWeb = "https://practicum.yandex.ru/android-developer/"
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.setType("text/plain")
            shareIntent.putExtra(Intent.EXTRA_TEXT, practicumWeb)
            startActivity(Intent.createChooser(shareIntent, "Шарим нашу приложуху"))
        }

        supportButton.setOnClickListener {
            val email = "madmaks.45cal@gmail.com"
            val tema = "Сообщение разработчикам и разработчицам приложения Playlist Maker"
            val tekst = "Спасибо разработчикам и разработчицам за крутое приложение!"

            val shareIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:").buildUpon().appendQueryParameter("to", email)
                    .appendQueryParameter("subject", tema).appendQueryParameter("body", tekst).build()
            }
            startActivity(shareIntent)
        }

        userAgreementButton.setOnClickListener {
            val practicumWeb = Uri.parse("https://yandex.ru/legal/practicum_offer/")
            val agreeIntent = Intent(Intent.ACTION_VIEW, practicumWeb)
            startActivity(agreeIntent)
        }

        backButton.setOnClickListener {
            finish()
        }
    }
}