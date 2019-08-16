package com.hogent.dikkeploaten.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.hogent.dikkeploaten.Activities.MainActivity
import com.hogent.dikkeploaten.Models.Album
import com.hogent.dikkeploaten.R
import com.hogent.dikkeploaten.Services.API
import kotlinx.android.synthetic.main.layout_albumitem.view.*

class AlbumAdapter(var context: Context, var albums: ArrayList<Album>, var showStatus: Boolean) : RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {

    /**
     * Inflates the AlbumViewHolder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.layout_albumitem, parent, false)
        return AlbumViewHolder(itemView)
    }

    /**
     * Binds each album in the ArrayList to a view.
     */
    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val album = albums[position]
        holder.txt_title.text = album.title
        holder.txt_artist.text = album.artist

        val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
        Glide.with(context).load(album.thumb).apply(requestOptions).into(holder.image_album)

        //Adds status image if album belongs to collection/wantlist
        if(showStatus) {
            if (API.shared.cache.user.plates.any { userAlbum -> userAlbum.albumID == album.id }) {
                Glide.with(context).load(R.mipmap.ic_in_collection).apply(requestOptions).into(holder.image_status)
            } else if (API.shared.cache.user.wantList.any { userAlbum -> userAlbum.albumID == album.id }){
                Glide.with(context).load(R.mipmap.ic_in_wantlist).apply(requestOptions).into(holder.image_status)
            }
            else {
                Glide.with(context).load(R.drawable.ic_not_in_collection).apply(requestOptions).into(holder.image_status)
            }
        }

        holder.itemView.setOnClickListener {
            run {
                if (context is MainActivity)
                {
                    val activity = context as MainActivity
                    activity.goToAlbumDetail(album)
                }
            }
        }
    }

    /**
     * Gets the number of albums in the list.
     */
    override fun getItemCount(): Int {
        return albums.size
    }

    class AlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txt_title = itemView.title_album
        val txt_artist = itemView.artist_album
        val image_album = itemView.image_album
        val image_status = itemView.image_status

    }

    /**
     * Filters the albums in the recyclerView
     */
    fun setFilter(filteredList: ArrayList<Album>) {
        this.albums = filteredList
        notifyDataSetChanged()
    }
}

