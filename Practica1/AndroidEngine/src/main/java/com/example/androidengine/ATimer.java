package com.example.androidengine;

import com.example.interfaces.ITimer;

public class ATimer implements ITimer, Runnable {

    float OriginalTime;
    float TimeLeft;

    private Thread renderThread;
    boolean running;

    public ATimer() {
        running = false;
    }

    @Override
    public void setTimer(float time) {
        OriginalTime = time;
        TimeLeft = OriginalTime;
    }

    @Override
    public void startTimer() {
        resume();
    }

    @Override
    public float getTimeLeft() {
        return TimeLeft;
    }

    @Override
    public boolean isEnded() {
        return TimeLeft <= 0;
    }

    @Override
    public void run() {
        if (running) {
            long lastFrameTime = System.nanoTime();
            while (OriginalTime > 0 && TimeLeft > 0) {
                long currentTime = System.nanoTime();
                long nanoElapsedTime = currentTime - lastFrameTime;
                lastFrameTime = currentTime;

                // Actualizacion del deltaTime
                double elapsedTime = (double) nanoElapsedTime / 1.0E9;

                //Actualizacion variable tiempo
                TimeLeft -= elapsedTime;
            }
            running = false;
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
}

