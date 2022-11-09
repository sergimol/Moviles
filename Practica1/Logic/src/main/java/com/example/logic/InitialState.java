package com.example.logic;

import java.awt.Font;

import com.example.interfaces.IEngine;
import com.example.interfaces.IFont;
import com.example.interfaces.IGraphics;
import com.example.interfaces.IImage;
import com.example.interfaces.IInput;
import com.example.interfaces.ITimer;

import java.awt.Component;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;

public class InitialState extends GameState {

    IFont title;
    ITimer timer;

    public InitialState(int x, int y) {
        super(x, y);
    }


    @Override
    public void init(IEngine e) {
        engine = e;
        //añadir una imagen
        title = e.getGraphics().newFont("CuteEasterFont.ttf", Font.PLAIN, (int) (4f * Math.log(e.getGraphics().relationAspectDimension()) * e.getGraphics().getScale()));
        timer = e.getTimer();
        timer.setTimer(2);
        timer.startTimer();
    }


    @Override
    public void render(IGraphics graphics) {

        //renderizar otro objeto como puede ser el boton
        String word;
        if (title != null) {
            graphics.setFont(title);
            word = "NANOGRAMOS";
            graphics.setColor(0X00000000);
            graphics.drawText(word, graphics.getOriginalWidth() / 2 - graphics.getFontWidth(word) / 2, (int) (graphics.getOriginalHeight() * 0.1));
            if (timer.isEnded()) {
                word = "Jugar";
                graphics.drawText(word, graphics.getOriginalWidth() / 2 - graphics.getFontWidth(word) / 2, (int) (graphics.getOriginalHeight() * 0.5));
            }
        }
    }

    @Override
    public void handleInput() {
        List<IInput.TouchEvent> events = engine.getInput().getTouchEvents();

        ListIterator<IInput.TouchEvent> i = events.listIterator();
        while (i.hasNext()) {
            IInput.TouchEvent o = i.next();
            if ((((IInput.Event) o).type == IInput.InputTouchType.TOUCH_DOWN)) {
                //FUNCIONALIDAD BOTON JUGAR
                LevelSelectionState st = new LevelSelectionState(2, 3);
                st.setPrevious(this);
                engine.setState(st);
                st.init(engine);
            }
        }

        engine.getInput().emptyTouchEvents();
    }


}
