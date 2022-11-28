package com.example.practica1;

import androidx.compose.ui.text.font.Font;

import com.example.androidengine.AEngine;
import com.example.androidengine.AFont;
import com.example.androidengine.AGraphics;
import com.example.androidengine.AInput;
import com.example.androidengine.ATimer;
import com.example.androidengine.State;

import java.util.List;
import java.util.ListIterator;


public class GameState extends State {
    Board board;
    int xCells, yCells;
    AFont font;
    Button backBoton;
    Button comprobarBoton;

    int wrongCount, missingCount;
    boolean showingWrong = false;
    ATimer timer;

    public GameState(int x, int y) {
        xCells = x;
        yCells = y;
        board = new Board(xCells, yCells);
    }

    @Override
    public void init(AEngine e) {
        engine = e;


        font = e.getGraphics().newFont("Larissa.ttf", 1, (int) (0.3f * (e.getGraphics().relationAspectDimension() / 10) / e.getGraphics().getScale()));
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
    public void render(AGraphics graphics) {
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
        List<AInput.TouchEvent> events = engine.getInput().getTouchEvents();

        ListIterator<AInput.TouchEvent> ev = events.listIterator();
        while (ev.hasNext()) {
            AInput.TouchEvent o = ev.next();

            if (o.type == AInput.InputTouchType.TOUCH_DOWN) {
            }
            else if (o.type == AInput.InputTouchType.TOUCH_MOVE){
                float xInCanvas = o.x;
                float yInCanvas = o.y;
                board.handleInput(xInCanvas, yInCanvas, engine, false);
            }
            else if (o.type == AInput.InputTouchType.TOUCH_UP) {
                float xInCanvas = o.x;
                float yInCanvas = o.y;
                board.handleInput(xInCanvas, yInCanvas, engine, true);
                board.resetAllowChangeStatesCells();
            }

            if (o.type == AInput.InputTouchType.TOUCH_DOWN) {

                //FUNCIONALIDAD BOTON RENDIRSE
                if (backBoton.click(o.x, o.y)) {
                    engine.setState(previous);
                }
                //FUNCIONALIDAD BOTON COMPROBAR
                if (comprobarBoton.click(o.x, o.y)) {
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
}
