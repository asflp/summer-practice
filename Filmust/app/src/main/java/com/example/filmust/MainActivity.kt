package com.example.filmust

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.filmust.fragments.ProfileFragment
import com.example.filmust.workdata.FirebaseManager
import com.example.filmust.workdata.MoviesRepository
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private var bottomNavigationView: BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle? ) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createRepo()

        bottomNavigationView = findViewById<BottomNavigationView>(R.id.bnv_main)

        hideBottomNavigation()

        val controller = (supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment)
            .navController

        findViewById<BottomNavigationView>(R.id.bnv_main).apply {
            setupWithNavController(controller)
        }

    }

    override fun onPause() {
        super.onPause()

        sendRepo()
    }

    override fun onDestroy() {
        super.onDestroy()

        sendRepo()
    }

    fun hideBottomNavigation() {
        bottomNavigationView?.visibility = View.GONE
    }

    fun showBottomNavigation() {
        bottomNavigationView?.visibility = View.VISIBLE
    }

    fun createRepo(){
        val prefs = getSharedPreferences("MyPrefs", AppCompatActivity.MODE_PRIVATE)
        val login = prefs.getString("userLogin", "")
        if(login == ""){
            ProfileFragment.login = login
        }
    }

    fun sendRepo(){
        if(ProfileFragment.login != ""){
            FirebaseManager.writeUserData()
        }
        val login = ProfileFragment.login
        val prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        prefs.edit().putString("userLogin", login).apply()
    }
}
