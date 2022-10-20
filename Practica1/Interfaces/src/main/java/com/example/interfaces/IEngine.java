package com.example.interfaces;

public interface IEngine {
    IGraphics getGraphics();
    IInput getInput();
    IAudio getAudio();
    IState getState();

    
}
