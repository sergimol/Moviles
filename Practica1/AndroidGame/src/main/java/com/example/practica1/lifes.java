package com.example.practica1;

public class lifes {
    int maxLifes = 3;
    int hearts = 3;


    public void restLife(){
        hearts--;
    }
    public void addLife(){
        hearts++;
    }
    int getHearts(){return  hearts;}



}
