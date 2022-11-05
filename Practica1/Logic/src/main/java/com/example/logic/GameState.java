package com.example.logic;

import com.example.interfaces.IEngine;
import com.example.interfaces.IGraphics;
import com.example.interfaces.IImage;
import com.example.interfaces.IState;


public class GameState implements IState {

    Board board;

    IImage image;

    public GameState(int xSize, int ySize) {
        //graphics = gr;
        System.out.println(System.getProperty("user.dir"));
        //board = new Board(xSize, ySize);
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
        if (image != null) {
            graphics.drawImage(image, 0, 0, graphics.getWidth(), graphics.getHeight());
        }
    }

    @Override
    public void handleInput() {


    }


}
