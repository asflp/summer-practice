package com.example.filmust.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.filmust.R
import com.example.filmust.workdata.User
import com.example.filmust.databinding.FragmentSignUpBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private var binding: FragmentSignUpBinding? = null
    private lateinit var rootNode: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private var name: String = ""
    private var login: String = ""
    private var password: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSignUpBinding.bind(view)
        putStrings()

        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bnv_main)
        bottomNavigationView?.visibility = View.GONE

        rootNode = FirebaseDatabase.getInstance()
        reference = rootNode.getReference("users")

        val etName = binding!!.etName
        val tiName = binding!!.tiName

        etName.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) {
                name = etName.text.toString().trim()
                if (name.isEmpty()) {
                    tiName.error = "Name can't be empty"
                } else if (name.length > 30) {
                    tiName.error = "Name is too long"
                } else if (!name.matches("[a-zA-Zа-яА-Я]+".toRegex())) {
                    tiName.error = "Name should consist only of letters"
                } else {
                    tiName.error = null
                    etName.setText(name)
                }
            }
        }

        val etLogin = binding!!.etLogin
        val tiLogin = binding!!.tiLogin

        etLogin.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) {
                login = etLogin.text.toString().trim()
                val query = reference.orderByChild("login").equalTo(login)
                query.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()) {
                            tiLogin.error = "Login is already registered"
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {
                        Log.e("TAG", error.message, error.toException())
                    }
                })
                if (login.isEmpty()) {
                    tiLogin.error = "Login can't be empty"
                } else if (login.length > 30) {
                    tiLogin.error = "Login is too long"
                } else if (!login.matches("[A-Za-z._]+".toRegex())) {
                    tiLogin.error = "Login should consist only of english letters and \".\", \"_\""
                } else {
                    tiLogin.error = null
                    etLogin.setText(login)
                }
            }
        }

        val etPass = binding!!.etPassword
        val tiPass = binding!!.tiPassword

        etPass.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) {
                password = etPass.text.toString().trim()
                if (password.isEmpty()) {
                    tiPass.error = "Password can't be empty"
                } else if (password.length < 8) {
                    tiPass.error = "Password must consist of at least 8 characters"
                } else {
                    tiPass.error = null
                    etPass.setText(password)
                }
            }
        }

        val etConfirmPass = binding!!.etConfirmPassword
        val tiConfirmPass = binding!!.tiConfirmPassword

        binding!!.btSignUp.setOnClickListener {
            etLogin.clearFocus()
            etPass.clearFocus()
            etName.clearFocus()
            etConfirmPass.clearFocus()

            val confirmPass = etConfirmPass.text.toString().trim()
            if (confirmPass.isEmpty()) {
                tiConfirmPass.error = "Password can't be empty"
            } else if (confirmPass != password) {
                tiConfirmPass.error = "Passwords don't match"
            } else {
                tiConfirmPass.error = null
            }

            if (tiName.error == null && tiLogin.error == null && tiPass.error == null && tiConfirmPass.error == null) {
                val user = User(name, login, password.hashCode().toString())
                reference.child(login).setValue(user)

                ProfileFragment.login = login
                findNavController().navigate(R.id.action_signUpFragment_to_searchFragment)
            } else {
                Snackbar.make(
                    binding!!.root, "Correct errors!",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        binding!!.btSignIn.setOnClickListener {
            findNavController().navigate(
                R.id.action_signUpFragment_to_signInFragment,
                SignInFragment.createBundle(login, password)
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

    private fun putStrings() {
        val loginStr = arguments?.getString(ARG_LOGIN)
        binding!!.etLogin.setText(loginStr)
        val passStr = arguments?.getString(ARG_PASSWORD)
        binding!!.etPassword.setText(passStr)
    }
}
