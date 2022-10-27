package com.example.logic;

import com.example.interfaces.IEngine;
import com.example.interfaces.IGraphics;
import com.example.interfaces.IState;

public class GameState implements IState{

    Board board;
    IEngine engine;

    GameState(IEngine e, int xSize, int ySize){
        engine = e;
        board = new Board(xSize, ySize);
    }

    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void render(IGraphics graphics) {

    }
}
