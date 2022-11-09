package com.example.desktopgame;

import javax.swing.JFrame;

import com.example.engine.JEngine;
import com.example.logic.GameState;
import com.example.logic.InitialState;
import com.example.logic.LevelSelectionState;

public class DesktopGame {

    public static void main(String[] args) {
        JFrame window = new JFrame("Mi aplicación");
        window.setSize(400, 600);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);

        //Creamos el Engine
        InitialState state = new InitialState();
        JEngine javaEngine = new JEngine(window);
        javaEngine.setState(state);
        state.init(javaEngine);
    }
}