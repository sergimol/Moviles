package com.example.engine;

import com.example.interfaces.IAudio;
import com.example.interfaces.IGraphics;
import com.example.interfaces.IEngine;
import com.example.interfaces.IInput;
import com.example.interfaces.IState;
import com.example.interfaces.IImage;

import javax.swing.JFrame;

public class JEngine implements IEngine, Runnable {

    private IState currentState;
    private boolean running;
    private JGraphics graphics;

    public JEngine(JFrame renderView) {

        renderView.setIgnoreRepaint(true);

        renderView.setVisible(true);
        // Intentamos crear el buffer strategy con 2 buffers.
        int intentos = 100;
        while (intentos-- > 0) {
            try {
                renderView.createBufferStrategy(2);
                break;
            } catch (Exception e) {
            }
        } // while pidiendo la creación de la buffeStrategy
        if (intentos == 0) {
            System.err.println("No pude crear la BufferStrategy");
            return;
        } else {
            // En "modo debug" podríamos querer escribir esto.
            //System.out.println("BufferStrategy tras " + (100 - intentos) + " intentos.");
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

            do {
                this.graphics.prepareFrame();
                this.currentState.render();             //Se le pasa el graphics de alguna manera
                this.graphics.finishFrame();
            } while (!this.graphics.cambioBuffer());
        }
    }
}