package com.example.practica1;

import com.example.androidengine.AEngine;
import com.example.androidengine.AFont;
import com.example.androidengine.AGraphics;
import com.example.androidengine.AImage;
import com.example.androidengine.AInput;
import com.example.androidengine.State;

//import java.awt.Font;
import java.util.List;
import java.util.ListIterator;

public class InitialState extends State {

    AFont title;
    AFont playButton;
    Button myBoton;
    AImage imagen;

    public InitialState() {

    }


    @Override
    public void init(AEngine e) {
        engine = e;
        //System.out.println("Escala: " + e.getGraphics().getScale() + "Math.log(): " + Math.log(e.getGraphics().relationAspectDimension()));
        title = e.getGraphics().newFont("CuteEasterFont.ttf", 1, (int) (0.6f * (e.getGraphics().relationAspectDimension() / 10) / e.getGraphics().getScale()));
        playButton = e.getGraphics().newFont("Larissa.ttf", 1, (int) (0.4f * (e.getGraphics().relationAspectDimension() / 10) / e.getGraphics().getScale()));

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
    public void render(AGraphics graphics) {


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


        List<AInput.TouchEvent> events = engine.getInput().getTouchEvents();
        ListIterator<AInput.TouchEvent> i = events.listIterator();
        while (i.hasNext()) {
            AInput.TouchEvent o = i.next();
            if (o.type == AInput.InputTouchType.TOUCH_DOWN) {
                //FUNCIONALIDAD BOTON JUGAR
                //cambio a la siguiente escena
                //creo al siguiente escena y la añado al engine

                if (myBoton.click(o.x, o.y)) {
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

}
