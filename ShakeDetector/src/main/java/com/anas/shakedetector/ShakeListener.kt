package com.anasesh.shakedetector

/**
 * Interface for listening to shake events detected by [ShakeDetector].
 * 
 * Implement this interface to receive callbacks when the device is shaken
 * according to the configured sensitivity thresholds.
 */
interface ShakeListener {
    /**
     * Called when a shake gesture is detected.
     * 
     * This method is invoked on the main thread when the shake detector
     * has identified a valid shake pattern based on accelerometer data.
     * The shake detection requires a minimum number of consecutive shakes
     * within a specific time window to trigger this callback.
     */
    fun onShaked()
}