package com.example.sprint8_app

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

// add ViewHolder
class SongsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val artistName: TextView = itemView.findViewById(R.id.artistTV)
    private val trackName: TextView = itemView.findViewById(R.id.songTV)
    private val trackTime: TextView = itemView.findViewById(R.id.timeTV)
    private val coverUrl: ImageView = itemView.findViewById(R.id.coverTV)

    fun bind(model: TrackAct) {
        artistName.text = model.artistName
        trackName.text = model.trackName
        trackTime.text = model.trackTime
        Glide.with(itemView).load(model.artworkUrl100).placeholder(R.drawable.placeholder).centerInside().into(coverUrl)
    }

}