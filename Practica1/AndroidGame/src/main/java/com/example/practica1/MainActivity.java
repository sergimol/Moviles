package com.example.practica1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.SurfaceView;

import com.example.androidengine.AEngine;
import com.example.logic.GameState;

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

        //Creamos el Engine y lo inicializamos
        GameState state = new GameState(10, 10);

        try {
            androidEngine = new AEngine(window, assetManager);
        } catch (IOException e) {
            e.printStackTrace();
        }

        androidEngine.setState(state);
        state.init(androidEngine);
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