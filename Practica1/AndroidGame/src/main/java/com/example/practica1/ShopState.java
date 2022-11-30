package com.example.practica1;

import android.graphics.Bitmap;

import com.example.androidengine.AEngine;
import com.example.androidengine.AFont;
import com.example.androidengine.AGraphics;
import com.example.androidengine.AImage;
import com.example.androidengine.AInput;
import com.example.androidengine.State;

import java.util.List;
import java.util.ListIterator;


public class ShopState extends State {

    AImage background;

    Button BackButton;
    AImage BackButtonImage;

    public ShopState() {
    }


    @Override
    public void init(AEngine e) {
        engine = e;
        //System.out.println("Escala: " + e.getGraphics().getScale() + "Math.log(): " + Math.log(e.getGraphics().relationAspectDimension()));

        background = e.getGraphics().newImage("GolemsShop.png");
        //BackButton
        BackButtonImage = e.getGraphics().newImage("BackButton.png");
        BackButton = new Button(BackButtonImage, 0, 0, e.getGraphics().getCanvasAspectRelationWidth() * 0.15f, e.getGraphics().getCanvasAspectRelationHeight() * 0.15f);
        BackButton.moveButton((int) (BackButton.getSizeX() / 2), (int) (BackButton.getSizeY() / 2));

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
        if (background != null) {
            background.resizeImage((int) graphics.getCanvasAspectRelationWidth(), (int) graphics.getCanvasAspectRelationHeight());
            graphics.drawImage(background, engine.getGraphics().getOriginalWidth() / 2 - background.getWidth() / 2, engine.getGraphics().getOriginalHeight() / 2 - background.getHeight() / 2, background.getWidth(), background.getHeight());
        }
        if (BackButton != null)
            BackButton.render(graphics);
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
                if (BackButton.click(o.x, o.y)) {
                    engine.setState(previous);
                }
            }

            engine.getInput().emptyTouchEvents();
        }
    }

}
