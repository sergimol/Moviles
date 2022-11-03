package com.example.interfaces;

public interface IState {
    void update(double deltaTime);

    void render(IGraphics gr);

    void handleInput();

    void init(IGraphics gr);
}
