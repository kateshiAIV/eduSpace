package com.eduspace


import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.eduspace.databinding.ActivityMainBinding
import com.eduspace.fragments.games
import com.google.android.filament.utils.Utils
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.eduspace.fragments.news
import com.eduspace.fragments.picture
import com.eduspace.fragments.quiz
import com.eduspace.fragments.quizAndPic
import com.eduspace.fragments.solarMap
import com.eduspace.fragments.starMap
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    companion object {
        init {
            Utils.init()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        makeCurrentFragment(solarMap())



        // Set up the BottomNavigationView
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.selectedItemId = R.id.solar_map

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.news -> makeCurrentFragment(news())
                R.id.star_map -> makeCurrentFragment(starMap())
                R.id.solar_map -> makeCurrentFragment(solarMap())
                R.id.quiz -> makeCurrentFragment(quizAndPic())
                R.id.picture -> makeCurrentFragment(games())

            }
            true
        }
    }


    private fun makeCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            Firebase.auth.signOut()
            commit()
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()

        finishAffinity()
    }
}

