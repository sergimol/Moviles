package com.example.logic;

enum States{
    Grey, Blue, Empty, Red
}
public class Cell {
    // Posiciones logicas dentro del tablero
    int xPos, yPos;
    // Estado de la casilla
    States state = States.Grey;
    // True = casilla forma parte del dibujo, False = no forma parte
    boolean isGood;

    Cell(int x, int y, boolean good){
        xPos = x;
        yPos = y;
        isGood = good;
    }
    States getState() { return state; }
    void changeState(){
        state = States.values()[state.ordinal() + 1];
    };
    void changeState(States newState){
        state = newState;
    }
}
