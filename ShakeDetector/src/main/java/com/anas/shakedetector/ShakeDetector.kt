package com.anasesh.shakedetector

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlin.math.abs

/**
 * A shake detection utility that uses the device's accelerometer to detect shake gestures.
 * 
 * This class monitors accelerometer data and triggers callbacks when shake patterns are detected.
 * The shake detection is based on configurable thresholds for force, timing, and shake count.
 * 
 * @param context The Android context used to access system services
 * @param shakeListener The listener that will receive shake event callbacks
 * 
 * @see ShakeListener
 * 
 * Example usage:
 * ```kotlin
 * val shakeDetector = ShakeDetector(this) { 
 *     // Handle shake event
 *     Log.d("Shake", "Device was shaken!")
 * }
 * 
 * // Start listening in onStart()
 * override fun onStart() {
 *     super.onStart()
 *     shakeDetector.start()
 * }
 * 
 * // Stop listening in onStop()
 * override fun onStop() {
 *     super.onStop()
 *     shakeDetector.stop()
 * }
 * ```
 */
class ShakeDetector(private val context: Context, private val shakeListener: ShakeListener) :
    SensorEventListener {
    private var mSensorMgr: SensorManager? = null
    private var sensor: Sensor?=null;

    private var mLastX = -1.0f
    private var mLastY = -1.0f
    private var mLastZ = -1.0f
    private var mLastTime: Long = 0
    private var mShakeCount = 0
    private var mLastShake: Long = 0
    private var mLastForce: Long = 0

    /**
     * call this method to start listening to shakes
     * @throws [UnsupportedOperationException] if sensors not supported
     * Should be used in lifecycle hooks depending on your need, for most cases onStart
     */
    fun start() {
        mSensorMgr = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor=mSensorMgr?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (mSensorMgr == null) {
            throw UnsupportedOperationException("Sensors not supported")
        }
        val supported = mSensorMgr!!.registerListener(
            this,
            sensor,
            SensorManager.SENSOR_DELAY_NORMAL,
        )
        if (!supported) {
            mSensorMgr!!.unregisterListener(this, sensor)
            throw UnsupportedOperationException("Accelerometer not supported")
        }
    }

    /**
     * call this method to stop listening to shakes
     * Should be used in lifecycle hooks depending on your need, for most cases onStop hook is convenient
     */
    fun stop() {
        if (mSensorMgr != null&&sensor!=null) {
            mSensorMgr!!.unregisterListener(this, sensor)
            mSensorMgr = null
        }
    }


    companion object {
        private const val FORCE_THRESHOLD = 350
        private const val TIME_THRESHOLD = 100
        private const val SHAKE_TIMEOUT = 500
        private const val SHAKE_DURATION = 1000
        private const val SHAKE_COUNT = 3
    }

    override fun onSensorChanged(ev: SensorEvent?) {
        if(ev==null) return;

        val now = System.currentTimeMillis()
        val values=ev.values;
        if ((now - mLastForce) > SHAKE_TIMEOUT) {
            mShakeCount = 0
        }

        if ((now - mLastTime) > TIME_THRESHOLD) {
            val diff = now - mLastTime
            val speed =
                (abs((values[0] + values[1] + values[2] - mLastX - mLastY - mLastZ).toDouble()) / diff * 10000).toFloat()
            if (speed > FORCE_THRESHOLD) {
                if ((++mShakeCount >= SHAKE_COUNT) && (now - mLastShake > SHAKE_DURATION)) {
                    mLastShake = now
                    mShakeCount = 0
                    shakeListener.onShaked()
                }
                mLastForce = now
            }
            mLastTime = now
            mLastX = values[0]
            mLastY = values[1]
            mLastZ = values[2]
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}
}
