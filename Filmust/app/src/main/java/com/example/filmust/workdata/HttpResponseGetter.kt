package com.example.filmust.workdata



import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

object HttpResponseGetter {

    fun getMoviesListByUrl(url: String, typeOfMovieList: TypeOfMovieList) {

            val client = OkHttpClient()

            val request = Request.Builder()
                .url(url)
                .get()
                .addHeader("X-RapidAPI-Key", "b173d8d0ccmsh62defdcbb5aed73p1efb70jsn183b90ae351a")
                .addHeader("X-RapidAPI-Host", "moviesdatabase.p.rapidapi.com")
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    TODO("Not yet implemented")
                }

                override fun onResponse(call: Call, response: Response) {
                    response.use {
                        if (!response.isSuccessful)
                            throw Exception()
                        when(typeOfMovieList){
                            TypeOfMovieList.Upcoming -> MoviesRepository.upcomingMovies = deserializeJsonToMoviesList(response.body!!.string())
                            TypeOfMovieList.Searched -> MoviesRepository.searchedMovies = deserializeJsonToMoviesList((response.body!!.string()))
                        }
                    }
                }
            })
    }

    private fun deserializeJsonToMoviesList(jsonString : String) : List<LightMovie> {
        val json = Json { allowStructuredMapKeys = true }
        return json.decodeFromString(MovieResponse.serializer(), jsonString).results.toLightMovieList()
    }

    private fun List<Movie>.toLightMovieList() : List<LightMovie> {
        var result = mutableListOf<LightMovie>()
        for(movie in this){
            var genres : MutableList<String>? = mutableListOf()
            var date = mutableListOf<String>()
            date.add(movie.releaseDate?.day.toString())
            date.add(movie.releaseDate?.month.toString())
            date.add(movie.releaseDate?.year.toString())
            if(movie.genres?.genres != null)
                for (genre in movie.genres?.genres!!) {
                    genres?.add(genre.text!!)
                }

            result.add(
                LightMovie(
                    id = movie.resultID,
                    titleText = movie.titleText?.text,
                    genres = listOf<String>().plus(genres!!.toList()),
                    imageUrl = movie.primaryImage?.url,
                    runtime = movie.runtime?.seconds,
                    releaseDate = date,
                    rating = movie.meterRanking?.currentRank,
                    plot = movie.plot?.plotText?.plainText
                )
            )
        }

        return result
    }
}