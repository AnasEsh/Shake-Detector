package com.anasesh.shakedetector

/**
 * Shake sensitivity, higher sensitivity means less shake/vibration needed to trigger the on shake
 */
sealed class ShakeSensitivity {
    object High : ShakeSensitivity();
    object Normal : ShakeSensitivity();
    object Low : ShakeSensitivity();
    /**
     * Used to create custom sensitivity and detection threshold,
     * @param threshold defaults to 650
     */
    data class Custom(val threshold: Int = ShakeDetector.NORMAL_THRESHOLD) : ShakeSensitivity();
}