package com.example.logic;

import com.example.interfaces.IEngine;
import com.example.interfaces.IFont;
import com.example.interfaces.IGraphics;
import com.example.interfaces.IInput;
import com.example.interfaces.IState;

import java.awt.Font;
import java.util.List;
import java.util.ListIterator;

public class FinalState implements IState {
    IFont title;
    IFont volver;
    IState previous = null;
    Button backBoton;
    IEngine engine;
    Board board;

    public FinalState(Board b) {
        board = b;
    }

    @Override
    public void init(IEngine e) {
        engine = e;
        title = e.getGraphics().newFont("CuteEasterFont.ttf", Font.PLAIN, (int) (0.4f * (e.getGraphics().relationAspectDimension() / 10) / e.getGraphics().getScale()));
        volver = e.getGraphics().newFont("Larissa.ttf", Font.PLAIN, (int) (0.3f * (e.getGraphics().relationAspectDimension() / 10) / e.getGraphics().getScale()));
        backBoton = new Button(volver, "← Volver", e.getGraphics().getOriginalWidth() / 2, e.getGraphics().getOriginalHeight() - e.getGraphics().getOriginalHeight() * 0.1f, e.getGraphics().getOriginalWidth() * 0.3f, e.getGraphics().getOriginalHeight() * 0.05f, 0XFFFFFFFF, 15);
        engine.getAudio().playSound("tada");
    }

    @Override
    public void start() {

    }

    @Override
    public void update(double deltaTime) {

    }


    @Override
    public void render(IGraphics graphics) {

        if (backBoton != null)
            backBoton.render(graphics);

        String word;
        graphics.setColor(0xFF000000);
        graphics.setFont(title,20);
        word = "ENHORABUENA";
        graphics.drawText(word, (int) graphics.getOriginalWidth() / 2 - graphics.getFontWidth(word) / 2, (int) (graphics.getOriginalHeight() * 0.1));

        board.renderWin(graphics);
    }

    @Override
    public void handleInput() {
        List<IInput.TouchEvent> events = engine.getInput().getTouchEvents();

        ListIterator<IInput.TouchEvent> i = events.listIterator();
        while (i.hasNext()) {
            IInput.TouchEvent o = i.next();
            if (((IInput.Event) o).type == IInput.InputTouchType.TOUCH_DOWN) {

                //FUNCIONALIDAD BOTON VOLVER
                if (backBoton.click(((IInput.Event) o).x, (((IInput.Event) o).y))) {
                    //InitialState st = new InitialState();
                    //st.setPrevious(this);
                    engine.setState(previous.getprevious().getprevious());
                    //st.init(engine);
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
}

