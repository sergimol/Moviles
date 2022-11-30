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

public class FinalState extends State {
    AFont title;
    Board board;

    Button BackButton;
    AImage BackButtonImage;

    public FinalState(Board b) {
        board = b;
    }

    @Override
    public void init(AEngine e) {
        engine = e;
        title = e.getGraphics().newFont("CuteEasterFont.ttf", 1, (int) (0.4f * (e.getGraphics().relationAspectDimension() / 10) / e.getGraphics().getScale()));
        //BackButton
        BackButtonImage = e.getGraphics().newImage("BackButton.png");
        BackButton = new Button(BackButtonImage, 0, 0, e.getGraphics().getCanvasAspectRelationWidth() * 0.15f, e.getGraphics().getCanvasAspectRelationHeight() * 0.15f);
        BackButton.moveButton((int) (BackButton.getSizeX() / 2), (int) (BackButton.getSizeY() / 2));

        engine.getAudio().playSound("tada");
    }

    @Override
    public void render(AGraphics graphics) {
        if (BackButton != null)
            BackButton.render(graphics);

        String word;
        graphics.setColor(0xFF000000);
        graphics.setFont(title, 20);
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
            if ((o.type == AInput.InputTouchType.TOUCH_DOWN)) {

                //FUNCIONALIDAD BOTON VOLVER
                if (BackButton.click(o.x, o.y)) {
                    engine.setState(previous.getprevious());
                }
            }
        }
        engine.getInput().emptyTouchEvents();
    }
}

