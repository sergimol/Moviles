package com.example.practica1;

import com.example.androidengine.AGraphics;
import com.example.androidengine.AImage;

public class Lives {
    int maxLives = 3;
    int hearts = 3;

    private float posX, posY, sizeX, sizeY, spacing;

    AImage container, hearth;

    public void subtractLife(){
        hearts--;
    }
    public void addLife(){
        hearts++;
    }
    int getHearts(){return  hearts;}


    public void setContainer(AImage a){
        container = a;
    }

    public void setHeart(AImage a){
        hearth = a;
    }

    public void setSpacing (float s){
        spacing = s;
    }
    public void setPos(float x, float y){
        posX = x; posY = y;
    }
    public void setSize(float x, float y){
        sizeX = x; sizeY = y;
    }
    public void render(AGraphics g){
        //para los corazones rellenoz
        for (int i = 0; i < hearts; i++){
            g.drawImage(hearth, (posX * (1 + i) + spacing * (i)), posY, (int)sizeX, (int)sizeY);
        }
        //para el resto de corazones vacios
        for (int i = hearts; i < maxLives; i++){
            g.drawImage(container, (posX * (1 + i) + spacing * (i)), posY, (int)sizeX, (int)sizeY);
        }
    }

}
