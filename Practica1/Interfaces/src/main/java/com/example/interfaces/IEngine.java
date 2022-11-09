package com.example.interfaces;

public interface IEngine {
    IGraphics getGraphics();

    IInput getInput();

    IAudio getAudio();

    IState getState();

    ITimer getTimer();

    public void pause();

    public void resume();

    public void setState(IState state);
}
