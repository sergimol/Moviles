package com.example.practica1;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidengine.AEngine;
import com.example.androidengine.State;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.io.IOException;
import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements Serializable {

    private AEngine androidEngine;
    private SurfaceView window;
    private AssetManager assetManager;
    private Resources resourcesManager;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        assetManager = getAssets();
        resourcesManager = getResources();

        //Creamos el SurfaceView y lo inicializamos
        window = new SurfaceView(this);
        setContentView(window);




        System.out.println("contador: " + count);

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


        State state;
        if (savedInstanceState != null){
            count = savedInstanceState.getInt("contador",0);
            count++;
            //state = new InitialState();
            Bundle scene = savedInstanceState.getBundle("Scene");
            if (scene != null){ //una vez aqui nunca va a ser null pero proteccion
                switch (scene.getInt("SceneType")){
                    case 0:
                        state = new InitialState(null);
                        break;
                    case 1:
                        state = new LevelSelectionState(scene);
                        break;
                    case 2:
                        state = new GameState(scene.getInt("x"), scene.getInt("y"), scene);
                        break;
                    case 3:
                        state = new ShopState(scene);
                        break;
                    case 4:
                        state = new CategoryLevelSelectionState(scene);
                        break;
                    case 5:
                        state = new CategorySelect(scene);
                        break;
                    default:
                        state = new InitialState(null);
                        break;
                }
            }
            else{
                state = new InitialState(null);
            }

        }
        else{
            count = 0;
            state = new InitialState(null);
        }
        //Creamos el Engine y lo inicializamos

        try {
            androidEngine = new AEngine(window, assetManager, resourcesManager, this);
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
        outState.putInt("contador", count);
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