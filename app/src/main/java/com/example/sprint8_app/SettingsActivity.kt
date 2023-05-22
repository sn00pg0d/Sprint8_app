package com.example.sprint8_app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
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

        shareButton.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.setType("text/plain")
            shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.practicum_web))
            startActivity(Intent.createChooser(shareIntent, "Шарим нашу приложуху"))
        }

        supportButton.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:").buildUpon().appendQueryParameter("to", getString(R.string.email))
                    .appendQueryParameter("subject", getString(R.string.tema))
                    .appendQueryParameter("body", getString(R.string.tekst)).build()
            }
            startActivity(shareIntent)
        }

        userAgreementButton.setOnClickListener {
            val agreeIntent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.oferta_web)))
            startActivity(agreeIntent)
        }

        backButton.setOnClickListener {
            finish()
        }
    }
}