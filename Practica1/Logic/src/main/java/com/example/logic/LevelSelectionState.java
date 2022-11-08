package com.example.logic;

import com.example.interfaces.IEngine;
import com.example.interfaces.IFont;
import com.example.interfaces.IGraphics;
import com.example.interfaces.IInput;

import java.awt.Font;
import java.util.List;
import java.util.ListIterator;

public class LevelSelectionState extends GameState {
    IFont volver;
    IFont text;

    public LevelSelectionState(int x, int y) {
        super(x, y);
    }

    @Override
    public void init(IEngine e) {
        engine = e;

        volver = e.getGraphics().newFont("CuteEasterFont.ttf", Font.PLAIN, 20);
        text = e.getGraphics().newFont("CuteEasterFont.ttf", Font.PLAIN, 20);
    }


    @Override
    public void render(IGraphics graphics) {

//        //renderizar otro objeto como puede ser el boton
//        if (volver != null) {
//            graphics.setFont(volver);
//            String word = "<- Volver";
//            graphics.setColor(0X00000000);
//            graphics.drawText(word, 10, 100);
//        }
//        if (text != null) {
//            graphics.setFont(text);
//            String word = "Selecciona el tamaño del puzzle";
//            graphics.setColor(0X00000000);
//            graphics.drawText(word, graphics.getCanvasWidth() / 2 - graphics.getFontWidth(word) / 2, 300);
//        }
    }

    @Override
    public void handleInput() {
        List<IInput.TouchEvent> events = engine.getInput().getTouchEvents();

        ListIterator<IInput.TouchEvent> i = events.listIterator();
        while (i.hasNext()) {
            IInput.TouchEvent o = i.next();
            if (/*((IInput.Event)o).source == imagen &&*/(((IInput.Event) o).type == IInput.InputTouchType.TOUCH_DOWN)) {
                //VOLVER AL MENU
                engine.setState(previous);
                previous.init(engine);

                //SELECCIONA UN TAMANO DE NIVEL
//                GameState st = new GameState(10, 10);
//                st.setPrevious(this);
//                engine.setState(st);
//                st.init(engine);
            }
        }

        engine.getInput().emptyTouchEvents();
    }
}
