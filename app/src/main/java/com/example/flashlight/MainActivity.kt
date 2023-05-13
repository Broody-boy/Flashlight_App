package com.example.flashlight

import android.content.pm.PackageManager
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private var isFlashOn = false
    private lateinit var cameraManager: CameraManager
    private lateinit var cameraId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Check if the device has a camera flash
        if (!packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
            Toast.makeText(this, "This device doesn't have a flash", Toast.LENGTH_SHORT).show()
            finish()
        }

        // Initialize the CameraManager and get the camera ID for the flash
        cameraManager = getSystemService(CAMERA_SERVICE) as CameraManager
        cameraId = cameraManager.cameraIdList[0]

        // Set up the flashlight button
        val flashlightButton = findViewById<Button>(R.id.flashlight_button)
        flashlightButton.setOnClickListener {
            toggleFlashlight()
        }
    }

    private fun toggleFlashlight() {
        val flashlightButton = findViewById<Button>(R.id.flashlight_button)
        try {
            if (isFlashOn) {
                // Turn off the flashlight
                cameraManager.setTorchMode(cameraId, false)
                isFlashOn = false
                flashlightButton.text = "Off"
            } else {
                // Turn on the flashlight
                cameraManager.setTorchMode(cameraId, true)
                isFlashOn = true
                flashlightButton.text = "On"
            }
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }
}