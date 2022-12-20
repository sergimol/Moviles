package com.example.practica1;

import com.example.androidengine.AEngine;
import com.example.androidengine.AFont;
import com.example.androidengine.AGraphics;
import com.example.androidengine.AImage;

enum States {
    normal, selected
}


public class Button {

    //Font Button
    private AFont fuente;
    private String texto;
    private float PosX;
    private float PosY;
    private float SizeX;
    private float SizeY;
    float TextSize;

    //Image Button
    private AImage imagen;
    private AImage buyImagen;
    private boolean isButtonFont;

    private float clickTopX;
    private float clickTopY;
    private float clickBottomX;
    private float clickBottomY;
    int color;

    boolean buttonUnlocked;
    int moneyToUnlock;

    Button(AFont f, String text, float x, float y, float sizeX_, float sizeY_, int c, float TextSize_, boolean unlock) {
        fuente = f;
        texto = text;
        PosX = x;
        PosY = y;
        SizeX = sizeX_;
        SizeY = sizeY_;
        color = c;
        TextSize = TextSize_;
        isButtonFont = true;
        buttonUnlocked = unlock;
        moneyToUnlock = 0;
    }

    Button(AImage ima, float x, float y, float sizeX_, float sizeY_, boolean unlock) {
        imagen = ima;
        PosX = x;
        PosY = y;
        SizeX = sizeX_;
        SizeY = sizeY_;
        isButtonFont = false;
        buttonUnlocked = unlock;
        moneyToUnlock = 0;
    }

    Button(AImage ima, float x, float y, float sizeX_, float sizeY_, boolean unlock, int money, AImage buyImage) {
        imagen = ima;
        PosX = x;
        PosY = y;
        SizeX = sizeX_;
        SizeY = sizeY_;
        isButtonFont = false;
        buttonUnlocked = unlock;
        moneyToUnlock = money;
        buyImagen = buyImage;
    }


    void render(AGraphics graphics) {
        //calculo logico del cuadrado para el pulsado
        clickTopX = (PosX - (SizeX / 2));
        clickTopY = (PosY - (SizeY / 2));

        clickBottomX = (clickTopX + SizeX);
        clickBottomY = (clickTopY + SizeY);

        if (isButtonFont) {
            graphics.setColor(color);
            graphics.fillRect(clickTopX, clickTopY, SizeX, SizeY);

            if (fuente != null) {
                graphics.setFont(fuente, TextSize);
                graphics.setColor(0xFF000000);
                graphics.drawText(texto, clickTopX + SizeX / 2 - graphics.getFontWidth(texto) / 2, clickBottomY - SizeY / 3);
            }
        } else {
            graphics.drawImage(imagen, clickTopX, clickTopY, (int) (clickBottomX - clickTopX), (int) (clickBottomY - clickTopY));
            if (moneyToUnlock > 0) {
                float clickTopXbuy = PosX - (buyImagen.getWidth() / 2);
                float clickTopYbuy = PosY - (buyImagen.getHeight() / 2);

                float clickBottomXbuy = (clickTopXbuy + SizeY);
                float clickBottomYbuy = (clickTopYbuy + SizeY);

                graphics.drawImage(buyImagen, clickTopXbuy, clickTopYbuy, (int) (clickBottomXbuy - clickTopXbuy), (int) (clickBottomYbuy - clickTopYbuy));
            }
        }
    }

    void setColor(int c) {
        color = c;
    }

    boolean click(float x, float y) {
        if (!buttonUnlocked && moneyToUnlock == 0)
            return false;

        if (x >= clickTopX && x < clickBottomX)
            if (y >= clickTopY && y < clickBottomY) {
                return true;
            }
        return false;
    }

    public int getSizeX() {
        return (int) SizeX;
    }

    public int getSizeY() {
        return (int) SizeY;
    }

    public void moveButton(int x, int y) {
        PosX = x;
        PosY = y;

    }

    public void changeImage(AImage i) {
        imagen = i;
    }

    public AImage getImagen() {
        return imagen;
    }

    public void unlockButton() {
        buttonUnlocked = true;
    }

    public boolean isButtonUnlocked() {
        return buttonUnlocked;
    }
}
