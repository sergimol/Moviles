package com.example.logic;

import com.example.interfaces.IEngine;
import com.example.interfaces.IGraphics;
import com.example.interfaces.IImage;
import com.example.interfaces.IState;


public class GameState implements IState {

    Board board;

    IImage image;
    IGraphics graphics;

    public GameState(IGraphics gr, int xSize, int ySize) {
        graphics = gr;
        image = graphics.newImage("apedra.png");
        board = new Board(xSize, ySize);
    }

    public void init() {
    }


    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void render() {
        graphics.drawImage(image, 0, 0);
    }

    @Override
    public void handleInput() {


    }
}
