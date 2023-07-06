package com.example.sprint8_app

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

// add Adapter
class TrackSearchAdapter() : RecyclerView.Adapter<SongsViewHolder>() {

    internal var tracks = ArrayList<Track>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongsViewHolder = SongsViewHolder(parent)

    override fun onBindViewHolder(holder: SongsViewHolder, position: Int) {
        holder.bind(tracks[position])
    }

    override fun getItemCount() : Int = tracks.size
}