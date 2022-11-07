package com.example.logic;

import com.example.interfaces.IEngine;
import com.example.interfaces.IGraphics;
import com.example.interfaces.IInput;
import com.example.interfaces.IState;

import java.util.List;
import java.util.ListIterator;

public class GameState implements IState {
    IEngine engine;
    Board board;
    int xCells, yCells;
    GameState previous = null;

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

            handleInput();
    }

    @Override
    public void render(IGraphics graphics) {
        for (int i = 0; i < xCells; ++i) {
            for (int j = 0; j < yCells; ++j) {
                board.getCell(i, j).render(graphics, board.getxSize(), graphics.getScale());
            }
        }
    }

    @Override
    public void handleInput() {
        //check touch cells and buttons to play
        List<IInput.TouchEvent> events = engine.getInput().getTouchEvents();

        ListIterator<IInput.TouchEvent> ev = events.listIterator();
        while (ev.hasNext()) {
            IInput.TouchEvent o = ev.next();

            if (((IInput.Event)o).type == IInput.InputTouchType.TOUCH_DOWN || ((IInput.Event)o).type == IInput.InputTouchType.TOUCH_MOVE) {
                // Ancho del canvas dentro la pantalla
                int canvasWidth = engine.getGraphics().getCanvasWidth();
                // Alto del canvas dentro la pantalla
                int canvasHeight = engine.getGraphics().getHeight();
                // Coordenada x del principio del canvas dentro de la pantalla
                int xZeroInCanvas = (engine.getGraphics().getWidth() - canvasWidth) / 2;
                // Coordenada x del principio del tablero dentro de la pantalla
                int xZeroInBoard = xZeroInCanvas + canvasWidth / 5;


                if(((IInput.Event) o).x >= xZeroInBoard && ((IInput.Event) o).x <= xZeroInCanvas + canvasWidth){
                    // Coordenada x del toque respecto del principio del tablero
                    int xCoordInBoard = ((IInput.Event) o).x - xZeroInCanvas - canvasWidth / 5;
                    // Coordenada x dentro de la lógica del tablero (>= 0 && < xCells)
                    float xLogicInBoard = ((float)xCoordInBoard / canvasWidth * xCells);

                    // Coordenada y del toque respecto del principio del tablero
                    int yCoordInBoard = ((IInput.Event) o).y  - canvasHeight / 3;
                    float yLogicInBoard = ((float)yCoordInBoard / canvasWidth * yCells);
                    board.getCell((int) xLogicInBoard, (int)yLogicInBoard).changeState();
                }
            }
            else   if (((IInput.Event)o).type == IInput.InputTouchType.TOUCH_UP){
                board.resetAllowChangeStatesCells();
            }
                /*for (int i = 0; i < xCells; ++i)
                    for (int j = 0; j < yCells; ++j) {
                        board.getCell(i, j).touchCell(((IInput.Event)o).x,((IInput.Event)o).y , board.getxSize(), engine.getGraphics().getScale(), engine.getGraphics());
                    }*/

        }
        engine.getInput().emptyTouchEvents();
    }

    public void setPrevious(GameState st){
        previous = st;
    }
    GameState getPrevious(){
        return  previous;
    }
}
