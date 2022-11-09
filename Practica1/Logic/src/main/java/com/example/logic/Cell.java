package com.example.logic;

import com.example.interfaces.IGraphics;

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
    // Espacio entre celdas en px a máxima resolución
    private static int BASE_SPACING = 5;
    //booleano que permite el cambio
    boolean allowchange = true;

    Cell(int x, int y, boolean good) {
        xPos = x;
        if(x%2==0)
            state = cellStates.Blue;
        yPos = y;
        if(y%2==0)
            state = cellStates.Red;
        isGood = good;
    }

    cellStates getState() {
        return state;
    }

    void changeState() {
        if (allowchange) {
            if (state.ordinal() + 1 < cellStates.values().length - 1)
                state = cellStates.values()[state.ordinal() + 1];
            else state = cellStates.values()[0];
            allowchange = !allowchange;
        }
    }

    void resetAllowChange() {
        allowchange = true;
    }

    void changeState(cellStates newState) {
        state = newState;
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

    void render(IGraphics graphics, int zeroX, int zeroY, float cellSide, float cellSpacing) {
        switch (state) {
            case Grey:
                graphics.setColor(0x808080);
                //graphics.fillRect(zeroX + cellSpacing + xPos * cellSide, zeroY + cellSpacing + yPos * cellSide, cellSide, cellSide);
                break;
            case Red:
                graphics.setColor(0xFF0000);
                //graphics.fillRect(zeroX + xPos * cellSide, zeroY + yPos * cellSide, cellSide, cellSide);
                break;
            case Blue:
                graphics.setColor(0x0000FF);
                //graphics.fillRect(zeroX + xPos * cellSide, zeroY + yPos * cellSide, cellSide, cellSide);
                break;
            case Empty:
                graphics.setColor(0);
                graphics.drawRect(zeroX + xPos * cellSide, zeroY + yPos * cellSide, cellSide, cellSide);
                //graphics.drawLine(zeroX + xPos * cellSide, zeroY + yPos * cellSide, , );
                break;
        }
        graphics.fillRect(zeroX + xPos * (cellSide + cellSpacing), zeroY + yPos * (cellSide + cellSpacing) , cellSide, cellSide);
    }
}

