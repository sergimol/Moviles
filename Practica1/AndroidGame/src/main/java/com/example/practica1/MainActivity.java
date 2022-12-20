package com.example.practica1;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.androidengine.AEngine;
import com.example.androidengine.State;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class MainActivity extends AppCompatActivity implements Serializable, SensorEventListener {

    private AEngine androidEngine;
    private SurfaceView window;
    private AssetManager assetManager;
    private Resources resourcesManager;
    private AdView mAdView;

    //private SensorManagerTest sensorManagerTest;
    public SensorManager sensorManager;
    public Sensor mySensor;
    private long lastUpdate, actualTime;

    //esto es de la logica unicamente
    private GameManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        WorkManager.getInstance(getApplicationContext()).cancelAllWork();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("PRUEBA", "prueba", importance);
            channel.setDescription("Canal de prueba");
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        assetManager = getAssets();
        resourcesManager = getResources();

        //SensorManager
        //sensorManagerTest = new SensorManagerTest();
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mySensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (mySensor == null) {
            Toast.makeText(this, "No acelerometer detected in this device", Toast.LENGTH_LONG).show();
            finish();
        } else {
            sensorManager.registerListener(this, mySensor, SensorManager.SENSOR_DELAY_NORMAL);
        }


        //Creamos el SurfaceView y lo inicializamos
        window = new SurfaceView(this);
        setContentView(window);


        // fullscreen and remove support action bar
        if (Build.VERSION.SDK_INT < 16)
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        else {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
        getSupportActionBar().hide();

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {

            }
        });

        ResourceLoader resourceLoader = new ResourceLoader();


        manager = new GameManager(this);

        State state = manager.loadLastLevel();
        if (state == null)
            if (savedInstanceState != null) {
                //state = new InitialState();

                switch (savedInstanceState.getInt("SceneType")) {
                    case 1:
                        state = new LevelSelectionState(manager);
                        break;
                    case 2:
                        state = new GameState(savedInstanceState.getInt("x"), savedInstanceState.getInt("y"), savedInstanceState, manager);
                        break;
                    case 3:
                        state = new ShopState(manager);
                        break;
                    case 4:
                        state = new CategoryLevelSelectionState(savedInstanceState, manager);
                        break;
                    case 5:
                        state = new CategorySelect(manager);
                        break;
                    default:
                        state = new InitialState(manager);
                        break;
                }
            } else {
                state = new InitialState(manager);
            }

        //Creamos el Engine y lo inicializamos

        try {
            androidEngine = new AEngine(window, assetManager, resourcesManager, this, this);
            androidEngine.setState(state);
            resourceLoader.loadResources(androidEngine);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        androidEngine.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        manager.saveMoney();
        manager.saveStyle();

        //por default no hay escena final
        manager.lastEscene = false;
        manager.saveLastSceneBool();

        androidEngine.getState().onDestroy();


        androidEngine.pause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //hay que meter la escena principal
        androidEngine.getState().onSaveInstanceState(outState);
        OneTimeWorkRequest notificationWork = new OneTimeWorkRequest.Builder(NotifyWorker.class)
                .setInitialDelay(2, TimeUnit.MINUTES)
                .build();

        WorkManager.getInstance(this).enqueue(notificationWork);

        //manager.addMoney(20);

        manager.saveMoney();
        manager.saveStyle();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();



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
                if ((actualTime - lastUpdate) > 1000) {
                    lastUpdate = actualTime;
                    //Llamada de metodo
                    System.out.println("Sensor");
                    manager.rotateStyle();
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}
}