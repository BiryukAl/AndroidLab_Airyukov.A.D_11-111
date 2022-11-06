package com.example.education.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.education.R
import com.example.education.databinding.ItemCategoriesBinding
import com.example.education.databinding.ItemFavoriteSongBinding
import com.example.education.databinding.ItemPlaylistBinding

class MultiHolderAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var dataList: List<String> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            ViewHolderTypes.FAVORITE_SONG_TYPE.typeValue -> {
                FavoriteSongsViewHolder(viewBinding = ItemFavoriteSongBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                ))
            }

            ViewHolderTypes.CATEGORIES_TYPE.typeValue -> {
                CategoriesViewHolder(
                    viewBinding = ItemCategoriesBinding.inflate(LayoutInflater.from(parent.context),
                        parent,
                        false)
                )
            }
            else -> {
                PlaylistsViewHolder(
                    viewBinding = ItemPlaylistBinding.inflate(LayoutInflater.from(parent.context),
                        parent,
                        false)
                )
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ViewHolderTypes.FAVORITE_SONG_TYPE.typeValue -> {
                (holder as? FavoriteSongsViewHolder)?.bindFavoriteSongItem()
            }
            ViewHolderTypes.CATEGORIES_TYPE.typeValue -> {
                (holder as? CategoriesViewHolder)?.bindCategoryItem()
            }
            ViewHolderTypes.PLAYLIST_TYPE.typeValue -> {
                (holder as? PlaylistsViewHolder)?.bindPlaylistItem()
            }
        }

    }

    override fun getItemViewType(position: Int): Int = 1
//        when(dataList[position]){
//            is
//        }


    override fun getItemCount(): Int = dataList.size


    inner class FavoriteSongsViewHolder(private val viewBinding: ItemFavoriteSongBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bindFavoriteSongItem() {

        }
    }

    inner class CategoriesViewHolder(private val viewBinding: ItemCategoriesBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bindCategoryItem() {

        }
    }

    inner class PlaylistsViewHolder(private val viewBinding: ItemPlaylistBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bindPlaylistItem() {}
    }


}

enum class ViewHolderTypes(val typeValue: Int) {
    // Layout тоже является интом
    FAVORITE_SONG_TYPE(typeValue = R.layout.item_favorite_song),
    CATEGORIES_TYPE(typeValue = R.layout.item_categories),
    PLAYLIST_TYPE(typeValue = R.layout.item_playlist),
}