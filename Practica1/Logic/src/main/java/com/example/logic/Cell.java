package com.example.logic;

enum States{
    Grey, Blue, Empty, Red
}
public class Cell {
    int x, y;
    States state = States.Grey;

    States getState() { return state; }
    void changeState(){
        state = States.values()[state.ordinal() + 1];
    };
    void changeState(States newState){
        state = newState;
    }
}
