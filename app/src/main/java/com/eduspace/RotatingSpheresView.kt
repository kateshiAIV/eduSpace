package com.eduspace

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import android.view.View
import kotlin.math.*

class RotatingSpheresView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private val planets = mutableListOf<Planet>()
    private val paintPlanet = Paint().apply {
        style = Paint.Style.FILL
    }
    private val paintTrace = Paint().apply {
        color = Color.LTGRAY
        style = Paint.Style.STROKE
        strokeWidth = 10f
    }
    private var timeElapsed = 0f
    private var scaleFactor = 1.0f
    private var offsetX = 0f
    private var offsetY = 0f

    fun setZoom(scaleFactor: Float) {
        this.scaleFactor = scaleFactor
        invalidate()
    }

    fun setOffset(offsetX: Float, offsetY: Float) {
        this.offsetX = offsetX
        this.offsetY = offsetY
        invalidate()
    }

    init {
        initializePlanets()
    }

    fun updatePlanets(planet: Planet) {
        planets.add(planet)
        invalidate()
    }

    private fun initializePlanets() {
        planets.clear()

        // Sun (center of the system)
        planets.add(
            Planet(
                name = "Sun",
                semiMajorAxis = 0f,
                eccentricity = 0f,
                orbitalSpeed = 0f,
                radius = 250f,
                color = Color.YELLOW
            )
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val centerX = width / 2f + offsetX
        val centerY = height / 2f + offsetY
        canvas.scale(scaleFactor, scaleFactor, centerX, centerY)


        for (planet in planets) {
            if (planet.semiMajorAxis > 0) {
                val angle = planet.orbitalSpeed * timeElapsed
                val radius = (planet.semiMajorAxis * (1 - planet.eccentricity.pow(2))) /
                        (1 + planet.eccentricity * cos(angle))
                val x = centerX + radius * cos(angle)
                val y = centerY + radius * sin(angle)


                paintPlanet.color = planet.color

                var previousX = x
                var previousY = y
                for (i in 1..12) {
                    val angleT = planet.orbitalSpeed * (timeElapsed + i * 2500f)
                    val radiusT = (planet.semiMajorAxis * (1 - planet.eccentricity.pow(2))) /
                            (1 + planet.eccentricity * cos(angleT))
                    val xT = centerX + radiusT * cos(angleT)
                    val yT = centerY + radiusT * sin(angleT)

                    canvas.drawLine(previousX, previousY, xT, yT, paintTrace)

                    previousX = xT
                    previousY = yT
                }

                canvas.drawCircle(x, y, planet.radius, paintPlanet)


                planet.moons.forEach { moon ->
                    val moonAngle = moon.orbitalSpeed * timeElapsed
                    val moonRadius = (moon.semiMajorAxis * (1 - moon.eccentricity.pow(2))) /
                            (1 + moon.eccentricity * cos(moonAngle))
                    val moonX = x + moonRadius * cos(moonAngle)
                    val moonY = y + moonRadius * sin(moonAngle)

                    paintPlanet.color = moon.color
                    canvas.drawCircle(moonX, moonY, moon.radius, paintPlanet)
                }
            } else {

                paintPlanet.color = planet.color
                canvas.drawCircle(centerX, centerY, planet.radius, paintPlanet)
            }
        }


        timeElapsed += 1f


        invalidate()
    }

    data class Planet(
        val name: String,
        val semiMajorAxis: Float,
        val eccentricity: Float,
        val orbitalSpeed: Float,
        val radius: Float,
        val color: Int,
        val moons: List<Moon> = emptyList()
    ) : Parcelable {
        constructor(parcel: Parcel) : this(
            name = parcel.readString() ?: "",
            semiMajorAxis = parcel.readFloat(),
            eccentricity = parcel.readFloat(),
            orbitalSpeed = parcel.readFloat(),
            radius = parcel.readFloat(),
            color = parcel.readInt(),
            moons = parcel.createTypedArrayList(Moon) ?: emptyList()
        )

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(name)
            parcel.writeFloat(semiMajorAxis)
            parcel.writeFloat(eccentricity)
            parcel.writeFloat(orbitalSpeed)
            parcel.writeFloat(radius)
            parcel.writeInt(color)
            parcel.writeTypedList(moons)
        }

        override fun describeContents(): Int = 0

        companion object CREATOR : Parcelable.Creator<Planet> {
            override fun createFromParcel(parcel: Parcel): Planet = Planet(parcel)
            override fun newArray(size: Int): Array<Planet?> = arrayOfNulls(size)
        }
    }

    data class Moon(
        val name: String,
        val semiMajorAxis: Float,
        val eccentricity: Float,
        val orbitalSpeed: Float,
        val radius: Float,
        val color: Int
    ) : Parcelable {
        constructor(parcel: Parcel) : this(
            name = parcel.readString() ?: "",
            semiMajorAxis = parcel.readFloat(),
            eccentricity = parcel.readFloat(),
            orbitalSpeed = parcel.readFloat(),
            radius = parcel.readFloat(),
            color = parcel.readInt()
        )

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(name)
            parcel.writeFloat(semiMajorAxis)
            parcel.writeFloat(eccentricity)
            parcel.writeFloat(orbitalSpeed)
            parcel.writeFloat(radius)
            parcel.writeInt(color)
        }

        override fun describeContents(): Int = 0

        companion object CREATOR : Parcelable.Creator<Moon> {
            override fun createFromParcel(parcel: Parcel): Moon = Moon(parcel)
            override fun newArray(size: Int): Array<Moon?> = arrayOfNulls(size)
        }
    }
}

