package com.hogent.dikkeploaten.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.hogent.dikkeploaten.R
import com.hogent.dikkeploaten.databinding.FragmentUserAlbumInfoBinding
import com.hogent.dikkeploaten.models.toAlbumAndUserAlbums
import com.hogent.dikkeploaten.utilities.InjectorUtils
import com.hogent.dikkeploaten.viewmodels.UserAlbumDetailViewModel
import com.hogent.domain.models.AlbumAndUserAlbums

/**
 * This [Fragment] represents the detail page of a selected [UserAlbum].
 */
class UserAlbumDetailFragment : Fragment() {

    private val args: UserAlbumDetailFragmentArgs by navArgs()

    private val userAlbumDetailViewModel: UserAlbumDetailViewModel by viewModels {
        InjectorUtils.provideUserAlbumDetailViewModelFactory(
            requireActivity(),
            args.selectedAlbum.toAlbumAndUserAlbums()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentUserAlbumInfoBinding>(
            inflater, R.layout.fragment_user_album_info, container, false
        ).apply {
            viewModel = userAlbumDetailViewModel
            lifecycleOwner = viewLifecycleOwner
            callback = object : Callback {
                override fun removeUserAlbumFromCollection(userAlbum: AlbumAndUserAlbums?) {
                    userAlbum.let {
                        hideAppBarFab(fabRemoveUserAlbum)
                        userAlbumDetailViewModel.removeUserAlbumFromCollection()
                        Snackbar.make(
                            root,
                            "\"${userAlbum!!.album.title}\" is verwijderd uit je collectie",
                            Snackbar.LENGTH_LONG
                        )
                            .show()
                    }
                }
            }

            var isToolbarShown = false

            // scroll change listener begins at Y = 0 when image is fully collapsed
            userAlbumDetailScrollview.setOnScrollChangeListener(
                NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, _ ->

                    // User scrolled past image to height of toolbar and the title text is
                    // underneath the toolbar, so the toolbar should be shown.
                    val shouldShowToolbar = scrollY > toolbar.height

                    // The new state of the toolbar differs from the previous state; update
                    // appbar and toolbar attributes.
                    if (isToolbarShown != shouldShowToolbar) {
                        isToolbarShown = shouldShowToolbar

                        // Use shadow animator to add elevation if toolbar is shown
                        appbar.isActivated = shouldShowToolbar

                        // Show the album name if toolbar is shown
                        toolbarLayout.isTitleEnabled = shouldShowToolbar
                    }
                }
            )

            toolbar.setNavigationOnClickListener { view ->
                view.findNavController().navigateUp()
            }
        }

        return binding.root
    }

    // FloatingActionButtons anchored to AppBarLayouts have their visibility controlled by the scroll position.
    // We want to turn this behavior off to hide the FAB when it is clicked.
    private fun hideAppBarFab(fab: FloatingActionButton) {
        val params = fab.layoutParams as CoordinatorLayout.LayoutParams
        val behavior = params.behavior as FloatingActionButton.Behavior
        behavior.isAutoHideEnabled = false
        fab.hide()
    }

    interface Callback {
        fun removeUserAlbumFromCollection(album: AlbumAndUserAlbums?)
    }
}
