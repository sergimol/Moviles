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


        State state;
        if (savedInstanceState != null) {
            //state = new InitialState();
                switch (savedInstanceState.getInt("SceneType")) {
                    case 0:
                        state = new InitialState(null);
                        break;
                    case 1:
                        state = new LevelSelectionState(null);
                        break;
                    case 2:
                        state = new GameState(savedInstanceState.getInt("x"), savedInstanceState.getInt("y"), savedInstanceState);
                        break;
                    case 3:
                        state = new ShopState(null);
                        break;
                    case 4:
                        state = new CategoryLevelSelectionState("null");
                        break;
                    case 5:
                        state = new CategorySelect(null);
                        break;
                    default:
                        state = new InitialState(null);
                        break;
                }
            } else {
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
        //hay que meter la escena principal
        androidEngine.getState().onSaveInstanceState(outState);
    }

    //@Override
    //public void onRestoreInstanceState(Bundle savedInstanceState ){
    //super.OnRestoreInstanceState(savedInstanceState);

    //}

    public void saveUnlocks(boolean[] unlocks, String name) {
        try {
            FileOutputStream f = openFileOutput(name,
                    Context.MODE_PRIVATE);
            String text = "" + unlocks.length;
            for (int i = 0; i < unlocks.length; i++) {
                text += ("\n" + (unlocks[i] ? 1 : 0));
            }
            f.write(text.getBytes());
            f.close();
        } catch (Exception e) {
            Log.e("guardado", e.getMessage(), e);
        }
    }

    public boolean[] loadUnlocks(String nombre, int q) {

        String[] a = fileList();

        try {
            int quantity = q;
            for (String file : a) {
                if (file.equals(nombre)) {
                    //file exits
                    FileInputStream f = openFileInput(nombre);
                    BufferedReader input = new BufferedReader(
                            new InputStreamReader(f));

                    int n = 0;
                    String line;
                    int read;

                    line = input.readLine();
                    quantity = Integer.parseInt(line);
                    boolean[] unlocks = new boolean[quantity];

                    do {
                        line = input.readLine();
                        if (line != null) {
                            read = Integer.parseInt(line);
                            unlocks[n] = (read == 1);
                            n++;
                        }
                    }
                    while (n < quantity && line != null);

                    f.close();
                    return unlocks;
                }
            }
            return createSaveFiles(quantity);
        } catch (Exception e) {
            Log.e("guardados error", e.getMessage(), e);
            return null;
        }
    }

    public boolean[] createSaveFiles(int quantity) {
        boolean[] res = new boolean[quantity];
        res[0] = true;
        return res;
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