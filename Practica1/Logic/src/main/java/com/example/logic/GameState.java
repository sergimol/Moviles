package com.example.logic;

import com.example.interfaces.IEngine;
import com.example.interfaces.IGraphics;
import com.example.interfaces.IImage;
import com.example.interfaces.IState;

public class GameState implements IState {
    IEngine engine;
    Board board;
    int xCells, yCells;

    public GameState(int x, int y) {
        xCells = x;
        yCells = y;
        board = new Board(xCells, yCells);
    }

    @Override
    public void init(IEngine e) {
        engine = e;
    }


    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void render(IGraphics graphics) {
        for (int i = 0; i < xCells; ++i) {
            for (int j = 0; j < yCells; ++j) {
                board.getCell(i, j).render(graphics, board.getxSize(), board.getySize());
            }
        }
    }

    @Override
    public void handleInput() {


    }
}
