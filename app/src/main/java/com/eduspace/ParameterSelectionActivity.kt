package com.eduspace

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity

class ParameterSelectionActivity : AppCompatActivity() {
    private val planets = mutableListOf<RotatingSpheresView.Planet>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parameter_selection)

        val colors = listOf(
            Color.RED, Color.GREEN, Color.BLUE, Color.CYAN, Color.MAGENTA,
            Color.YELLOW, Color.BLACK, Color.WHITE, Color.GRAY, Color.DKGRAY,
            Color.LTGRAY, Color.parseColor("#FF5733"), Color.parseColor("#33FF57"),
            Color.parseColor("#5733FF"), Color.parseColor("#FFD700"), Color.parseColor("#008080"),
            Color.parseColor("#800080"), Color.parseColor("#FF00FF"), Color.parseColor("#00FFFF"),
            Color.parseColor("#FFFF00"), Color.parseColor("#FF1493"), Color.parseColor("#7FFF00"),
            Color.parseColor("#FF6347"), Color.parseColor("#B22222"), Color.parseColor("#00BFFF"),
            Color.parseColor("#4169E1"), Color.parseColor("#8A2BE2"), Color.parseColor("#5F9EA0"),
            Color.parseColor("#D2691E"), Color.parseColor("#FF4500"), Color.parseColor("#DAA520")
        )


        val radioGroupColors = findViewById<RadioGroup>(R.id.radioGroupColors)
        for (color in colors) {
            val radioButton = RadioButton(this).apply {

                setTextColor(if (color == Color.BLACK) Color.WHITE else Color.BLACK)
                text = "Color " + Integer.toHexString(color).toUpperCase()
                setBackgroundColor(color)

                tag = color
                setPadding(16, 16, 16, 16)
                layoutParams = RadioGroup.LayoutParams(
                    RadioGroup.LayoutParams.MATCH_PARENT,
                    RadioGroup.LayoutParams.WRAP_CONTENT
                )
            }


            radioGroupColors.addView(radioButton)
        }

        val planetContainer = findViewById<LinearLayout>(R.id.planet_container)
        val btnAddPlanet = findViewById<Button>(R.id.btn_add_planet)
        val btnStartSimulation = findViewById<Button>(R.id.btn_start_simulation)
        val btnClear = findViewById<Button>(R.id.btn_clear)
        val btn_delete = findViewById<Button>(R.id.btn_delete)



        val semiMajorAxis = findViewById<EditText>(R.id.input_semi_major_axis)
        val eccentricity = findViewById<EditText>(R.id.input_eccentricity)
        val orbitalSpeed = findViewById<EditText>(R.id.input_orbital_speed)

        val radius = findViewById<EditText>(R.id.input_radius)
        val color = findViewById<RadioGroup>(R.id.radioGroupColors)
        val nmbMoons = findViewById<EditText>(R.id.input_nmbMoons)

        btnClear.setOnClickListener{
            if (planets.isNotEmpty()) {
                planets.clear()
            }
        }
        btn_delete.setOnClickListener {
            if (planets.isNotEmpty()) {
                planets.removeAt(planets.size - 1)
            }
        }


        btnAddPlanet.setOnClickListener {



            if(setError()) {
                return@setOnClickListener
            }


            val semiMajorAxisValue = semiMajorAxis.text.toString().toFloatOrNull() ?: 0f
            val eccentricityValue = eccentricity.text.toString().toFloatOrNull() ?: 0f
            val orbitalSpeedValue = orbitalSpeed.text.toString().toFloatOrNull() ?: 0f
            val radiusValue = radius.text.toString().toFloatOrNull() ?: 0f
            val radiusValueInt = radius.text.toString().toIntOrNull() ?: 0
            val numberOfMoons = nmbMoons.text.toString().toIntOrNull() ?: 0

            // Get the selected color from RadioGroup
            val selectedRadioButtonId = color.checkedRadioButtonId
            val selectedRadioButton = findViewById<RadioButton>(selectedRadioButtonId)
            val colorValue = selectedRadioButton?.tag as? Int ?: Color.GRAY


            val moons = mutableListOf<RotatingSpheresView.Moon>()
            for (i in 0 until numberOfMoons) {
                moons.add(
                    RotatingSpheresView.Moon(
                        name = "Moon$i",
                        semiMajorAxis = (radiusValueInt * 2..(radiusValueInt * 2) + 300).random()
                            .toFloat(),
                        eccentricity = ((-50..50).random().toFloat()) / 100.0f,

                        orbitalSpeed = ((5..20).random()
                            .toFloat()) / 100.0f,
                        radius = (5..10).random().toFloat(),
                        color = Color.rgb(
                            (0..255).random(),
                            (0..255).random(),
                            (0..255).random()
                        )
                    )
                )
            }


            val newPlanet = RotatingSpheresView.Planet(
                name = "PlanetOne",
                semiMajorAxis = semiMajorAxisValue,
                eccentricity = eccentricityValue,
                orbitalSpeed = orbitalSpeedValue,
                radius = radiusValue,
                color = colorValue,
                moons = moons
            )


            planets.add(newPlanet)

        }

        btnStartSimulation.setOnClickListener {
            val intent = Intent(this, SimulationActivity::class.java).apply {
                putParcelableArrayListExtra("planets", ArrayList(planets))
            }
            startActivity(intent)
        }
    }

    private fun setError(): Boolean {
        var error = false
        val semiMajorAxis = findViewById<EditText>(R.id.input_semi_major_axis)
        val eccentricity = findViewById<EditText>(R.id.input_eccentricity)
        val orbitalSpeed = findViewById<EditText>(R.id.input_orbital_speed)
        val radius = findViewById<EditText>(R.id.input_radius)
        val nmbMoons = findViewById<EditText>(R.id.input_nmbMoons)

        if (semiMajorAxis.text.isNullOrEmpty()) {
            semiMajorAxis.error = "Semi-major axis cannot be empty."
            error = true
        } else {
            semiMajorAxis.error = null
        }

        if (eccentricity.text.isNullOrEmpty()) {
            eccentricity.error = "Eccentricity cannot be empty."
            error = true
        } else {
            eccentricity.error = null
        }

        if (orbitalSpeed.text.isNullOrEmpty()) {
            orbitalSpeed.error = "Orbital speed cannot be empty."
            error = true
        } else {
            orbitalSpeed.error = null
        }

        if (radius.text.isNullOrEmpty()) {
            radius.error = "Radius cannot be empty."
            error = true
        } else {
            radius.error = null
        }

        if (nmbMoons.text.isNullOrEmpty()) {
            nmbMoons.error = "Number of moons cannot be empty."
            error = true
        } else {
            nmbMoons.error = null
        }

        return error
    }

}
