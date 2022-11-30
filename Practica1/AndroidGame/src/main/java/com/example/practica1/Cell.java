package com.example.practica1;

import com.example.androidengine.AGraphics;

enum cellStates {
    Grey, Blue, Empty, Red
};

enum checkStates {
    Correct, Wrong, Missing
};

public class Cell {
    // Posiciones logicas dentro del tablero
    int xPos, yPos;
    // Estado de la casilla
    cellStates state = cellStates.Grey;
    // True = casilla forma parte del dibujo, False = no forma parte
    boolean isGood;
    //booleano que permite el cambio
    boolean allowchange = true;

    Cell(int x, int y, boolean good) {
        xPos = x;
        yPos = y;
        isGood = good;
    }

    cellStates getState() {
        return state;
    }

    void changeState() {
        // Cambia el estado al siguiente
        if (allowchange) {
                                        // Length - 1 para no acceder a las celdas rojas 
            if (state.ordinal() + 1 < cellStates.values().length - 1)
                state = cellStates.values()[state.ordinal() + 1];
            else state = cellStates.values()[0];
            allowchange = !allowchange;
        }
    }

    void resetAllowChange() {
        allowchange = true;
    }

    boolean changeState(cellStates newState) {

        if (allowchange) {
            // Length - 1 para no acceder a las celdas rojas
            state = newState;
            allowchange = !allowchange;
            //comprobar si pertenece a la solucion en el caso de ser blue
            if (newState == cellStates.Blue){
                return checkStates.Correct == checkCell();
            }
        }

        //default no se considerara un error
        return true;

    }

    checkStates checkCell() {
        if (!isGood && state == cellStates.Blue) {
            state = cellStates.Red;
            return checkStates.Wrong;
        } else if (isGood && state != cellStates.Blue)
            return checkStates.Missing;
        else
            return checkStates.Correct;
    }

    void render(AGraphics graphics, int zeroX, int zeroY, float cellSide, float cellSpacing) {
        switch (state) {
            case Grey:
                graphics.setColor(0xFF808080);
                break;
            case Red:
                graphics.setColor(0xFFFF0000);
                break;
            case Blue:
                graphics.setColor(0xFF0000FF);
                break;
            case Empty:
                graphics.setColor(0xFF000000);
                graphics.drawRect(zeroX + xPos * (cellSide + cellSpacing), zeroY + yPos * (cellSide + cellSpacing), cellSide - 1, cellSide - 1,1);
                graphics.drawLine(zeroX + xPos * (cellSide + cellSpacing), zeroY + yPos * (cellSide + cellSpacing), zeroX + xPos * (cellSide + cellSpacing) + cellSide - 1, zeroY + yPos * (cellSide + cellSpacing) + cellSide - 1);
                return;
        }
        graphics.fillRect(zeroX + xPos * (cellSide + cellSpacing), zeroY + yPos * (cellSide + cellSpacing) , cellSide, cellSide);
    }
}

