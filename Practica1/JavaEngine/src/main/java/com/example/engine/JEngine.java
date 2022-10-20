package com.example.engine;

import com.example.interfaces.IAudio;
import com.example.interfaces.IGraphics;
import com.example.interfaces.IEngine;
import com.example.interfaces.IInput;
import com.example.interfaces.IState;

public class JEngine implements IEngine, Runnable {

    private IState state;
    private boolean running;
    private JGraphics graphics;

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
        return state;
    }


    @Override
    public void run() {

        long lastFrameTime = System.nanoTime();
        while (running) {
            long currentTime = System.nanoTime();
            long nanoElapsedTime = currentTime - lastFrameTime;
            lastFrameTime = currentTime;

            // Actualizacion del deltaTime
            double elapsedTime = (double) nanoElapsedTime / 1.0E9;

            render(graphics);
            update(elapsedTime);
        }
    }

    public void render(JGraphics graphics) {
        state.render(graphics);
    }

    public void update(double deltaTime) {
        state.update(deltaTime);
    }
}