package com.example.desktopgame;

import javax.swing.JFrame;

import com.example.engine.JEngine;
import com.example.engine.JGraphics;
import com.example.logic.GameState;

public class DesktopGame {

    private Thread renderThread;
    private static JEngine engine;

    public static void main(String[] args) {
        JFrame renderView = new JFrame("Mi aplicación");
        renderView.setSize(600, 400);
        renderView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JGraphics render = new JGraphics(renderView);
        GameState state = new GameState(render);
        state.init();

        //scene.init(render);
        //render.setScene(scene);
        //render.resume();
    }
}