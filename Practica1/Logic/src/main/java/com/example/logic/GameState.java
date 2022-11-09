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
    IState previous = null;

    public GameState(int x, int y) {
        xCells = x;
        yCells = y;
        board = new Board(xCells, yCells);
    }

    @Override
    public void init(IEngine e) {
        engine = e;
        board.init(e);
    }


    @Override
    public void update(double deltaTime) {
            handleInput();
    }

    @Override
    public void render(IGraphics graphics) {
        board.render(graphics);
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
                /*int canvasWidth = engine.getGraphics().getCanvasWidth();
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
                }*/
                float xInCanvas = ((IInput.Event) o).x;
                float yInCanvas = ((IInput.Event) o).y;
                board.handleInput(xInCanvas, yInCanvas);
            }
            else if (((IInput.Event)o).type == IInput.InputTouchType.TOUCH_UP){
                board.resetAllowChangeStatesCells();
            }
        }
        engine.getInput().emptyTouchEvents();
    }

    @Override
    public void setPrevious(IState st) {
        previous = st;
    }

    IState getPrevious(){
        return previous;
    }
}
