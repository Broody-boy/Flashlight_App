package com.example.flashlight

import android.content.pm.PackageManager
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

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
        cameraManager = getSystemService(CAMERA_SERVICE) as CameraManager   //cameraManager is the name of the object that references the system's camera manager.
        cameraId = cameraManager.cameraIdList[0]    //getCameraIdList() is a method of the cameraManager object that returns a list of all the available cameras on the device.
                                                    //And [0] is the index of the back camera in the list.
        // Set up the flashlight button
        val flashlightImgButton = findViewById<ImageButton>(R.id.flashlight_img_button)
        flashlightImgButton.setOnClickListener {
            toggleFlashlight()
        }
    }

    private fun toggleFlashlight() {
        val flashlightImgButton = findViewById<ImageButton>(R.id.flashlight_img_button)
        try {
            if (isFlashOn) {
                // Turn off the flashlight
                cameraManager.setTorchMode(cameraId, false)
                isFlashOn = false
                flashlightImgButton.setImageResource(R.drawable.off)    //For ImageButton, we use setImageResource while for ImageView, we use setBackgroundResource
            } else {
                // Turn on the flashlight
                cameraManager.setTorchMode(cameraId, true)
                isFlashOn = true
                flashlightImgButton.setImageResource(R.drawable.on)     //For ImageButton, we use setImageResource while for ImageView, we use setBackgroundResource
            }
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }
}