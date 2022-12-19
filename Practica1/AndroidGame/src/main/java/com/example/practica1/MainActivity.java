package com.example.practica1;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidengine.AEngine;
import com.example.androidengine.State;
import com.google.android.gms.ads.AdRequest;
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

public class MainActivity extends AppCompatActivity implements Serializable {

    private AEngine androidEngine;
    private SurfaceView window;
    private AssetManager assetManager;
    private Resources resourcesManager;
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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


        GameManager manager = new GameManager(this);

        State state;
        if (savedInstanceState != null) {
            //state = new InitialState();
                switch (savedInstanceState.getInt("SceneType")) {
                    case 0:
                        state = new InitialState(manager);
                        break;
                    case 1:
                        state = new LevelSelectionState(manager);
                        break;
                    case 2:
                        state = new GameState(savedInstanceState.getInt("x"), savedInstanceState.getInt("y"), savedInstanceState);
                        break;
                    case 3:
                        state = new ShopState(manager);
                        break;
                    case 4:
                        state = new CategoryLevelSelectionState(savedInstanceState);
                        break;
                    case 5:
                        state = new CategorySelect();
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
    }

    //@Override
    //public void onRestoreInstanceState(Bundle savedInstanceState ){
    //super.OnRestoreInstanceState(savedInstanceState);

    //}

}


/*
    Quitar interfaces
    Manifest
        Documento XML que describe la aplicacion
        Contiene informacion sobre el nombre del paquete
        Que servicios necesita (si necesita internet)
        Requisitos de software
        Version y codificacion de la aplicacion y donde se instala
        Si queremos que se ejecute con algun tema especial (tamaño completo y sin barra)
        Esto es lo que coge el googlePlay para ver que te pide


* */