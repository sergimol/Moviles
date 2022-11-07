package com.example.engine;

import com.example.interfaces.IAudio;
import com.example.interfaces.IGraphics;
import com.example.interfaces.IEngine;
import com.example.interfaces.IInput;
import com.example.interfaces.IState;

import javax.swing.JFrame;

public class JEngine implements IEngine, Runnable {

    private boolean running = false;

    private IState currentState;
    private JGraphics graphics;
    private JAudio audio;
    private JInput myInput;


    private Thread renderThread;

    public JEngine(JFrame window) {
        //Inicializamos el graphics
        graphics = new JGraphics(window);
        window.setIgnoreRepaint(true);
        window.setVisible(true);

        //Inicializamos el audio
        audio = new JAudio();
//        audio.newSound("train_0", "train.wav");
//        audio.playSound("train_0");
//        audio.loop("train_0", true);

        //Inicializamos el Input (Solo raton de momento)
        myInput = new JInput(window);

        resume();   //Lanza el run()
    }

    @Override
    public void run() {

        long lastFrameTime = System.nanoTime();
        while (this.currentState != null) {
            long currentTime = System.nanoTime();
            long nanoElapsedTime = currentTime - lastFrameTime;
            lastFrameTime = currentTime;

            // Actualizacion del deltaTime
            double elapsedTime = (double) nanoElapsedTime / 1.0E9;

            this.currentState.handleInput();
            this.currentState.update(elapsedTime);

            //Esto se supone certificado perfecto
            do {
                this.graphics.prepareFrame();
                this.currentState.render(graphics);
                this.graphics.finishFrame();
                this.graphics.restore();

            } while (!this.graphics.cambioBuffer());
        }
        //Cierre de la aplicacion
        //System.exit(0);
    }

    @Override
    public IGraphics getGraphics() {
        return graphics;
    }

    @Override
    public IInput getInput() {
        return myInput;
    }

    @Override
    public IAudio getAudio() {
        return audio;
    }

    @Override
    public IState getState() {
        return currentState;
    }

    @Override
    public void pause() {

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
    public void setState(IState st){
        currentState = st;
    }
}