package com.example.interfaces;

public interface IState {
    void init(IEngine engine);

    void update(double deltaTime);

    void render(IGraphics gr);

    void handleInput();

    void setPrevious(IState st);

}
