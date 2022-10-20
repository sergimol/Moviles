package com.example.interfaces;

public interface IState {
    void update(double deltaTime);
    void render(IGraphics graphics);  //Pasarle el graphics
}
