package com.example.filmust.workdata

import com.example.filmust.fragments.ProfileFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlin.system.measureTimeMillis


class FirebaseManager {
    companion object {
        private var rootNode: FirebaseDatabase = FirebaseDatabase.getInstance()
        private var reference: DatabaseReference = rootNode.getReference("users")
        var favoriteReference = reference.child(ProfileFragment.login).child("favoriteMovie")
        var viewedReference = reference.child(ProfileFragment.login).child("viewedMovies")
        fun readUserData() {
            favoriteReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list : MutableList<LightMovie>? = mutableListOf()
                    for(movie in snapshot.children){
                        list?.add(LightMovie(
                            id = movie.child("id").value as String?,
                            titleText = movie.child("titleText").value as String?,
                            genres = movie.child("genres").value as List<String>?,
                            imageUrl = movie.child("imageUrl").value as String?,
                            runtime = movie.child("runtime").value as Long?,
                            releaseDate = movie.child("releaseDate").value as List<String>?,
                            rating = movie.child("rating").value as Long?,
                            plot = movie.child("plot").value as String?
                        ))
                    }
                    if(list != null){
                        MoviesRepository.favoriteMovies = list
                    } else {
                        MoviesRepository.favoriteMovies = mutableListOf()
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    // Обработка ошибки
                }
            })
            if (MoviesRepository.favoriteMovies != null) {
                for (movie in MoviesRepository.favoriteMovies!!) {
                    MoviesRepository.favoriteSet.add(movie.id!!)
                }
            }

            viewedReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = mutableListOf<LightMovie>()
                    for(movie in snapshot.children){
                        list.add(LightMovie(
                            id = movie.child("id").value as String?,
                            titleText = movie.child("titleText").value as String?,
                            genres = movie.child("genres").value as List<String>?,
                            imageUrl = movie.child("imageUrl").value as String?,
                            runtime = movie.child("runtime").value as Long?,
                            releaseDate = movie.child("releaseDate").value as List<String>?,
                            rating = movie.child("rating").value as Long?,
                            plot = movie.child("plot").value as String?
                        ))
                    }
                    if(list != null){
                        MoviesRepository.viewedMovies = list
                    } else {
                        MoviesRepository.viewedMovies = mutableListOf()
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    // Обработка ошибки
                }
            })
            if (MoviesRepository.viewedMovies != null) {
                for (movie in MoviesRepository.viewedMovies!!) {
                    MoviesRepository.viewedSet.add(movie.id!!)
                }
            }

        }

        fun writeUserData() {
            if(MoviesRepository.favoriteMovies?.isNotEmpty() == true) {
                val favoriteMovies : MutableList<LightMovie> = mutableListOf()
                for(movie in MoviesRepository.favoriteMovies!!) {
                    favoriteMovies.add(movie)
                }
                favoriteReference.setValue(favoriteMovies)
            }

            if(MoviesRepository.viewedMovies?.isNotEmpty() == true) {
                val viewedMovies : MutableList<LightMovie> = mutableListOf()
                for(movie in MoviesRepository.viewedMovies!!) {
                    viewedMovies.add(movie)
                }
                viewedReference.setValue(viewedMovies)
            } else {
                viewedReference.setValue(null)
            }
        }
    }
}