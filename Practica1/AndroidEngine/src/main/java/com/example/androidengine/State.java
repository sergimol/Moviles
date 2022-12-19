package com.example.androidengine;

import android.os.Bundle;

import java.io.Serializable;

public abstract class State {

    public AEngine engine;
    public State previous = null;
    public boolean initiated = false;

    public void init(AEngine e){
        initiated = true;
        //if (previous != null){
          //  previous.init(e);
       // }
    }

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

    public void onSaveInstanceState(Bundle outState) {
    }
}
