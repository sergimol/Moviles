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
    IEngine engine;

    public FinalState() {
    }

    @Override
    public void init(IEngine e) {
        engine = e;
        title = e.getGraphics().newFont("CuteEasterFont.ttf", Font.PLAIN, (int) (0.3f * (e.getGraphics().relationAspectDimension()/10) / e.getGraphics().getScale()));
    }

    @Override
    public void update(double deltaTime) {

    }


    @Override
    public void render(IGraphics graphics) {

        //renderizar otro objeto como puede ser el boton
        String word;
        graphics.setColor(0X00000000);

        //Render FinalState
        word = "Volver";
        graphics.drawText(word, (int) graphics.getOriginalWidth() / 2 - graphics.getFontWidth(word) / 2, (int) (graphics.getOriginalHeight() * 0.9));
        graphics.setFont(title);
        word = "ENHORABUENA";
        graphics.drawText(word, (int) graphics.getOriginalWidth() / 2 - graphics.getFontWidth(word) / 2, (int) (graphics.getOriginalHeight() * 0.1));
    }

    @Override
    public void handleInput() {
        List<IInput.TouchEvent> events = engine.getInput().getTouchEvents();

        ListIterator<IInput.TouchEvent> i = events.listIterator();
        while (i.hasNext()) {
            IInput.TouchEvent o = i.next();
            if ((((IInput.Event) o).type == IInput.InputTouchType.TOUCH_DOWN)) {

                //FUNCIONALIDAD BOTON VOLVER
                InitialState st = new InitialState();
                st.setPrevious(this);
                engine.setState(st);
                st.init(engine);
            }
        }

        engine.getInput().emptyTouchEvents();
    }

    @Override
    public void setPrevious(IState st) {

    }
}

