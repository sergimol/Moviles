package com.example.androidengine;

public class ATimer {

    float originalTime;
    float timeLeft;

    private Thread renderThread;
    boolean running;

    public ATimer() {
        running = false;
    }

    public void setTimer(float time) {
        originalTime = time;
        timeLeft = originalTime;
    }

    public void startTimer() {
        running = true;;
    }

    public float getTimeLeft() {
        return timeLeft;
    }

    public boolean isEnded() {
        return timeLeft <= 0;
    }

    public void update(double deltaTime) {
        if (running) {
            //Actualizacion variable tiempo
            timeLeft -= deltaTime;
            System.out.println(timeLeft);
            if(timeLeft <= 0)
                running = false;
        }
    }
}

