package com.example.interfaces;

public interface ISound {
    void play();

    void pause();

    void resume();

    void stop();

    void loop(boolean loopCondition);
}

