package com.example.filmust.workdata

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.filmust.databinding.ItemMovieBinding

class MovieAdapter(
    private var listOfMovies: List<LightMovie>,
    val glide: RequestManager,
    val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<MovieItem>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItem =
        MovieItem(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            glide = glide,
            onItemClick = onItemClick
        )
//
    override fun getItemCount(): Int {
        return listOfMovies.size
    }

    override fun onBindViewHolder(holder: MovieItem, position: Int) {
        holder.onBind(listOfMovies[position])
    }

    fun updateList(newList: List<LightMovie>){
        listOfMovies = newList
        notifyDataSetChanged()
    }
}
