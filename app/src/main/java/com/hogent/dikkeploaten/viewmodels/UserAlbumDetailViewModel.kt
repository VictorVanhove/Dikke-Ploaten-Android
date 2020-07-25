package com.hogent.dikkeploaten.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.hogent.dikkeploaten.database.AlbumAndUserAlbums
import com.hogent.dikkeploaten.database.DatabaseAlbum
import com.hogent.dikkeploaten.database.UserAlbum
import com.hogent.dikkeploaten.repositories.UserAlbumRepository
import kotlinx.coroutines.launch

/**
 *  The [ViewModel] associated with the [UserAlbumDetailFragment], containing information about the selected
 *  [UserAlbum].
 */
class UserAlbumDetailViewModel(
    private val userAlbumRepository: UserAlbumRepository,
    private val albumProperty: AlbumAndUserAlbums
) : ViewModel() {
    private val _selectedProperty = MutableLiveData<AlbumAndUserAlbums>()

    // The external LiveData for the SelectedProperty
    val selectedProperty: LiveData<AlbumAndUserAlbums>
        get() = _selectedProperty

    // Initialize the _selectedProperty MutableLiveData
    init {
        _selectedProperty.value = albumProperty
    }

    val isInCollection = userAlbumRepository.isInCollection(albumProperty.album.albumId)

    fun removeUserAlbumFromCollection() {
        viewModelScope.launch {
            val userAlbum = userAlbumRepository.getUserAlbum(albumProperty.album.albumId)
            userAlbumRepository.removeUserAlbum(userAlbum)
        }
    }

}