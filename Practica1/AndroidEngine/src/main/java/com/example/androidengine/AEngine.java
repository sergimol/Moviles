package com.example.androidengine;

import android.content.res.AssetManager;
import android.view.SurfaceView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.interfaces.IAudio;
import com.example.interfaces.IEngine;
import com.example.interfaces.IGraphics;
import com.example.interfaces.IInput;
import com.example.interfaces.IState;

import java.io.IOException;

public class AEngine extends AppCompatActivity implements IEngine, Runnable {

    private boolean running = false;

    private IState currentState;

    private AGraphics graphics;

    //private AAudio audio;
    //private AInput myInput;

    private AssetManager assetManager;


    private Thread renderThread;

    public AEngine(SurfaceView window) throws IOException {
        //assetManager = getAssets();
        graphics = new AGraphics(window, assetManager);
    }


    @Override
    public void run() {
        if (renderThread != Thread.currentThread()) {
            throw new RuntimeException("run() should not be called directly");  //No se reberia llamar por otra clase
        }
        while (running && graphics.getWidth() == 0) ; //Por si tarda en inicializarse la ventana

        //Time deltaTime
        long lastFrameTime = System.nanoTime();
        long informePrevio = lastFrameTime;     //Informe de los fps
        int frames = 0;

        while (running) {
            long currentTime = System.nanoTime();
            long nanoElapsedTime = currentTime - lastFrameTime;
            lastFrameTime = currentTime;

            //Informe FPS
            double elapsedTime = (double) nanoElapsedTime / 1.0E9;

            currentState.update(elapsedTime);

            if (currentTime - informePrevio > 1000000000l) {
                long fps = frames * 1000000000l / (currentTime - informePrevio);
                System.out.println("" + fps + " fps");
                frames = 0;
                informePrevio = currentTime;
            }
            frames++;

            //Pintamos el frame
            graphics.prepareFrame();
            currentState.render(graphics);              //Renderiza la escena
            graphics.finishFrame();
        }
    }

    @Override
    public IGraphics getGraphics() {
        return graphics;
    }

    @Override
    public IInput getInput() {
        return null;
    }

    @Override
    public IAudio getAudio() {
        return null;
    }

    @Override
    public IState getState() {
        return currentState;
    }

    @Override
    public void pause() {
        if (running) {
            running = false;
            while (true) {
                try {
                    renderThread.join();
                    renderThread = null;
                } catch (InterruptedException e) {
                    //Esto no deberia ocurrir nunca
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void resume() {
        if (!running) {
            running = true;
            renderThread = new Thread(this);
            renderThread.start();
        }
    }

    @Override
    public void setState(IState st) {
        currentState = st;
    }
}
