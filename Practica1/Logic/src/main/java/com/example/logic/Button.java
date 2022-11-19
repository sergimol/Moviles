package com.example.logic;

import java.awt.Color;

enum States {
    normal, selected
}


public class Button {

    private AFont fuente;
    private String texto;
    private float PosX;
    private float PosY;
    private float SizeX;
    private float SizeY;
    float TextSize;


    private float clickTopX;
    private float clickTopY;
    private float clickBottomX;
    private float clickBottomY;
    int color;


    Button(AFont f, String text, float x, float y, float sizeX_, float sizeY_, int c, float TextSize_) {
        fuente = f;
        texto = text;
        PosX = x;
        PosY = y;
        SizeX = sizeX_;
        SizeY = sizeY_;
        color = c;
        TextSize = TextSize_;
    }


    void render(AGraphics graphics) {
        //calculo logico del cuadrado para el pulsado
        clickTopX = (PosX - (SizeX / 2));
        clickTopY = (PosY - (SizeY / 2));

        clickBottomX = (clickTopX + SizeX);
        clickBottomY = (clickTopY + SizeY);

        graphics.setColor(color);
        graphics.fillRect(clickTopX, clickTopY, SizeX, SizeY);

        if (fuente != null){
            graphics.setFont(fuente,TextSize);
            graphics.setColor(0xFF000000);
            graphics.drawText(texto, clickTopX + SizeX / 8, clickBottomY - SizeY / 3);
        }
    }

    void setColor(int c) {
        color = c;
    }

    boolean click(float x, float y) {

        System.out.println("pos " + x + " " + y + " limites " + clickTopX + " " + clickTopY + " botton " + clickBottomX + " " + clickBottomY);

        if (x >= clickTopX && x < clickBottomX)
            if (y >= clickTopY && y < clickBottomY) {
                return true;
            }
        return false;
    }


}
