package com.example.logic;

enum cellStates {
    Grey, Blue, Empty, Red
}
public class Cell {
    // Posiciones logicas dentro del tablero
    int xPos, yPos;
    // Estado de la casilla
    cellStates state = cellStates.Grey;
    // True = casilla forma parte del dibujo, False = no forma parte
    boolean isGood;

    Cell(int x, int y, boolean good){
        xPos = x;
        yPos = y;
        isGood = good;
    }
    cellStates getState() { return state; }
    void changeState(){
        state = cellStates.values()[state.ordinal() + 1];
    }
    void changeState(cellStates newState){
        state = newState;
    }
    boolean checkCell(){
        if(isGood)
            return state == cellStates.Blue;
        else
            return (state == cellStates.Empty || state == cellStates.Grey);
    }
}
