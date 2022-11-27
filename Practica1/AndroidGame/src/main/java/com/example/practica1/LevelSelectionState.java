package com.example.practica1;

import com.example.androidengine.AEngine;
import com.example.androidengine.AFont;
import com.example.androidengine.AGraphics;
import com.example.androidengine.AInput;
import com.example.androidengine.State;

//import java.awt.Font;
import java.util.List;
import java.util.ListIterator;

public class LevelSelectionState extends State {
    AFont volver;
    AFont text;
    Button backBoton;
    Button botonesNiveles[][];

    public LevelSelectionState() {

    }

    @Override
    public void init(AEngine e) {
        engine = e;

        volver = e.getGraphics().newFont("Larissa.ttf", 1, (int) (0.3f * (e.getGraphics().relationAspectDimension() / 10) / e.getGraphics().getScale()));
        backBoton = new Button(volver, "← Volver", e.getGraphics().getOriginalWidth() * 0.15f, e.getGraphics().getOriginalHeight() * 0.04f, e.getGraphics().getOriginalWidth() * 0.3f, e.getGraphics().getOriginalHeight() * 0.05f, 0XFFFFFFFF,20);

        botonesNiveles = new Button[2][3];

        botonesNiveles[0][0] = new Button(volver, "4x4", e.getGraphics().getOriginalWidth() * (1 / 4.0f), e.getGraphics().getOriginalHeight() * 0.4f, e.getGraphics().getOriginalWidth() * 0.2f, e.getGraphics().getOriginalWidth() * 0.2f, 0Xff808080,10);
        botonesNiveles[0][1] = new Button(volver, "5x5", e.getGraphics().getOriginalWidth() * (2 / 4.0f), e.getGraphics().getOriginalHeight() * 0.4f, e.getGraphics().getOriginalWidth() * 0.2f, e.getGraphics().getOriginalWidth() * 0.2f, 0Xff808080,10);
        botonesNiveles[0][2] = new Button(volver, "5x10", e.getGraphics().getOriginalWidth() * (3 / 4.0f), e.getGraphics().getOriginalHeight() * 0.4f, e.getGraphics().getOriginalWidth() * 0.2f, e.getGraphics().getOriginalWidth() * 0.2f, 0Xff808080,10);

        botonesNiveles[1][0] = new Button(volver, "8x8", e.getGraphics().getOriginalWidth() * (1 / 4.0f), e.getGraphics().getOriginalHeight() * 0.6f, e.getGraphics().getOriginalWidth() * 0.2f, e.getGraphics().getOriginalWidth() * 0.2f, 0Xff808080,10);
        botonesNiveles[1][1] = new Button(volver, "10x10", e.getGraphics().getOriginalWidth() * (2 / 4.0f), e.getGraphics().getOriginalHeight() * 0.6f, e.getGraphics().getOriginalWidth() * 0.2f, e.getGraphics().getOriginalWidth() * 0.2f, 0Xff808080,10);
        botonesNiveles[1][2] = new Button(volver, "10x15", e.getGraphics().getOriginalWidth() * (3 / 4.0f), e.getGraphics().getOriginalHeight() * 0.6f, e.getGraphics().getOriginalWidth() * 0.2f, e.getGraphics().getOriginalWidth() * 0.2f, 0Xff808080,10);


    }

    @Override
    public void start() {

    }

    @Override
    public void update(double deltaTime) {

    }


    @Override
    public void render(AGraphics graphics) {

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

        if (botonesNiveles != null) {
            //Recorro columnas
            for (int i = 0; i < botonesNiveles.length; ++i) {
                //Recorro filas
                for (int w = 0; w < botonesNiveles[0].length; ++w) {
                    botonesNiveles[i][w].render(graphics);
                }
            }

        }

        //Render GameState
//        word = "← Rendirse";
//        graphics.drawText(word, (int) graphics.getOriginalWidth() * 0.05f, (int) (graphics.getOriginalHeight() * 0.05));
//        word = "\uD83D\uDD0D︎Comprobar";
//        graphics.drawText(word, (int) graphics.getOriginalWidth() - graphics.getFontWidth(word), (int) (graphics.getOriginalHeight() * 0.05));
    }

    @Override
    public void handleInput() {
        List<AInput.TouchEvent> events = engine.getInput().getTouchEvents();
        ListIterator<AInput.TouchEvent> i = events.listIterator();

        while (i.hasNext()) {
            AInput.TouchEvent o = i.next();

            if (o.type == AInput.InputTouchType.TOUCH_DOWN) {
                if (backBoton.click(o.x, o.y)) {
                    engine.setState(previous);
                }

                else if (botonesNiveles[0][0].click(o.x, o.y)) {
                    GameState st = new GameState(4, 4);
                    st.setPrevious(this);
                    engine.setState(st);
                    st.init(engine);
                }
                else if (botonesNiveles[0][1].click(o.x, o.y)) {
                    GameState st = new GameState(5, 5);
                    st.setPrevious(this);
                    engine.setState(st);
                    st.init(engine);
                }
                else if (botonesNiveles[0][2].click(o.x, o.y)) {
                    GameState st = new GameState(5, 10);
                    st.setPrevious(this);
                    engine.setState(st);
                    st.init(engine);
                }
                else if (botonesNiveles[1][0].click(o.x, o.y)) {
                    GameState st = new GameState(8, 8);
                    st.setPrevious(this);
                    engine.setState(st);
                    st.init(engine);
                }
                else if (botonesNiveles[1][1].click(o.x, o.y)) {
                    GameState st = new GameState(10, 10);
                    st.setPrevious(this);
                    engine.setState(st);
                    st.init(engine);
                }
                else if (botonesNiveles[1][2].click(o.x, o.y)) {
                    GameState st = new GameState(10, 15);
                    st.setPrevious(this);
                    engine.setState(st);
                    st.init(engine);
                }
            }
        }

        engine.getInput().emptyTouchEvents();
    }
}
