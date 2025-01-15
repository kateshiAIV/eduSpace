package com.eduspace.fragments

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils.replace
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.eduspace.ParameterSelectionActivity
import com.eduspace.R
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


class quizAndPic : Fragment() {

    private lateinit var leftImage: ImageButton
    private lateinit var rightImage: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        switchPlanetFragment(view, "Picture")

    }

    private fun switchPlanetFragment(view: View?, s: String) {
        val fragment = when (s) {
            "Quiz" -> quiz()
            "Picture"-> picture()


            else -> null
        }

        fragment?.let {
            childFragmentManager.beginTransaction().apply {
                replace(R.id.fl_wrapper, it)
                commit()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_quiz_and_pic, container, false)
        leftImage = view.findViewById(R.id.left_image)
        rightImage = view.findViewById(R.id.right_image)





        leftImage.setOnClickListener {
            leftImage.visibility = View.GONE
            rightImage.visibility = View.VISIBLE
            switchPlanetFragment(view,"Picture")
        }
        rightImage.setOnClickListener {
            leftImage.visibility = View.VISIBLE
            rightImage.visibility = View.GONE
            switchPlanetFragment(view,"Quiz")
        }

        return view
    }


}