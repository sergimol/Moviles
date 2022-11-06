package com.example.logic;

import com.example.interfaces.IEngine;
import com.example.interfaces.IGraphics;
import com.example.interfaces.IImage;
import com.example.interfaces.IState;

public class GameState implements IState {
    IEngine engine;
    Board board;
    IImage image;
    int xCells, yCells;

    public GameState(IEngine e,int x, int y) {
        engine = e;
        xCells = x;
        yCells = y;
        board = new Board(xCells, yCells);
    }

    @Override
    public void init() {
    }


    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void render(IGraphics graphics) {
        /*if (image != null)
            graphics.drawImage(image, 0, 0);*/
        for(int i = 0; i < xCells; ++i)
            for(int j = 0; j < yCells; ++j){
                board.getCell(i, j).render(graphics);
            }
    }

    @Override
    public void handleInput() {


    }
}
