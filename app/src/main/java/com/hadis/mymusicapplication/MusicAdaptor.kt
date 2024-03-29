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


class MusicAdaptor(var musicList: List<Music>, private var context: Context) :
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
            view.setOnClickListener {
                currentMusic = musicList[position]
                if (menuItemInFavoriteSongsFragment!= null){
                    menuItemInFavoriteSongsFragment!!.collapseActionView()
                    menuItemInFavoriteSongsFragment!!.isVisible = false
                }
                if (menuItemAllInMusicFragment!= null){
                    menuItemAllInMusicFragment!!.collapseActionView()
                    menuItemAllInMusicFragment!!.isVisible = false
                }
                Navigation.findNavController(view).navigate(R.id.viewPagerToCurrentPlayingFragment)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return ViewHolder(view)
    }

    fun filterList(list: MutableList<Music>) {
        musicList = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return musicList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            title.text = musicList[position].title
            singerName.text = musicList[position].singerName
            Glide.with(context)
                .load(musicList[position].coverArtUri)
                .error(R.drawable.images)
                .transform(CenterCrop(), RoundedCorners(40))
                .into(image)
        }
    }
}