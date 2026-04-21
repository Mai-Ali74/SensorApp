package com.example.sensorapp

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private var gyroscope: Sensor? = null

    private lateinit var accelInfo: TextView
    private lateinit var gyroInfo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        accelInfo = findViewById(R.id.accelInfo)
        gyroInfo = findViewById(R.id.gyroInfo)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager


        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)


        if (accelerometer == null) accelInfo.text = "Error: Accelerometer not found!"
        if (gyroscope == null) gyroInfo.text = "Error: Gyroscope not found!"
    }

    override fun onResume() {
        super.onResume()

        accelerometer?.also {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
        gyroscope?.also {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onPause() {
        super.onPause()

        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {

        val x = "%.2f".format(event.values[0])
        val y = "%.2f".format(event.values[1])
        val z = "%.2f".format(event.values[2])


        val model = event.sensor.name
        val vendor = event.sensor.vendor

        when (event.sensor.type) {
            Sensor.TYPE_ACCELEROMETER -> {
                accelInfo.text = """
                    🚀 ACCELEROMETER
                    Model: $model
                    Vendor: $vendor
                    -----------------------
                    X: $x
                    Y: $y
                    Z: $z
                """.trimIndent()
            }

            Sensor.TYPE_GYROSCOPE -> {
                gyroInfo.text = """
                    🔄 GYROSCOPE
                    Model: $model
                    Vendor: $vendor
                    -----------------------
                    X: $x
                    Y: $y
                    Z: $z
                """.trimIndent()
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
}