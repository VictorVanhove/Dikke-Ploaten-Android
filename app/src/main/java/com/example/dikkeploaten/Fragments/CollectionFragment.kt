package com.example.dikkeploaten.Fragments

import android.os.Bundle
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import androidx.annotation.NonNull
    import androidx.fragment.app.Fragment
    import com.example.dikkeploaten.R

    class CollectionFragment : Fragment() {

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.fragment_collection, container, false)
        }

}