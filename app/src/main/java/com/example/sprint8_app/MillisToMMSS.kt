package com.example.sprint8_app

import java.text.SimpleDateFormat
import java.util.*

object MillisToMMSS {
    fun formatTime(trackTime: Long): String {
        return SimpleDateFormat("mm:ss", Locale.getDefault()).format(trackTime)
    }
}