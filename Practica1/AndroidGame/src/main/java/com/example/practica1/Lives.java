package com.example.practica1;

import android.os.Bundle;

import com.example.androidengine.AGraphics;
import com.example.androidengine.AImage;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Lives implements Serializable {
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


    public void metodoQueSerializa(){
        try
        {
            FileOutputStream fos = new FileOutputStream("Corazones.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(hearts);
            oos.close();
            fos.close();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }


    public void metodoQueDesSerializa(){
        try
        {
            FileInputStream fis = new FileInputStream("Corazones.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);

            hearts = (int) ois.readObject();

            ois.close();
            fis.close();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
            return;
        }
        catch (ClassNotFoundException c)
        {
            System.out.println("Class not found");
            c.printStackTrace();
            return;
        }
    }


    public void save (Bundle outState){
        outState.putSerializable("vidas", hearts);
    }
    public void load (Bundle saveState){
        hearts = (int) saveState.getSerializable("vidas");
    }

    /*
    private void writeObject(java.io.ObjectOutputStream out){
        throws IOException;
    }
    private void readObject(java.io.ObjectInputStream in){
        throws IOException, ClassNotFoundException;
    }
    private void readObjectNoData(){
        throws ObjectStreamException;
    }
*/

}
