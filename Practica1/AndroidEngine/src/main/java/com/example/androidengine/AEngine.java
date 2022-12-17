package com.example.androidengine;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.view.SurfaceView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class AEngine implements Runnable {

    private boolean running = false;

    private State currentState;

    private AGraphics graphics;

    private AAudio audio;

    private AInput myInput;

    private ATimer timer;

    private Context contexto;

    //private ALockManager lockManager;

    private Thread renderThread;
    private AssetManager assetManager;
    private Resources resourcesManager;

    private String style;

    public AEngine(SurfaceView window, AssetManager assetM, Resources resourcesM, Context c) throws IOException {
        assetManager = assetM;
        resourcesManager = resourcesM;
        graphics = new AGraphics(window, assetManager, resourcesM);
        audio = new AAudio(assetManager);
        myInput = new AInput(window, graphics);
        timer = new ATimer();
        contexto = c;
        style = "Preset";
        //lockManager = new ALockManager();
    }

    public Context getContext() {
        return contexto;
    }
    //public ALockManager getLockManager() {return lockManager;}

    FileOutputStream writeStream() {
        return null;
    }

    FileInputStream readStream() {
        return null;
    }

    @Override
    public void run() {
        if (renderThread != Thread.currentThread()) {
            throw new RuntimeException("run() should not be called directly");  //No se reberia llamar por otra clase
        }
        while (running && graphics.getWidth() == 0) ; //Por si tarda en inicializarse la ventana

        graphics.init();
        //System.out.println(graphics.getScale());
        getState().init(this);
        getState().start();

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

            currentState.handleInput();
            timer.update(elapsedTime);
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

    public AGraphics getGraphics() {
        return graphics;
    }

    public AInput getInput() {
        return myInput;
    }

    public AAudio getAudio() {
        return audio;
    }

    public State getState() {
        return currentState;
    }

    public ATimer getTimer() {
        return timer;
    }

    public AssetManager getAssets() {
        return assetManager;
    }

    public void pause() {
        if (running) {
            running = false;
            while (true) {
                try {
                    renderThread.join();
                    renderThread = null;
                    break;
                } catch (InterruptedException e) {
                    //Esto no deberia ocurrir nunca
                    e.printStackTrace();
                }
            }
        }
    }

    public void resume() {
        if (!running) {
            renderThread = new Thread(this);
            renderThread.start();
            running = true;
        }
    }

    public boolean getRunning() {
        return running;
    }

    public void setState(State st) {
        currentState = st;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String aux) {
        style = aux;
    }
}
