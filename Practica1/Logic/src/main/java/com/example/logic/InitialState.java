package com.example.logic;

import java.awt.Font;

import com.example.interfaces.IEngine;
import com.example.interfaces.IFont;
import com.example.interfaces.IGraphics;
import com.example.interfaces.IImage;
import com.example.interfaces.IInput;

import java.awt.Component;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class InitialState extends GameState{

    IImage imagen;
    IFont font_;

    public InitialState(int x, int y) {
        super(x, y);
    }


    @Override
    public void init(IEngine e){
        engine = e;
        //añadir una imagen
        imagen = e.getGraphics().newImage("apedra.png");

        font_ = e.getGraphics().newFont("CuteEasterFont.ttf", Font.PLAIN,40);
    }


    @Override
    public void render(IGraphics graphics) {

        //renderizar otro objeto como puede ser el boton
            if (imagen != null)
                graphics.drawImage(imagen, 50, 50, 50,50);
            if (font_ != null){
                graphics.setFont(font_);
                graphics.drawText("AY DIOS MIO", 50, 500);
            }
    }

    @Override
    public void handleInput() {
        List<IInput.TouchEvent> events = engine.getInput().getTouchEvents();

        ListIterator<IInput.TouchEvent> i = events.listIterator();
        while (i.hasNext()){
            IInput.TouchEvent o = i.next();
            if (/*((IInput.Event)o).source == imagen &&*/(((IInput.Event)o).type == IInput.InputTouchType.TOUCH_DOWN)){
                //cambio a la siguiente escena
                //creo al siguiente escena y la añado al engine
                GameState st = new GameState(10,10);
                st.setPrevious(this);
                engine.setState(st);
                st.init(engine);
            }
        }

        engine.getInput().emptyTouchEvents();

    }


}
