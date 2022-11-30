package com.example.practica1;

public class Lives {
    int maxLives = 3;
    int hearts = 3;


    public void subtractLife(){
        hearts--;
    }
    public void addLife(){
        hearts++;
    }
    int getHearts(){return  hearts;}



}
