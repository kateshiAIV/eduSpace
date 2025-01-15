package com.eduspace.fragments.planetFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.SurfaceView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.filament.utils.Utils
import com.eduspace.ModelRenderer
import com.eduspace.R
import com.google.android.material.navigation.NavigationView


private val planets = listOf("SolarSys","Sun","Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune")
class Saturn : Fragment() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toggle: ActionBarDrawerToggle

    private var currentPlanetIndex = 7


    companion object {
        init {
            Utils.init()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {




        val view = inflater.inflate(R.layout.fragment_saturn, container, false)


        displayObject(view, planets[currentPlanetIndex])






        return view

    }


    private fun displayObject(view: View, planetName: String) {

        val surfaceView = view.findViewById<SurfaceView>(R.id.surfaceView)

        
        val renderer = ModelRenderer()
        renderer.onSurfaceAvailable(surfaceView, lifecycle, planetName)
    }
}