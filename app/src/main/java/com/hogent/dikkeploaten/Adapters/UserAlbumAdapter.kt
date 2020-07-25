package com.hogent.dikkeploaten.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hogent.dikkeploaten.database.AlbumAndUserAlbums
import com.hogent.dikkeploaten.databinding.ListItemUserAlbumBinding
import com.hogent.dikkeploaten.viewmodels.AlbumAndUserAlbumsViewModel

class UserAlbumAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<AlbumAndUserAlbums, UserAlbumAdapter.UserAlbumViewHolder>(DiffCallback) {

    /**
     * The MarsPropertyViewHolder constructor takes the binding variable from the associated
     * GridViewItem, which nicely gives it access to the full [AlbumAndUserAlbums] information.
     */
    class UserAlbumViewHolder(private var binding: ListItemUserAlbumBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(albums: AlbumAndUserAlbums) {
            with(binding) {
                viewModel = AlbumAndUserAlbumsViewModel(albums)
                executePendingBindings()
            }
        }

    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [AlbumAndUserAlbums]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<AlbumAndUserAlbums>() {
        override fun areItemsTheSame(
            oldItem: AlbumAndUserAlbums,
            newItem: AlbumAndUserAlbums
        ): Boolean {
            return oldItem.album.albumId == newItem.album.albumId
        }

        override fun areContentsTheSame(
            oldItem: AlbumAndUserAlbums,
            newItem: AlbumAndUserAlbums
        ): Boolean {
            return oldItem.album == newItem.album
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAlbumViewHolder {
        return UserAlbumViewHolder(ListItemUserAlbumBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: UserAlbumViewHolder, position: Int) {
        val album = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(album)
        }
        holder.bind(album)
    }

    class OnClickListener(val clickListener: (album: AlbumAndUserAlbums) -> Unit) {
        fun onClick(album: AlbumAndUserAlbums) = clickListener(album)
    }

}