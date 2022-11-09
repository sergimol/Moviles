package com.example.logic;

import com.example.interfaces.IEngine;
import com.example.interfaces.IFont;
import com.example.interfaces.IGraphics;
import com.example.interfaces.IInput;

import java.awt.Font;
import java.util.List;
import java.util.ListIterator;

public class FinalState extends GameState {
    IFont title;
    IFont volver;
    Button backBoton;

    public FinalState(int x, int y) {
        super(x, y);
    }

    @Override
    public void init(IEngine e) {
        engine = e;
        title = e.getGraphics().newFont("CuteEasterFont.ttf", Font.PLAIN, (int) (0.4f * (e.getGraphics().relationAspectDimension() / 10) / e.getGraphics().getScale()));
        volver = e.getGraphics().newFont("Larissa.ttf", Font.PLAIN, (int) (0.3f * (e.getGraphics().relationAspectDimension() / 10) / e.getGraphics().getScale()));
        backBoton = new Button(volver, "← Volver", e.getGraphics().getOriginalWidth() / 2, e.getGraphics().getOriginalHeight() - e.getGraphics().getOriginalHeight() * 0.1f, e.getGraphics().getOriginalWidth() * 0.3f, e.getGraphics().getOriginalHeight() * 0.05f, 0XFFFFFFFF);
    }


    @Override
    public void render(IGraphics graphics) {

        if (backBoton != null)
            backBoton.render(graphics);

        String word;
        graphics.setColor(0);
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
            //FUNCIONALIDAD BOTON VOLVER
            if (backBoton.click(((IInput.Event) o).x, (((IInput.Event) o).y))) {
                engine.setState(previous);
                InitialState st = new InitialState();
                st.setPrevious(this);
                engine.setState(st);
                st.init(engine);
            }
        }

        engine.getInput().emptyTouchEvents();
    }
}

