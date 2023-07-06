package com.example.sprint8_app

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

// add ViewHolder
class SongsViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.songs_layout, parent, false)) {
    private val artistName: TextView = itemView.findViewById(R.id.artistTV)
    private val trackName: TextView = itemView.findViewById(R.id.songTV)
    private val trackTime: TextView = itemView.findViewById(R.id.timeTV)
    private val artworkUrl100: ImageView = itemView.findViewById(R.id.coverTV)

    fun bind(model: Track) {
        artistName.text = model.artistName
        trackName.text = model.trackName
        trackTime.text = MillisToMMSS.formatTime(model.trackTimeMillis)
        Glide.with(itemView).load(model.artworkUrl100).placeholder(R.drawable.placeholder).centerInside().into(artworkUrl100)
    }

}