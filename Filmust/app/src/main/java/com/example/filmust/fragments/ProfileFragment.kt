package com.example.filmust.fragments

import android.os.Bundle
import android.util.Log
import com.example.filmust.R
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.filmust.databinding.FragmentProfileBinding
import com.example.filmust.workdata.FirebaseManager
import com.example.filmust.workdata.LightMovie
import com.example.filmust.workdata.Movie
import com.example.filmust.workdata.MovieAdapter
import com.example.filmust.workdata.MoviesRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private var binding: FragmentProfileBinding? = null
    private var adapter: MovieAdapter? = null
    private lateinit var rootNode: FirebaseDatabase
    private lateinit var reference: DatabaseReference

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentProfileBinding.bind(view)
        putStrings()

        if(MoviesRepository.viewedMovies?.isNotEmpty() == true){
            initAdapter(MoviesRepository.viewedMovies!!.toList())
        }

        binding!!.ivExit.setOnClickListener{
            FirebaseManager.writeUserData()
            login = ""
            findNavController().navigate(
                R.id.action_profileFragment_to_signInFragment,
                SignInFragment.createBundle(login, "")
            )
        }

//        binding!!.cardView.setOnClickListener{
//
//        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        var login: String = ""
    }

    fun putStrings(){
        binding!!.tvEmail.setText("@$login")
        rootNode = FirebaseDatabase.getInstance()
        reference = rootNode.getReference("users")
        val query = reference.orderByChild("login").equalTo(login)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val userName = dataSnapshot.child(login).child("name").getValue(String::class.java)
                    binding!!.tvName.setText(userName)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e("TAG", error.message, error.toException())
            }
        })
    }

    private fun initAdapter(listOfMovies : List<LightMovie>) {
        adapter = MovieAdapter(
            listOfMovies = listOfMovies,
            glide = Glide.with(this),
            onItemClick = {
                val bundle = Bundle()
                bundle.putString("MOVIE_ID", it)
                findNavController().navigate(
                    R.id.action_profileFragment_to_detailFragment,
                    bundle
                )
            })
        binding?.rvViewed?.adapter = adapter
    }

}
