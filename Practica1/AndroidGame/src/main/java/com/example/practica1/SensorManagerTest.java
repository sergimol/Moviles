package com.example.practica1;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SensorManagerTest extends AppCompatActivity implements SensorEventListener {

    public SensorManager sensorManager;
    public Sensor mySensor;
    private long lastUpdate, actualTime;

    public SensorManagerTest() {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mySensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (mySensor == null) {
            Toast.makeText(this, "No acelerometer detected in this device", Toast.LENGTH_LONG).show();
            finish();
        } else {
            sensorManager.registerListener(this, mySensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float[] values = event.values;
            float x = values[0];
            float y = values[1];
            float z = values[2];
            float EG = SensorManager.GRAVITY_EARTH;

            float devAccel = (x * x + y * y + z * z) / (EG * EG);

            if (devAccel >= 1.5) {
                actualTime = System.currentTimeMillis();
                if((actualTime-lastUpdate) > 1000){
                    lastUpdate = actualTime;
                    //Llamada de metodo

                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this);
    }
}
