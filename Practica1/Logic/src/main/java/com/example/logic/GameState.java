package com.example.logic;

import com.example.interfaces.IEngine;
import com.example.interfaces.IGraphics;
import com.example.interfaces.IImage;
import com.example.interfaces.IState;


public class GameState implements IState {

    Board board;

    IImage image;
    int xCells, yCells;

    public GameState(int x, int y) {
        //graphics = gr;
        System.out.println(System.getProperty("user.dir"));
        xCells = x;
        yCells = y;
        board = new Board(xCells, yCells);
    }

    @Override
    public void init(IGraphics graphics) {
        image = graphics.newImage("apedra.png");
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
