package com.example.practica1;

import com.example.androidengine.AEngine;
import com.example.androidengine.AFont;
import com.example.androidengine.AGraphics;
import com.example.androidengine.AImage;
import com.example.androidengine.AInput;
import com.example.androidengine.State;

import java.util.List;
import java.util.ListIterator;


public class EasyLevelSelectionState extends State {
    AFont text;
    Button BackButton;
    AImage BackButtonImage;
    Button botonesNiveles[][];
    AImage levelUnlocked;
    AImage levelLocked;

    public EasyLevelSelectionState() {

    }

    @Override
    public void init(AEngine e) {
        engine = e;

        text = e.getGraphics().newFont("Larissa.ttf", 1, (int) (0.8f * (e.getGraphics().relationAspectDimension() / 10) / e.getGraphics().getScale()));

        //BackButton
        BackButtonImage = e.getGraphics().newImage("BackButton.png");
        BackButton = new Button(BackButtonImage, 0, 0, e.getGraphics().getOriginalWidth() * 0.15f, e.getGraphics().getOriginalHeight() * 0.15f);
        BackButton.moveButton((int) (BackButton.getSizeX() / 2), (int) (BackButton.getSizeY() / 2));

        //Botones niveles
        botonesNiveles = new Button[5][4];
        levelUnlocked = e.getGraphics().newImage("LevelUnlocked.png");
        levelLocked = e.getGraphics().newImage("LevelLocked.png");


        float ButtonSizeX = 0.18f;
        float ButtonSizeY = 0.18f;

        botonesNiveles[0][0] = new Button(levelUnlocked, e.getGraphics().getOriginalWidth() * (1 / 5.0f), e.getGraphics().getOriginalHeight() * (4 / 9.0f), e.getGraphics().getOriginalWidth() * ButtonSizeX, e.getGraphics().getOriginalWidth() * ButtonSizeY);
        botonesNiveles[0][1] = new Button(levelLocked, e.getGraphics().getOriginalWidth() * (2 / 5.0f), e.getGraphics().getOriginalHeight() * (4 / 9.0f), e.getGraphics().getOriginalWidth() * ButtonSizeX, e.getGraphics().getOriginalWidth() * ButtonSizeY);
        botonesNiveles[0][2] = new Button(levelLocked, e.getGraphics().getOriginalWidth() * (3 / 5.0f), e.getGraphics().getOriginalHeight() * (4 / 9.0f), e.getGraphics().getOriginalWidth() * ButtonSizeX, e.getGraphics().getOriginalWidth() * ButtonSizeY);
        botonesNiveles[0][3] = new Button(levelLocked, e.getGraphics().getOriginalWidth() * (4 / 5.0f), e.getGraphics().getOriginalHeight() * (4 / 9.0f), e.getGraphics().getOriginalWidth() * ButtonSizeX, e.getGraphics().getOriginalWidth() * ButtonSizeY);

        botonesNiveles[1][0] = new Button(levelLocked, e.getGraphics().getOriginalWidth() * (1 / 5.0f), e.getGraphics().getOriginalHeight() * (5 / 9.0f), e.getGraphics().getOriginalWidth() * ButtonSizeX, e.getGraphics().getOriginalWidth() * ButtonSizeY);
        botonesNiveles[1][1] = new Button(levelLocked, e.getGraphics().getOriginalWidth() * (2 / 5.0f), e.getGraphics().getOriginalHeight() * (5 / 9.0f), e.getGraphics().getOriginalWidth() * ButtonSizeX, e.getGraphics().getOriginalWidth() * ButtonSizeY);
        botonesNiveles[1][2] = new Button(levelLocked, e.getGraphics().getOriginalWidth() * (3 / 5.0f), e.getGraphics().getOriginalHeight() * (5 / 9.0f), e.getGraphics().getOriginalWidth() * ButtonSizeX, e.getGraphics().getOriginalWidth() * ButtonSizeY);
        botonesNiveles[1][3] = new Button(levelLocked, e.getGraphics().getOriginalWidth() * (4 / 5.0f), e.getGraphics().getOriginalHeight() * (5 / 9.0f), e.getGraphics().getOriginalWidth() * ButtonSizeX, e.getGraphics().getOriginalWidth() * ButtonSizeY);

        botonesNiveles[2][0] = new Button(levelLocked, e.getGraphics().getOriginalWidth() * (1 / 5.0f), e.getGraphics().getOriginalHeight() * (6 / 9.0f), e.getGraphics().getOriginalWidth() * ButtonSizeX, e.getGraphics().getOriginalWidth() * ButtonSizeY);
        botonesNiveles[2][1] = new Button(levelLocked, e.getGraphics().getOriginalWidth() * (2 / 5.0f), e.getGraphics().getOriginalHeight() * (6 / 9.0f), e.getGraphics().getOriginalWidth() * ButtonSizeX, e.getGraphics().getOriginalWidth() * ButtonSizeY);
        botonesNiveles[2][2] = new Button(levelLocked, e.getGraphics().getOriginalWidth() * (3 / 5.0f), e.getGraphics().getOriginalHeight() * (6 / 9.0f), e.getGraphics().getOriginalWidth() * ButtonSizeX, e.getGraphics().getOriginalWidth() * ButtonSizeY);
        botonesNiveles[2][3] = new Button(levelLocked, e.getGraphics().getOriginalWidth() * (4 / 5.0f), e.getGraphics().getOriginalHeight() * (6 / 9.0f), e.getGraphics().getOriginalWidth() * ButtonSizeX, e.getGraphics().getOriginalWidth() * ButtonSizeY);

        botonesNiveles[3][0] = new Button(levelLocked, e.getGraphics().getOriginalWidth() * (1 / 5.0f), e.getGraphics().getOriginalHeight() * (7 / 9.0f), e.getGraphics().getOriginalWidth() * ButtonSizeX, e.getGraphics().getOriginalWidth() * ButtonSizeY);
        botonesNiveles[3][1] = new Button(levelLocked, e.getGraphics().getOriginalWidth() * (2 / 5.0f), e.getGraphics().getOriginalHeight() * (7 / 9.0f), e.getGraphics().getOriginalWidth() * ButtonSizeX, e.getGraphics().getOriginalWidth() * ButtonSizeY);
        botonesNiveles[3][2] = new Button(levelLocked, e.getGraphics().getOriginalWidth() * (3 / 5.0f), e.getGraphics().getOriginalHeight() * (7 / 9.0f), e.getGraphics().getOriginalWidth() * ButtonSizeX, e.getGraphics().getOriginalWidth() * ButtonSizeY);
        botonesNiveles[3][3] = new Button(levelLocked, e.getGraphics().getOriginalWidth() * (4 / 5.0f), e.getGraphics().getOriginalHeight() * (7 / 9.0f), e.getGraphics().getOriginalWidth() * ButtonSizeX, e.getGraphics().getOriginalWidth() * ButtonSizeY);

        botonesNiveles[4][0] = new Button(levelLocked, e.getGraphics().getOriginalWidth() * (1 / 5.0f), e.getGraphics().getOriginalHeight() * (8 / 9.0f), e.getGraphics().getOriginalWidth() * ButtonSizeX, e.getGraphics().getOriginalWidth() * ButtonSizeY);
        botonesNiveles[4][1] = new Button(levelLocked, e.getGraphics().getOriginalWidth() * (2 / 5.0f), e.getGraphics().getOriginalHeight() * (8 / 9.0f), e.getGraphics().getOriginalWidth() * ButtonSizeX, e.getGraphics().getOriginalWidth() * ButtonSizeY);
        botonesNiveles[4][2] = new Button(levelLocked, e.getGraphics().getOriginalWidth() * (3 / 5.0f), e.getGraphics().getOriginalHeight() * (8 / 9.0f), e.getGraphics().getOriginalWidth() * ButtonSizeX, e.getGraphics().getOriginalWidth() * ButtonSizeY);
        botonesNiveles[4][3] = new Button(levelLocked, e.getGraphics().getOriginalWidth() * (4 / 5.0f), e.getGraphics().getOriginalHeight() * (8 / 9.0f), e.getGraphics().getOriginalWidth() * ButtonSizeX, e.getGraphics().getOriginalWidth() * ButtonSizeY);

    }

    @Override
    public void start() {

    }

    @Override
    public void update(double deltaTime) {

    }


    @Override
    public void render(AGraphics graphics) {
        //Background
        graphics.setColor(0XFFFFB23C);
        graphics.fillRect(0, 0, graphics.getOriginalWidth(), graphics.getOriginalHeight());

        if (text != null) {
            String word;
            graphics.setFont(text, 20);
            word = "Selecciona Nivel";
            graphics.setColor(0XFF000000);
            graphics.drawText(word, graphics.getOriginalWidth() / 2 - graphics.getFontWidth(word) / 2, (int) (graphics.getOriginalHeight() * 0.25));
        }
        if (BackButton != null)
            BackButton.render(graphics);

        if (botonesNiveles != null) {
            //Recorro columnas
            for (int i = 0; i < botonesNiveles.length; ++i) {
                //Recorro filas
                for (int w = 0; w < botonesNiveles[0].length; ++w) {
                    botonesNiveles[i][w].render(graphics);
                }
            }
        }
    }

    @Override
    public void handleInput() {
        List<AInput.TouchEvent> events = engine.getInput().getTouchEvents();
        ListIterator<AInput.TouchEvent> i = events.listIterator();

        while (i.hasNext()) {
            AInput.TouchEvent o = i.next();

            if (o.type == AInput.InputTouchType.TOUCH_DOWN) {
                if (BackButton.click(o.x, o.y)) {
                    engine.setState(previous);
                } else if (botonesNiveles[0][0].click(o.x, o.y)) {
                    GameState st = new GameState(4, 4);
                    st.setPrevious(this);
                    engine.setState(st);
                    st.init(engine);
                } else if (botonesNiveles[0][1].click(o.x, o.y)) {

                } else if (botonesNiveles[0][2].click(o.x, o.y)) {

                } else if (botonesNiveles[1][0].click(o.x, o.y)) {

                } else if (botonesNiveles[1][1].click(o.x, o.y)) {

                } else if (botonesNiveles[1][2].click(o.x, o.y)) {

                }
            }
        }

        engine.getInput().emptyTouchEvents();
    }
}
