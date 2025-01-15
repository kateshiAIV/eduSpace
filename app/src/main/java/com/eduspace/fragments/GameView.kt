package com.eduspace.views

import android.content.Context
import android.graphics.*
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceView
import com.eduspace.R

class GameView(context: Context, attrs: AttributeSet? = null) : SurfaceView(context, attrs), Runnable, SensorEventListener {
    private val thread = Thread(this)
    private var isRunning = false
    private val screenWidth = resources.displayMetrics.widthPixels.toFloat()
    private val screenHeight = resources.displayMetrics.heightPixels.toFloat()
    private var highestPoint: Float = 0f
    private val player = Player(screenWidth/2, 0f, screenWidth, context)
    private val platforms = mutableListOf<Platform>()
    private var score = 0
    private var platformC = 0
    private var lost = false

    init {
        // Generate initial platforms
        val platformCount = 8
        val gap = screenHeight / platformCount
        for (i in 1..platformCount) {
            val platformX = (200f*i)-200f
            val platformY = screenHeight/2
            platforms.add(Platform(platformX, platformY,context))
        }
    }

    private var touchX = 0f
    private var touchY = 0f

    private lateinit var sensorManager: SensorManager
    private lateinit var accelerometer: Sensor

    fun resume() {
        isRunning = true
        thread.start()

        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)!!
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI)
    }

    fun pause() {
        isRunning = false
        thread.join()
        sensorManager.unregisterListener(this)
    }

    override fun run() {
        while (isRunning) {
            if (score < -player.y.toInt()) {

                score = -player.y.toInt()

            }


            player.update(platforms)
            createNewPlatforms(score)  // Check and create new platforms
            drawGame()
            Thread.sleep(16)  // ~60 FPS
        }
    }

    private fun createNewPlatforms(platformC: Int) {
        // Create more platforms as the player moves up the screen
        if (player.y < platforms.last().y + screenHeight) {
            val platformX = (0..(screenWidth - 200).toInt()).random().toFloat()

            var platformY = platforms.last().y - screenHeight / 6
            if (score < 6000){
                platformY = platforms.last().y - screenHeight / 6
            }
            else if (score > 6000 &&  score < 12000){
                platformY = platforms.last().y - screenHeight / 5
            }
            else if (score > 12000 &&  score < 18000) {
                platformY = platforms.last().y - screenHeight / 4
            }
            else if (score > 18000 &&  score < 24000){
                 platformY = platforms.last().y - screenHeight / 3
            }
            else if (score > 24000){
                platformY = platforms.last().y - screenHeight / 2
            }

            platforms.add(Platform(platformX, platformY,context))
        }

        // Optionally, remove platforms that are too far off-screen
        if (platforms.first().y > player.y + (screenHeight/2.3)) {
            platforms.removeAt(0)
        }
    }

    private fun drawGame() {
        if (holder.surface.isValid) {
            val canvas = holder.lockCanvas()
            canvas.drawColor(Color.BLACK)

            // Draw the score above everything else
            if(!lost){
                drawScore(canvas)
            }

            if(-player.y < score - 2200){
                lost = true
                drawLose(canvas)
            }

            val offsetY = screenHeight / 2 - player.y
            canvas.translate(0f, offsetY)

            // Draw the platforms and player
            for (platform in platforms) {
                platform.draw(canvas)
            }

            player.draw(canvas)

            holder.unlockCanvasAndPost(canvas)
        }
    }

    private fun drawScore(canvas: Canvas) {
        val paint = Paint().apply {
            color = Color.WHITE
            textSize = 60f
            textAlign = Paint.Align.CENTER
        }
        val x = screenWidth / 2
        canvas.drawText("Score: $score", x, 100f, paint)
    }
    private fun drawLose(canvas: Canvas) {
        val paint = Paint().apply {
            color = Color.WHITE
            textSize = 100f  // Increased text size
            textAlign = Paint.Align.CENTER
            style = Paint.Style.FILL_AND_STROKE
            strokeWidth = 5f  // Adding a stroke around the text for better visibility
        }

        // Prepare the text to display
        val messageLine1 = "You Lost"
        val messageLine2 = "Your Score: $score"

        // Calculate text dimensions
        val textHeight = paint.fontMetrics.descent - paint.fontMetrics.ascent
        val textWidthLine1 = paint.measureText(messageLine1)
        val textWidthLine2 = paint.measureText(messageLine2)

        // Calculate X and Y coordinates to center the text
        val x = screenWidth / 2f
        val y = screenHeight / 2f - textHeight / 2

        // Draw a background rectangle for the text (optional for better visibility)
        val backgroundPaint = Paint().apply {
            color = Color.argb(200, 0, 0, 0)  // Semi-transparent black background
        }
        val padding = 20f
        canvas.drawRect(
            x - textWidthLine1 / 2 - padding, y - textHeight / 2 - padding,
            x + textWidthLine1 / 2 + padding, y + textHeight / 2 + padding,
            backgroundPaint
        )

        // Draw shadow effect for text
        paint.setShadowLayer(10f, 5f, 5f, Color.BLACK)

        // Draw the first line of text ("You Lost")
        canvas.drawText(messageLine1, x, y, paint)

        // Adjust Y for the second line of text
        val secondLineY = y + textHeight + 20f  // Adding a little space between the lines
        // Draw the second line of text ("Your Score")
        canvas.drawText(messageLine2, x, secondLineY, paint)

        // Remove shadow effect for other drawings (optional)
        paint.clearShadowLayer()
    }





    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                player.jump()  // Jump once when the screen is first touched
            }
        }
        return true
    }

    // SensorEventListener methods
    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null && event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            val xAxis = event.values[0]
            if (xAxis < 0) {
                player.moveLeft(-(xAxis * 8))
            } else if (xAxis > 0) {
                player.moveRight(-(xAxis * 8))
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Handle accuracy changes if necessary
    }
}


class Player(var x: Float, var y: Float, private val screenWidth: Float, context: Context) {
    private val jumpForce = 50f
    private val paint = Paint().apply { color = Color.YELLOW }
    private var velocity = -50f
    private val gravity = 1f
    private val radius = 50f // Player's size
    private var currentBitmap: Bitmap
    private val shuttleBitmap: Bitmap
    private val shuttlep45Bitmap: Bitmap
    private val shuttlep90Bitmap: Bitmap
    private val shuttlep135Bitmap: Bitmap
    private val shuttlep180Bitmap: Bitmap
    private val shuttlen45Bitmap: Bitmap
    private val shuttlen90Bitmap: Bitmap
    private val shuttlen135Bitmap: Bitmap

    init {


        // Load the shuttle image
        currentBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.shuttle0)
        shuttleBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.shuttle0)
        shuttlep45Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.shuttlep45)
        shuttlep90Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.shuttlep90)
        shuttlep135Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.shuttlep135)
        shuttlep180Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.shuttlep180)
        shuttlen45Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.shuttlen45)
        shuttlen90Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.shuttlen90)
        shuttlen135Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.shuttlen135)
    }

    fun getVelocity(): Float {
        return velocity
    }

    fun update(platforms: List<Platform>) {
        velocity += gravity
        y += velocity   // Update the vertical position based on velocity

        // Check for potential collisions with platforms
        for (platform in platforms) {
            // Check if the player is falling toward the platform
            if (y + radius >= platform.y && y + radius <= platform.y + platform.getHeight()) {
                val minX = platform.x - radius
                val maxX = platform.x + platform.getWidth()

                // Check if the player's x position is within the platform's x bounds
                if (x >= minX && x <= maxX) {
                    // Stop just before hitting the platform
                    if (velocity > 0) {
                        y = platform.y - radius  // Set the player's position directly above the platform
                        velocity = 0f  // Stop downward movement
                        jump()  // Trigger the jump after landing on the platform
                    }
                    break  // Exit the loop once a platform is found
                }
            }
        }

        // If the player is moving too fast and passing through platforms, adjust velocity
        if (velocity > 40f) {  // Add a cap to the falling speed
            velocity = 40f
        }
    }

    fun jump() {
        this.velocity -= this.jumpForce
    }

    fun moveLeft(xAxis: Float) {

        x += xAxis

        if (x - radius > screenWidth) {  // If player moves past the right side
            x = -radius  // Spawn at the left side
        }

        when {
            velocity < -30 -> {
                currentBitmap = shuttleBitmap
            }
            velocity < -10  && velocity > -30 ->{
                currentBitmap = shuttlep45Bitmap
            }
            velocity < 10  && velocity > -10 ->{
                currentBitmap = shuttlep90Bitmap
            }
            velocity < 20  && velocity > 10 ->{
                currentBitmap = shuttlep135Bitmap
            }
            velocity > 20 ->{
                currentBitmap = shuttlep180Bitmap
            }

        }
    }

    fun moveRight(xAxis: Float) {
        x += xAxis
        if (x + radius < 0) {  // If player moves past the left side
            x = screenWidth + radius  // Spawn at the right side
        }

        when {
            velocity < -30 -> {
                currentBitmap = shuttleBitmap
            }
            velocity < -10  && velocity > -30 ->{
                currentBitmap = shuttlen45Bitmap
            }
            velocity < 10  && velocity > -10 ->{
                currentBitmap = shuttlen90Bitmap
            }
            velocity < 20  && velocity > 10 ->{
                currentBitmap = shuttlen135Bitmap
            }
            velocity > 20 ->{
                currentBitmap = shuttlep180Bitmap
            }

        }


    }

    fun draw(canvas: Canvas) {
        // Draw the circle


        // Scale the shuttle bitmap to match the circle's size
        val scaledShuttleBitmap = Bitmap.createScaledBitmap(currentBitmap, (radius * 4).toInt(), (radius * 4).toInt(), true)

        val shuttleX = x - radius  // Align the left edge
        val shuttleY = y - radius  // Align the top edge

        // Draw the scaled shuttle bitmap
        canvas.drawBitmap(scaledShuttleBitmap, shuttleX, shuttleY, null)
    }
}




class Platform(var x: Float, var y: Float, context: Context) {
    private val paint = Paint().apply { color = Color.GREEN }
    private val height = 10f // Platform height
    private val width = 200f // Platform width
    private val bitmaps: List<Bitmap>
    private val selectedBitmap: Bitmap

    init {
        // Load all planet bitmaps
        val earth = BitmapFactory.decodeResource(context.resources, R.drawable.earth)
        val jupiter = BitmapFactory.decodeResource(context.resources, R.drawable.jupiter)
        val mars = BitmapFactory.decodeResource(context.resources, R.drawable.mars)
        val mercury = BitmapFactory.decodeResource(context.resources, R.drawable.mercury)
        val neptune = BitmapFactory.decodeResource(context.resources, R.drawable.neptune)
        val saturn = BitmapFactory.decodeResource(context.resources, R.drawable.saturn)
        val uranus = BitmapFactory.decodeResource(context.resources, R.drawable.uranus)
        val venus = BitmapFactory.decodeResource(context.resources, R.drawable.venus)

        // Store all bitmaps in a list
        bitmaps = listOf(earth, jupiter, mars, mercury, neptune, saturn, uranus, venus)

        // Select a random bitmap from the list
        selectedBitmap = bitmaps.random()
    }

    // Draw the platform as a rectangle
    fun getHeight(): Float {
        return height
    }

    fun getWidth(): Float {
        return width
    }

    fun draw(canvas: Canvas) {
        // Scale the selected bitmap to fit the platform size
        val scaledBitmap = Bitmap.createScaledBitmap(selectedBitmap, width.toInt(), width.toInt(), true)

        // Draw the scaled bitmap
        val bitmapX = x  // Align the left edge
        val bitmapY = y - width/2 // Align the top edge
        canvas.drawBitmap(scaledBitmap, bitmapX, bitmapY, null)


    }
}





