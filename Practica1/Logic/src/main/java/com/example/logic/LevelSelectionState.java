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
    Button botonesNiveles[][];

    public LevelSelectionState() {

    }

    @Override
    public void init(IEngine e) {
        engine = e;

        volver = e.getGraphics().newFont("Larissa.ttf", Font.PLAIN, (int) (0.3f * (e.getGraphics().relationAspectDimension() / 10) / e.getGraphics().getScale()));
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
        List<IInput.TouchEvent> events = engine.getInput().getTouchEvents();
        ListIterator<IInput.TouchEvent> i = events.listIterator();

        while (i.hasNext()) {
            IInput.TouchEvent o = i.next();

            if (((IInput.Event) o).type == IInput.InputTouchType.TOUCH_DOWN) {
                if (backBoton.click(((IInput.Event) o).x, (((IInput.Event) o).y))) {
                    engine.setState(previous);
                }

                else if (botonesNiveles[0][0].click(((IInput.Event) o).x, (((IInput.Event) o).y))) {
                    GameState st = new GameState(4, 4);
                    st.setPrevious(this);
                    engine.setState(st);
                    st.init(engine);
                }
                else if (botonesNiveles[0][1].click(((IInput.Event) o).x, (((IInput.Event) o).y))) {
                    GameState st = new GameState(5, 5);
                    st.setPrevious(this);
                    engine.setState(st);
                    st.init(engine);
                }
                else if (botonesNiveles[0][2].click(((IInput.Event) o).x, (((IInput.Event) o).y))) {
                    GameState st = new GameState(5, 10);
                    st.setPrevious(this);
                    engine.setState(st);
                    st.init(engine);
                }
                else if (botonesNiveles[1][0].click(((IInput.Event) o).x, (((IInput.Event) o).y))) {
                    GameState st = new GameState(8, 8);
                    st.setPrevious(this);
                    engine.setState(st);
                    st.init(engine);
                }
                else if (botonesNiveles[1][1].click(((IInput.Event) o).x, (((IInput.Event) o).y))) {
                    GameState st = new GameState(10, 10);
                    st.setPrevious(this);
                    engine.setState(st);
                    st.init(engine);
                }
                else if (botonesNiveles[1][2].click(((IInput.Event) o).x, (((IInput.Event) o).y))) {
                    GameState st = new GameState(10, 15);
                    st.setPrevious(this);
                    engine.setState(st);
                    st.init(engine);
                }
            }
        }

        engine.getInput().emptyTouchEvents();
    }


    @Override
    public void setPrevious(IState st) {
        previous = st;
    }

    @Override
    public IState getprevious() {
        return previous;
    }
}
