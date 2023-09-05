package com.example.filmust.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.filmust.workdata.MovieAdapter
import com.example.filmust.R
import com.example.filmust.databinding.FragmentFavouritesBinding
import com.example.filmust.workdata.LightMovie
import com.example.filmust.workdata.MoviesRepository

class FavouritesFragment : Fragment(R.layout.fragment_favourites) {

    private var binding: FragmentFavouritesBinding? = null
    private var adapter : MovieAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavouritesBinding.bind(view)

        initAdapter(MoviesRepository.favoriteMovies!!)

        binding!!.rvFavourites.findViewHolderForItemId(R.id.btn_favourite.toLong())
    }

    private fun initAdapter(listOfMovies : List<LightMovie>){
        adapter = MovieAdapter(
            listOfMovies = listOfMovies,
            glide = Glide.with(this),
            onItemClick = {
                val bundle = Bundle()
                bundle.putString("MOVIE_ID", it)
                findNavController().navigate(
                    R.id.action_favouritesFragment_to_detailFragment,
                    bundle
                )
            })

        binding?.rvFavourites?.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}