package com.example.desktopgame;

import javax.swing.JFrame;

import com.example.engine.JEngine;
import com.example.logic.GameState;

public class DesktopGame {

    private Thread renderThread;
    private static JEngine engine;


    public static void main(String[] args) {
        JFrame window = new JFrame("Mi aplicación");
        window.setSize(600, 400);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        
        //Creamos el Engine
        JEngine javaEngine = new JEngine(window);

        GameState state = new GameState(javaEngine, 10, 10);
        javaEngine.setState(state);
    }
}