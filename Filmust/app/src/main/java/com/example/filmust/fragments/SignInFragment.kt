package com.example.filmust.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.filmust.MainActivity
import com.example.filmust.R
import com.example.filmust.databinding.FragmentSignInBinding
import com.example.filmust.workdata.FirebaseManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private var binding: FragmentSignInBinding? = null
    private lateinit var rootNode: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var firebaseManager: FirebaseManager
    private var password: String = ""
    private var login: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSignInBinding.bind(view)
        putStrings()

        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bnv_main)
        bottomNavigationView?.visibility = View.GONE

        rootNode = FirebaseDatabase.getInstance()
        reference = rootNode.getReference("users")

        val etLogin = binding!!.etLogin1
        val tiLogin = binding!!.tiLogin1

        etLogin.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) {
                login = etLogin.text.toString().trim()
                if (login.isEmpty()) {
                    tiLogin.error = "Login can't be empty"
                } else if(login.length > 30) {
                    tiLogin.error = "Login is too long"
                } else if(!login.matches("[A-Za-z._]+".toRegex())){
                    tiLogin.error = "Login should consist only of english letters and \".\", \"_\""
                } else {
                    tiLogin.error = null
                    etLogin.setText(login)
                }
            }
        }

        val etPass = binding!!.etPassword1
        val tiPass = binding!!.tiPassword1

        etPass.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) {
                password = etPass.text.toString().trim()
                if (password.isEmpty()) {
                    tiPass.error = "Password can't be empty"
                } else if(password.length < 8) {
                    tiPass.error = "Password must consist of at least 8 characters"
                } else {
                    tiPass.error = null
                    etPass.setText(password)
                }
            }
        }


        binding!!.btSignIn1.setOnClickListener{
            etLogin.clearFocus()
            etPass.clearFocus()

            val query = reference.orderByChild("login").equalTo(login)
            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        val passwordFromBD = dataSnapshot.child(login).child("password").getValue(String::class.java)
                        if(passwordFromBD != password.hashCode().toString()){
                            tiPass.error = "Invalid password"
                        } else{
                            ProfileFragment.login = login
                            FirebaseManager.readUserData()
                            findNavController().navigate(R.id.action_signInFragment_to_searchFragment)
                        }
                    } else {
                        tiLogin.error = "This login wasn't found"
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    Log.e("TAG", error.message, error.toException())
                }
            })

            if(tiLogin.error != null || tiPass.error!= null) {
                Snackbar.make(
                    binding!!.root, "Correct errors!",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        binding!!.btSignUp1.setOnClickListener{
            findNavController().navigate(
                R.id.action_signInFragment_to_signUpFragment,
                SignUpFragment.createBundle(login, password)
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {

        private const val ARG_LOGIN = "ARG_LOGIN"
        private const val ARG_PASSWORD = "ARG_PASSWORD"

        fun createBundle(login: String, password: String): Bundle {
            val bundle = Bundle()
            bundle.putString(ARG_LOGIN, login)
            bundle.putString(ARG_PASSWORD, password)
            return bundle
        }
    }

    fun putStrings(){
        val loginStr = arguments?.getString(ARG_LOGIN)
        binding!!.etLogin1.setText(loginStr)
        val passStr = arguments?.getString(ARG_PASSWORD)
        binding!!.etPassword1.setText(passStr)
    }
}
