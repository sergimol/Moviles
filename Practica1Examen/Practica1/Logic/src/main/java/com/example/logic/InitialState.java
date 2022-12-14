package com.example.logic;

import com.example.interfaces.IEngine;
import com.example.interfaces.IFont;
import com.example.interfaces.IGraphics;
import com.example.interfaces.IImage;
import com.example.interfaces.IInput;
import com.example.interfaces.IState;

import java.awt.Font;
import java.util.List;
import java.util.ListIterator;

public class InitialState implements IState {

    IFont title;
    IFont playButton;
    IEngine engine;
    Button myBoton;
    IImage imagen;
    IState previous = null;


    public InitialState() {

    }


    @Override
    public void init(IEngine e) {
        engine = e;
        //System.out.println("Escala: " + e.getGraphics().getScale() + "Math.log(): " + Math.log(e.getGraphics().relationAspectDimension()));
        title = e.getGraphics().newFont("CuteEasterFont.ttf", Font.PLAIN, (int) (0.6f * (e.getGraphics().relationAspectDimension() / 10) / e.getGraphics().getScale()));
        playButton = e.getGraphics().newFont("Larissa.ttf", Font.PLAIN, (int) (0.4f * (e.getGraphics().relationAspectDimension() / 10) / e.getGraphics().getScale()));

        myBoton = new Button(playButton, "Jugar", e.getGraphics().getOriginalWidth() / 2, e.getGraphics().getOriginalHeight() / 2, e.getGraphics().getOriginalWidth() * 0.3f, e.getGraphics().getOriginalHeight() * 0.1f, 0xFFFF8000, 20);
        imagen = e.getGraphics().newImage("apedra.png");

    }

    @Override
    public void start() {
        engine.getAudio().playSound("music");
        engine.getAudio().loop("music", true);
    }

    @Override
    public void update(double deltaTime) {

    }


    @Override
    public void render(IGraphics graphics) {


        if (imagen != null) {
            //graphics.drawImage(imagen,100,10,10,10);
        }
        //renderizar otro objeto como puede ser el boton
        String word;
        if (title != null) {
            graphics.setFont(title, 28);
            word = "NANOGRAMOS";
            graphics.setColor(0XFF000000);
            graphics.drawText(word, graphics.getOriginalWidth() / 2 - graphics.getFontWidth(word) / 2, (int) (graphics.getOriginalHeight() * 0.1));
        }
        if (myBoton != null)
            myBoton.render(graphics);
    }

    @Override
    public void handleInput() {


        List<IInput.TouchEvent> events = engine.getInput().getTouchEvents();
        ListIterator<IInput.TouchEvent> i = events.listIterator();
        while (i.hasNext()) {
            IInput.TouchEvent o = i.next();
            if ((((IInput.Event) o).type == IInput.InputTouchType.TOUCH_DOWN)) {
                //FUNCIONALIDAD BOTON JUGAR
                //cambio a la siguiente escena
                //creo al siguiente escena y la a??ado al engine

                if (myBoton.click(((IInput.Event) o).x, (((IInput.Event) o).y))) {
                    LevelSelectionState st = new LevelSelectionState();
                    //GameState st = new GameState(5, 5);
                    st.setPrevious(this);
                    engine.setState(st);
                    st.init(engine);
                }
            }

            engine.getInput().emptyTouchEvents();
        }
    }

    @Override
    public void setPrevious(IState st) {

    }

    @Override
    public IState getprevious() {
        return previous;
    }


}
