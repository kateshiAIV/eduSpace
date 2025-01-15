package com.eduspace

import android.os.Bundle
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import androidx.appcompat.app.AppCompatActivity

class SimulationActivity : AppCompatActivity() {

    private lateinit var scaleGestureDetector: ScaleGestureDetector
    private var scaleFactor = 1.0f
    private var lastTouchX = 0f
    private var lastTouchY = 0f
    private var offsetX = 0f
    private var offsetY = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simulation)


        scaleGestureDetector = ScaleGestureDetector(this, ScaleListener())

        val planets = intent.getParcelableArrayListExtra<RotatingSpheresView.Planet>("planets")


        val rotatingSpheresView = findViewById<RotatingSpheresView>(R.id.simulation_view)
        planets?.forEach { planet ->
            rotatingSpheresView.updatePlanets(planet)
        }
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        scaleGestureDetector.onTouchEvent(event)

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                lastTouchX = event.x
                lastTouchY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = event.x - lastTouchX
                val dy = event.y - lastTouchY


                offsetX += dx
                offsetY += dy

                lastTouchX = event.x
                lastTouchY = event.y


                val rotatingSpheresView = findViewById<RotatingSpheresView>(R.id.simulation_view)
                rotatingSpheresView.setOffset(offsetX, offsetY)
            }
        }
        return super.onTouchEvent(event)
    }

    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {

            scaleFactor *= detector.scaleFactor
            scaleFactor = scaleFactor.coerceIn(0.01f, 10.0f)

            val rotatingSpheresView = findViewById<RotatingSpheresView>(R.id.simulation_view)
            rotatingSpheresView.setZoom(scaleFactor)

            return true
        }
    }
}


