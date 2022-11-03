package com.example.desktopgame;

import javax.swing.JFrame;

import com.example.engine.JEngine;
import com.example.engine.JGraphics;
import com.example.logic.GameState;

public class DesktopGame {

    private Thread renderThread;
    private static JEngine engine;

    public static void main(String[] args) {
        JFrame window = new JFrame("Mi aplicación");
        window.setSize(600, 400);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);


        GameState state = new GameState(5, 5);
        JEngine javaEngine = new JEngine(window);
        javaEngine.setState(state);
    }
}