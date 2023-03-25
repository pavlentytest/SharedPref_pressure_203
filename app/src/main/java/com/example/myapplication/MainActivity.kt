package com.example.myapplication

import android.content.Context
import android.content.SharedPreferences
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private lateinit var sharedPreferences: SharedPreferences
    private var pressureSensor: Sensor? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        sharedPreferences = getSharedPreferences("pressure", Context.MODE_PRIVATE)
    }

    override fun onResume() {
        super.onResume()
        pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)
        if(pressureSensor != null) {
            sensorManager.registerListener(this, pressureSensor, SensorManager.SENSOR_DELAY_GAME)
        }
    }


    override fun onSensorChanged(event: SensorEvent?) {
        if(event!!.sensor.type == pressureSensor!!.type) {
            with(sharedPreferences.edit()) {
                putFloat("pressure_value", event.values[0])
                apply()
            }
        }
        findViewById<TextView>(R.id.textView).text = sharedPreferences.getFloat("pressure_value",0f).toString()
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }


}