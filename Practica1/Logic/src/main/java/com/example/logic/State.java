package com.example.logic;

public abstract class State {

    AEngine engine;
    State previous = null;

    public void init(AEngine e){}

    public void start(){}

    public void render(AGraphics graphics){}

    public void update(double deltaTime){}

    public void handleInput(){}

    public void setPrevious(State st) {
        previous = st;
    }

    public State getprevious() {
        return previous;
    }
}
