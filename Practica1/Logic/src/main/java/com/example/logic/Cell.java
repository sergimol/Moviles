package com.example.logic;

import com.example.interfaces.IGraphics;

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
        if (isGood)
            return state == cellStates.Blue;
        else if (state == cellStates.Blue) {
            changeState(cellStates.Red);
            return false;
        }
        return true;
    }

    void render(IGraphics graphics){
        switch (state){
            case Grey:
                graphics.setColor(0x808080);
                graphics.fillSquare(xPos * 10, yPos * 10, 5);
                break;
            case Red:
                graphics.setColor(0xFF0000);
                break;
            case Blue:
                graphics.setColor(0x0000FF);
                break;
            case Empty:
                graphics.setColor(0);
                break;
        }
    }
}

