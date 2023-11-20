package com.hadis.mymusicapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class MusicAdaptor(var musicList: List<Music>, var context: Context) :
    RecyclerView.Adapter<MusicAdaptor.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView
        val singerName: TextView
        val image: ImageView

        init {
            view.apply {
                title = findViewById(R.id.titleTextView)
                singerName = findViewById(R.id.artistTextView)
                image = findViewById(R.id.coverArtImageView)
            }
            view.setOnClickListener{
                currentMusic = musicList[position]
                Navigation.findNavController(view).navigate(R.id.viewPagerToCurrentPlayingFragment)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return musicList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            title.text = musicList[position].title
            singerName.text = musicList[position].singerName
            Glide.with(context).load(musicList[position].coverArtUri)
                .transform(CenterCrop(), RoundedCorners(25)).into(image)
        }

    }
}