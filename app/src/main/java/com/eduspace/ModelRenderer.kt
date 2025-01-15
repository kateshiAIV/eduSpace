package com.eduspace

import android.content.res.AssetManager
import android.view.Choreographer
import android.view.SurfaceView
import androidx.compose.material3.lightColorScheme
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.google.android.filament.Skybox
import com.google.android.filament.View
import com.google.android.filament.android.UiHelper
import com.google.android.filament.utils.ModelViewer
import java.nio.ByteBuffer
import java.nio.ByteOrder
import com.google.android.filament.gltfio.Animator


class ModelRenderer {
    private lateinit var surfaceView: SurfaceView
    private lateinit var lifecycle: Lifecycle

    private lateinit var choreographer: Choreographer
    private lateinit var uiHelper: UiHelper

    private lateinit var modelViewer: ModelViewer
    private var animator: Animator? = null
    private var animationDuration: Float = 0.0f
    private var animationStartTime: Long = 0

    private val assets: AssetManager
        get() = surfaceView.context.assets

    private val frameScheduler = FrameCallback()

    private val lifecycleObserver = object : DefaultLifecycleObserver {
        override fun onResume(owner: LifecycleOwner) {
            choreographer.postFrameCallback(frameScheduler)
        }

        override fun onPause(owner: LifecycleOwner) {
            choreographer.removeFrameCallback(frameScheduler)
        }

        override fun onDestroy(owner: LifecycleOwner) {
            choreographer.removeFrameCallback(frameScheduler)
            lifecycle.removeObserver(this)
        }
    }

    fun onSurfaceAvailable(surfaceView: SurfaceView, lifecycle: Lifecycle, planetName: String) {
        this.surfaceView = surfaceView
        this.lifecycle = lifecycle

        lifecycle.addObserver(lifecycleObserver)

        choreographer = Choreographer.getInstance()
        uiHelper = UiHelper(UiHelper.ContextErrorPolicy.DONT_CHECK).apply {

            isOpaque = false
        }

        modelViewer = ModelViewer(surfaceView = surfaceView, uiHelper = uiHelper)


        surfaceView.setOnTouchListener { _, event ->
            modelViewer.onTouchEvent(event)
            true
        }

        modelViewer.scene.skybox = null/// Skybox.Builder().color(0f, 0f, 0f, 1f).build(modelViewer.engine) //null


        modelViewer.view.blendMode = View.BlendMode.TRANSLUCENT // OPAQUE after lights
        modelViewer.renderer.clearOptions = modelViewer.renderer.clearOptions.apply {
            clear = true
        }


        modelViewer.view.apply {
            renderQuality = renderQuality.apply {
                hdrColorBuffer = View.QualityLevel.ULTRA
            }
        }

        createRenderables(planetName)
    }

    private fun createRenderables(planetName: String) {
        val buffer = assets.open("models/$planetName.glb").use { input ->
            val bytes = ByteArray(input.available())
            input.read(bytes)
            ByteBuffer.allocateDirect(bytes.size).apply {
                order(ByteOrder.nativeOrder())
                put(bytes)
                rewind()
            }
        }

        modelViewer.loadModelGlb(buffer)
        modelViewer.transformToUnitCube()

        if(planetName == "SolarSys") {
            val asset = modelViewer.asset ?: return
            animator = modelViewer.animator
            if (animator?.animationCount ?: 0 > 0) {
                animationDuration = animator!!.getAnimationDuration(0)
                animationStartTime = System.nanoTime()
            }

        }
    }

    inner class FrameCallback : Choreographer.FrameCallback {
        override fun doFrame(frameTimeNanos: Long) {
            choreographer.postFrameCallback(this)
            modelViewer.render(frameTimeNanos)


            animator?.let {
                val elapsedTime = (System.nanoTime() - animationStartTime) / 1_000_000_000.0f
                val progress = elapsedTime % animationDuration
                it.applyAnimation(0, progress)
                it.updateBoneMatrices()
            }
        }
    }
}

