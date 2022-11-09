package com.example.logic;

import com.example.interfaces.IEngine;
import com.example.interfaces.IFont;
import com.example.interfaces.IGraphics;
import com.example.interfaces.IInput;
import com.example.interfaces.IState;

import java.awt.Font;
import java.util.List;
import java.util.ListIterator;

public class LevelSelectionState implements IState {
    IFont volver;
    IFont text;
    IEngine engine;
    IState previous = null;
    Button backBoton;

    public LevelSelectionState() {

    }

    @Override
    public void init(IEngine e) {
        engine = e;

        volver = e.getGraphics().newFont("Larissa.ttf", Font.PLAIN, (int) (0.3f * (e.getGraphics().relationAspectDimension() / 10) / e.getGraphics().getScale()));
        backBoton = new Button(volver, "← Volver", e.getGraphics().getOriginalWidth() * 0.15f, e.getGraphics().getOriginalHeight() * 0.04f, e.getGraphics().getOriginalWidth() * 0.3f, e.getGraphics().getOriginalHeight() * 0.05f, 0XFFFFFFFF);
    }

    @Override
    public void update(double deltaTime) {

    }


    @Override
    public void render(IGraphics graphics) {

        //renderizar otro objeto como puede ser el boton
        String word;
        graphics.setColor(0X00000000);

        //Render LevelSelectionState
        //word = "← Volver";
        //graphics.drawText(word, (int) graphics.getOriginalWidth() * 0.05f, (int) (graphics.getOriginalHeight() * 0.05));
        word = "Selecciona el tamaño del puzzle";
        graphics.drawText(word, graphics.getOriginalWidth() / 2 - graphics.getFontWidth(word) / 2, (int) (graphics.getOriginalHeight() * 0.2));

        if (backBoton != null)
            backBoton.render(graphics);
        //Render GameState
//        word = "← Rendirse";
//        graphics.drawText(word, (int) graphics.getOriginalWidth() * 0.05f, (int) (graphics.getOriginalHeight() * 0.05));
//        word = "\uD83D\uDD0D︎Comprobar";
//        graphics.drawText(word, (int) graphics.getOriginalWidth() - graphics.getFontWidth(word), (int) (graphics.getOriginalHeight() * 0.05));
    }

    @Override
    public void handleInput() {
        List<IInput.TouchEvent> events = engine.getInput().getTouchEvents();

        ListIterator<IInput.TouchEvent> i = events.listIterator();
        while (i.hasNext()) {
            IInput.TouchEvent o = i.next();
            if (backBoton.click(((IInput.Event) o).x, (((IInput.Event) o).y))) {
                engine.setState(previous);
            }
        }

        engine.getInput().emptyTouchEvents();
    }

    @Override
    public void setPrevious(IState st) {
        previous = st;
    }
}
