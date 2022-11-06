package com.example.interfaces;

public interface IState {
    void init();

    void update(double deltaTime);

    void render(IGraphics gr);

    void handleInput();

}
