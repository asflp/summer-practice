package com.example.filmust.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.filmust.MainActivity
import com.example.filmust.workdata.HttpResponseGetter
import com.example.filmust.workdata.Movie
import com.example.filmust.workdata.MovieAdapter
import com.example.filmust.workdata.MoviesRepository
import com.example.filmust.R
import com.example.filmust.workdata.TypeOfMovieList
import com.example.filmust.databinding.FragmentSearchBinding
import com.example.filmust.workdata.LightMovie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SearchFragment : Fragment(R.layout.fragment_search) {

    private var binding: FragmentSearchBinding? = null
    private var adapter : MovieAdapter? = null

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).showBottomNavigation()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)

        initAdapter(MoviesRepository.upcomingMovies)

        binding?.btnSearch?.setOnClickListener{
            val movieName = binding?.etSearch?.text.toString()
            if(movieName.isNotEmpty()){
                CoroutineScope(Dispatchers.IO).launch {
                    HttpResponseGetter.getMoviesListByUrl(
                        "https://moviesdatabase.p.rapidapi.com/titles/search/title/$movieName?exact=true&info=base_info&titleType=movie",
                        TypeOfMovieList.Searched
                    )
                    withContext(Dispatchers.Main) {
                        delay(1000L)
                        adapter?.updateList(MoviesRepository.searchedMovies)
                        binding?.twSearchTitle?.text = "Here's what I could find"
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun initAdapter(listOfMovies : List<LightMovie>) {
        adapter = MovieAdapter(
            listOfMovies = listOfMovies,
            glide = Glide.with(this),
            onItemClick = {
                val bundle = Bundle()
                bundle.putString("MOVIE_ID", it)

                findNavController().navigate(
                    R.id.action_searchFragment_to_detailFragment,
                    bundle
                )
            })
        binding?.rwSearchResults?.adapter = adapter
    }

}