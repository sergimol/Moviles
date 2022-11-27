package com.example.practica1;

import com.example.androidengine.AEngine;
import com.example.androidengine.AFont;
import com.example.androidengine.AGraphics;
import com.example.androidengine.AInput;
import com.example.androidengine.State;

import java.awt.Font;
import java.util.List;
import java.util.ListIterator;

public class FinalState extends State {
    AFont title;
    AFont volver;
    Button backBoton;
    Board board;

    public FinalState(Board b) {
        board = b;
    }

    @Override
    public void init(AEngine e) {
        engine = e;
        title = e.getGraphics().newFont("CuteEasterFont.ttf", Font.PLAIN, (int) (0.4f * (e.getGraphics().relationAspectDimension() / 10) / e.getGraphics().getScale()));
        volver = e.getGraphics().newFont("Larissa.ttf", Font.PLAIN, (int) (0.3f * (e.getGraphics().relationAspectDimension() / 10) / e.getGraphics().getScale()));
        backBoton = new Button(volver, "← Volver", e.getGraphics().getOriginalWidth() / 2, e.getGraphics().getOriginalHeight() - e.getGraphics().getOriginalHeight() * 0.1f, e.getGraphics().getOriginalWidth() * 0.3f, e.getGraphics().getOriginalHeight() * 0.05f, 0XFFFFFFFF, 15);
        engine.getAudio().playSound("tada");
    }

    @Override
    public void render(AGraphics graphics) {

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
        List<AInput.TouchEvent> events = engine.getInput().getTouchEvents();

        ListIterator<AInput.TouchEvent> i = events.listIterator();
        while (i.hasNext()) {
            AInput.TouchEvent o = i.next();
            if ((o.type == AInput.InputTouchType.TOUCH_DOWN) {

                //FUNCIONALIDAD BOTON VOLVER
                if (backBoton.click(o.x, o.y)) {
                    //InitialState st = new InitialState();
                    //st.setPrevious(this);
                    engine.setState(previous.getprevious().getprevious());
                    //st.init(engine);
                }
            }
        }

        engine.getInput().emptyTouchEvents();
    }
}

