package com.example.logic;

import com.example.interfaces.IEngine;
import com.example.interfaces.IFont;
import com.example.interfaces.IGraphics;
import com.example.interfaces.IInput;
import com.example.interfaces.IState;
import com.example.interfaces.ITimer;

import java.awt.Font;
import java.util.List;
import java.util.ListIterator;


public class GameState implements IState {
    IEngine engine;
    Board board;
    int xCells, yCells;
    IState previous = null;
    IFont font;
    Button backBoton;
    Button comprobarBoton;

    int wrongCount, missingCount;
    boolean showingWrong = false;
    ITimer timer;

    public GameState(int x, int y) {
        xCells = x;
        yCells = y;
        board = new Board(xCells, yCells);
    }

    @Override
    public void init(IEngine e) {
        engine = e;


        font = e.getGraphics().newFont("Larissa.ttf", Font.PLAIN, (int) (0.3f * (e.getGraphics().relationAspectDimension() / 10) / e.getGraphics().getScale()));
        board.init(e, font);
        backBoton = new Button(font, "Rendirse", e.getGraphics().getOriginalWidth() * 0.15f, e.getGraphics().getOriginalHeight() * 0.04f, e.getGraphics().getOriginalWidth() * 0.3f, e.getGraphics().getOriginalHeight() * 0.05f, 0XFFFFFFFF,20);
        comprobarBoton = new Button(font, "Comprobar", e.getGraphics().getOriginalWidth() - e.getGraphics().getOriginalWidth() * 0.23f, e.getGraphics().getOriginalHeight() * 0.04f, e.getGraphics().getOriginalWidth() * 0.4f, e.getGraphics().getOriginalHeight() * 0.08f, 0XFFFFFFFF,15);
        timer = e.getTimer();


    }

    @Override
    public void start() {

    }


    @Override
    public void update(double deltaTime) {
        if (timer != null) {
            if (timer.isEnded() && showingWrong) {
                showingWrong = false;
                board.resetRedCells();
            }
        }

        handleInput();
    }

    @Override
    public void render(IGraphics graphics) {
        if (!showingWrong) {
            if (backBoton != null)
                backBoton.render(graphics);
            if (comprobarBoton != null)
                comprobarBoton.render(graphics);
        } else {
            String word;
            if (font != null) {
                int auxWrongCount = wrongCount;
                int auxMissingCount = missingCount;
                System.out.println("Missing: " + missingCount + ", Wrong: " + wrongCount);

                graphics.setFont(font,20);
                if (auxMissingCount > 1)
                    word = "Te falta " + auxMissingCount + " casillas";
                else
                    word = "Te falta " + auxMissingCount + " casilla";

                graphics.setColor(0XFFFF0000);
                graphics.drawText(word, graphics.getOriginalWidth() / 2 - graphics.getFontWidth(word) / 2, (int) (graphics.getOriginalHeight() * 0.1));

                if (auxWrongCount > 1)
                    word = "Tienes mal " + auxWrongCount + " casillas";
                else
                    word = "Tienes mal " + auxWrongCount + " casilla";

                graphics.setColor(0XFFFF0000);
                graphics.drawText(word, graphics.getOriginalWidth() / 2 - graphics.getFontWidth(word) / 2, (int) (graphics.getOriginalHeight() * 0.15));
            }
        }
        board.render(graphics);
    }

    @Override
    public void handleInput() {
        //check touch cells and buttons to play
        List<IInput.TouchEvent> events = engine.getInput().getTouchEvents();

        ListIterator<IInput.TouchEvent> ev = events.listIterator();
        while (ev.hasNext()) {
            IInput.TouchEvent o = ev.next();

            if (((IInput.Event) o).type == IInput.InputTouchType.TOUCH_DOWN || ((IInput.Event) o).type == IInput.InputTouchType.TOUCH_MOVE) {
                float xInCanvas = ((IInput.Event) o).x;
                float yInCanvas = ((IInput.Event) o).y;
                board.handleInput(xInCanvas, yInCanvas, engine);
            } else if (((IInput.Event) o).type == IInput.InputTouchType.TOUCH_UP) {
                board.resetAllowChangeStatesCells();
            }

            if (((IInput.Event) o).type == IInput.InputTouchType.TOUCH_DOWN) {

                //FUNCIONALIDAD BOTON RENDIRSE
                if (backBoton.click(((IInput.Event) o).x, (((IInput.Event) o).y))) {
                    engine.setState(previous);
                }
                //FUNCIONALIDAD BOTON COMPROBAR
                if (comprobarBoton.click(((IInput.Event) o).x, (((IInput.Event) o).y))) {
                    //wrongCount, missingCount
                    if (!showingWrong) {
                        int a[] = board.checkBoard();
                        wrongCount = a[0];
                        missingCount = a[1];
                        //Si has completado el puzzle
                        if (wrongCount == 0 && missingCount == 0) {
                            FinalState st = new FinalState(board);
                            st.setPrevious(this);
                            engine.setState(st);
                            st.init(engine);
                        } else {
                            timer.setTimer(2);
                            timer.startTimer();
                            showingWrong = true;
                        }
                    }
                }
            }
        }
        engine.getInput().emptyTouchEvents();
    }

    @Override
    public void setPrevious(IState st) {
        previous = st;
    }

    @Override
    public IState getprevious() {
        return previous;
    }

    IState getPrevious() {
        return previous;
    }
}
