package com.eduspace.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import android.widget.Switch
import com.eduspace.R
import androidx.fragment.app.Fragment
import com.eduspace.fragments.planetFragments.Earth
import com.eduspace.fragments.planetFragments.EarthMoon
import com.eduspace.fragments.planetFragments.Jupiter
import com.eduspace.fragments.planetFragments.Mars
import com.eduspace.fragments.planetFragments.MarsDeimos
import com.eduspace.fragments.planetFragments.MarsPhobos
import com.eduspace.fragments.planetFragments.Mercury
import com.eduspace.fragments.planetFragments.Neptune
import com.eduspace.fragments.planetFragments.Saturn
import com.eduspace.fragments.planetFragments.SolarSys
import com.eduspace.fragments.planetFragments.Sun
import com.eduspace.fragments.planetFragments.Uranus
import com.eduspace.fragments.planetFragments.Venus
import com.eduspace.fragments.planetFragments.jupiter.JupiterCallisto
import com.eduspace.fragments.planetFragments.jupiter.JupiterEuropa
import com.eduspace.fragments.planetFragments.jupiter.JupiterGanymede
import com.eduspace.fragments.planetFragments.jupiter.JupiterIo
import com.eduspace.fragments.planetFragments.neptune.NeptuneTriton
import com.eduspace.fragments.planetFragments.saturn.SaturnDione
import com.eduspace.fragments.planetFragments.saturn.SaturnEnceladus
import com.eduspace.fragments.planetFragments.saturn.SaturnLapetus
import com.eduspace.fragments.planetFragments.saturn.SaturnMimas
import com.eduspace.fragments.planetFragments.saturn.SaturnRhea
import com.eduspace.fragments.planetFragments.saturn.SaturnTethys
import com.eduspace.fragments.planetFragments.saturn.SaturnTitan
import com.eduspace.fragments.planetFragments.uranus.UranusAriel
import com.eduspace.fragments.planetFragments.uranus.UranusMiranda
import com.eduspace.fragments.planetFragments.uranus.UranusOberon
import com.eduspace.fragments.planetFragments.uranus.UranusTitania
import com.eduspace.fragments.planetFragments.uranus.UranusUmbriel
import com.eduspace.fragments.starMapFragments.constellations_list

class starMap : Fragment() {

    private lateinit var webView: WebView
    private lateinit var switch: Switch

    private lateinit var frameLayout: FrameLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_star_map, container, false)

        webView = view.findViewById(R.id.webView)
        frameLayout = view.findViewById(R.id.fl_wrapper)
        webView.webViewClient = WebViewClient()


        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true

        // Load Stellarium Web
        webView.loadUrl("https://stellarium-web.org")

        switch = view.findViewById(R.id.switch1)


        webView.visibility = View.GONE


        switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                webView.visibility = View.VISIBLE
            } else {
                webView.visibility = View.GONE
                switchPlanetFragment(view,"True")
            }
        }

        return view
    }


    private fun switchPlanetFragment(view: View?, s: String) {
        val fragment = when (s) {
            "True" -> constellations_list()

            else -> null
        }

        fragment?.let {
            childFragmentManager.beginTransaction().apply {
                replace(R.id.fl_wrapper, it)
                commit()
            }
        }
    }



}
