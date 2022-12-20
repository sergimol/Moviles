package com.example.practica1;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;

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

public class MainActivity extends AppCompatActivity implements Serializable {

    private AEngine androidEngine;
    private SurfaceView window;
    private AssetManager assetManager;
    private Resources resourcesManager;
    private AdView mAdView;
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

        State state;
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
        androidEngine.pause();
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
    //@Override
    //public void onRestoreInstanceState(Bundle savedInstanceState ){
    //super.OnRestoreInstanceState(savedInstanceState);

    //}

}

/*
    Manifest
        Documento XML que describe la aplicacion
        Contiene informacion sobre el nombre del paquete
        Que servicios necesita (si necesita internet)
        Requisitos de software
        Version y codificacion de la aplicacion y donde se instala
        Si queremos que se ejecute con algun tema especial (tamaño completo y sin barra)
        Esto es lo que coge el googlePlay para ver que te pide


* */