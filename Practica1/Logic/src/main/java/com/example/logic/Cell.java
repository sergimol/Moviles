package com.example.logic;

import com.example.interfaces.IGraphics;

enum cellStates {
    Grey, Blue, Empty, Red
};

public class Cell {
    // Posiciones logicas dentro del tablero
    int xPos, yPos;
    // Estado de la casilla
    cellStates state = cellStates.Blue;
    // True = casilla forma parte del dibujo, False = no forma parte
    boolean isGood;
    // Espacio entre celdas en px a máxima resolución
    private static int BASE_SPACING = 5;

    Cell(int x, int y, boolean good){
        xPos = x;
        yPos = y;
        isGood = good;
    }
    cellStates getState() { return state; }

    void changeState(){
        if (state.ordinal() + 1 < cellStates.values().length)
        state = cellStates.values()[state.ordinal() + 1];
        else state = cellStates.values()[0];
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

    void render(IGraphics graphics, int xSize, float scale){
        int w = graphics.getWidth();
        int h = graphics.getHeight();
        float x = (xPos * (float) w / xSize) + (float) w / 5;
        float y = (yPos * (float) w / xSize) + (float) h / 3;
        float s = BASE_SPACING * scale;
        switch (state){
            case Grey:
                graphics.setColor(0x808080);
                graphics.fillRect(x, y, w / xSize - s, w / xSize - s);
                break;
            case Red:
                graphics.setColor(0xFF0000);
                graphics.fillRect(x, y, w / xSize - s, w / xSize - s);
                break;
            case Blue:
                graphics.setColor(0x0000FF);
                graphics.fillRect(x, y, w / xSize - s, w / xSize - s);
                break;
            case Empty:
                graphics.setColor(0);
                graphics.fillRect(x, y, w / xSize - s, w / xSize - s);
                break;
        }
    }

    void  TouchCell(int Touchx, int Touchy, int xSize, float scale, IGraphics graphics){
        int w = graphics.getWidth();
        int h = graphics.getHeight();
        float x = (xPos * (float) w / xSize) + (float) w / 5;
        float y = (yPos * (float) w / xSize) + (float) h / 3;
        float s = BASE_SPACING * scale;


        if (Touchx >= x && Touchx < x + (w/xSize - s))
            if (Touchy >= y && Touchy < y + (w/xSize - s)){
                //esta dentro le cambiamos el estado
                changeState();
            }
    }
}

