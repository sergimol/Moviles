package com.example.practica1;

import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidengine.AEngine;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private AEngine androidEngine;
    private SurfaceView window;
    private AssetManager assetManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        assetManager = getAssets();

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

        ResourceLoader resourceLoader = new ResourceLoader();

        //Creamos el Engine y lo inicializamos
        InitialState state = new InitialState();

        try {
            androidEngine = new AEngine(window, assetManager);
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