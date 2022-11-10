package com.example.engine;

import com.example.interfaces.ITimer;

public class JTimer implements ITimer {
    float originalTime;
    float timeLeft;

    boolean running;

    public JTimer() {
        running = false;
    }

    @Override
    public void setTimer(float time) {
        originalTime = time;
        timeLeft = originalTime;
    }

    @Override
    public void startTimer() {
        running = true;;
    }

    @Override
    public float getTimeLeft() {
        return timeLeft;
    }

    @Override
    public boolean isEnded() {
        return timeLeft <= 0;
    }

    @Override
    public void update(double deltaTime) {
        if (running) {
            //Actualizacion variable tiempo
            timeLeft -= deltaTime;
            if(timeLeft < 0)
                running = false;
        }
    }
}
