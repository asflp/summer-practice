package com.example.filmust.workdata

object MoviesRepository {
    var upcomingMovies : List<LightMovie> = mutableListOf()
    var searchedMovies : List<LightMovie> = mutableListOf()
    var favoriteMovies : MutableList<LightMovie>? = mutableListOf()
    var viewedMovies : MutableList<LightMovie>? = mutableListOf()
    val favoriteSet : MutableSet<String> = mutableSetOf()
    val viewedSet : MutableSet<String> = mutableSetOf()
}