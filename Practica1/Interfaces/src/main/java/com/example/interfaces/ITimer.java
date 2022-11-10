package com.example.interfaces;

public interface ITimer {

    void setTimer(float time);

    void startTimer();

    float getTimeLeft();

    boolean isEnded();

    public void update(double deltaTime);
}
