package com.example.sensorvetestuygulamas

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.sensorvetestuygulamas.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private var gyroscope: Sensor? = null
    private var magnetometer: Sensor? = null
    private var proximity: Sensor? = null
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        // Sensörleri başlat
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        proximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)

        // Sensörlerin var olup olmadığını kontrol et
        if (accelerometer == null) {
            Log.d("SensorTest", "Accelerometer not available")
        }
        if (gyroscope == null) {
            Log.d("SensorTest", "Gyroscope not available")
        }
        if (magnetometer == null) {
            Log.d("SensorTest", "Magnetometer not available")
        }
        if (proximity == null) {
            Log.d("SensorTest", "Proximity sensor not available")
        }
    }

    override fun onResume() {
        super.onResume()
        accelerometer?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
        gyroscope?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
        magnetometer?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
        proximity?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            when (event.sensor.type) {
                Sensor.TYPE_ACCELEROMETER -> {
                    val x = event.values[0]
                    val y = event.values[1]
                    val z = event.values[2]
                    Log.d("SensorTest", "Accelerometer - x: $x, y: $y, z: $z")
                    binding.accelerometerTextView.text = "Accelerometer - x: $x, y: $y, z: $z"
                }
                Sensor.TYPE_GYROSCOPE -> {
                    val x = event.values[0]
                    val y = event.values[1]
                    val z = event.values[2]
                    Log.d("SensorTest", "Gyroscope - x: $x, y: $y, z: $z")
                    binding.gyroscopeTextView.text = "Gyroscope - x: $x, y: $y, z: $z"
                }
                Sensor.TYPE_MAGNETIC_FIELD -> {
                    val x = event.values[0]
                    val y = event.values[1]
                    val z = event.values[2]
                    Log.d("SensorTest", "Magnetometer - x: $x, y: $y, z: $z")
                    binding.magnetometerTextView.text = "Magnetometer - x: $x, y: $y, z: $z"
                }
                Sensor.TYPE_PROXIMITY -> {
                    val distance = event.values[0]
                    Log.d("SensorTest", "Proximity - Distance: $distance")
                    binding.proximityTextView.text = "Proximity - Distance: $distance"
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Sensör hassasiyetindeki değişiklikleri işleyebilirsin
    }
}
