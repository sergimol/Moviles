package com.example.practica1;

import android.graphics.Bitmap;
import android.os.Bundle;

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

    Button PresetStyleButton;
    AImage PresetStyleButtonImage;

    Button RedStyleButton;
    AImage RedStyleButtonImage;
    AImage RedStyleButtonShopImage;

    String categoryType;
    boolean[] unlocks;
    String unlockType;
    int[] moneyUnlocks;

    public ShopState() {
    }

    public ShopState(Bundle savedData) {
        if (savedData != null) {
            Bundle PrevScene = savedData.getBundle("Scene");
            switch (PrevScene.getInt("SceneType")) {
                case 0:
                    previous = new InitialState(PrevScene);
                    break;
                case 1:
                    previous = new LevelSelectionState(PrevScene);
                    break;
                case 2:
                    previous = new GameState(PrevScene.getInt("x"), PrevScene.getInt("y"), PrevScene);
                    break;
                default:
                    previous = new InitialState(null);
                    break;
            }
        }
    }


    @Override
    public void init(AEngine e) {
        super.init(e);
        engine = e;
        //System.out.println("Escala: " + e.getGraphics().getScale() + "Math.log(): " + Math.log(e.getGraphics().relationAspectDimension()));

        //Desbloqueo de tienda
        categoryType = "tienda_0";
        unlocks = ((MainActivity) engine.getContext()).loadUnlocks(categoryType, 2);
        unlocks[0] = true;
        unlockType = "compras_0";
        moneyUnlocks = ((MainActivity) engine.getContext()).loadUnlocksINT(unlockType, 2);

        background = e.getGraphics().newImage("GolemsShop.png");
        //BackButton
        BackButtonImage = e.getGraphics().newImage(engine.getStyle() + "BackButton.png");
        BackButton = new Button(BackButtonImage, 0, 0, e.getGraphics().getCanvasAspectRelationWidth() * 0.15f, e.getGraphics().getCanvasAspectRelationHeight() * 0.15f, true);
        BackButton.moveButton((int) (BackButton.getSizeX() / 2), (int) (BackButton.getSizeY() / 2));

        PresetStyleButtonImage = e.getGraphics().newImage("PresetLevelUnlocked.png");
        PresetStyleButton = new Button(PresetStyleButtonImage, e.getGraphics().getOriginalWidth() / 2, e.getGraphics().getOriginalHeight() / 2, e.getGraphics().getCanvasAspectRelationWidth() * 0.6f, e.getGraphics().getCanvasAspectRelationHeight() * 0.15f, unlocks[0]);
        //ArcadeButton
        RedStyleButtonImage = e.getGraphics().newImage("RedLevelUnlocked.png");
        RedStyleButtonShopImage = e.getGraphics().newImage("PresetMoneyButton.png");
        RedStyleButton = new Button(RedStyleButtonImage, e.getGraphics().getOriginalWidth() / 2, e.getGraphics().getOriginalHeight() / 1.5f, e.getGraphics().getCanvasAspectRelationWidth() * 0.6f, e.getGraphics().getCanvasAspectRelationHeight() * 0.15f, unlocks[1], moneyUnlocks[0], RedStyleButtonShopImage);


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

        if (BackButton != null) {
            if (!BackButton.getImagen().getName().equals(engine.getStyle() + "BackButton.png"))
                BackButton.changeImage(engine.getGraphics().newImage(engine.getStyle() + "BackButton.png"));
            BackButton.render(graphics);
        }

        if (PresetStyleButton != null)
            PresetStyleButton.render(graphics);
        if (RedStyleButton != null)
            RedStyleButton.render(graphics);
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
                } else if (PresetStyleButton.click(o.x, o.y)) {
                    engine.setStyle("Preset");
                } else if (RedStyleButton.click(o.x, o.y)) {
                    //
                    if (engine.dinero >= RedStyleButton.moneyToUnlock) {
                        //Si entra aqui es porque lo hemos desbloqueado
                        if (!unlocks[1]) {
                            //Unlockear boton
                            unlocks[1] = true;
                            ((MainActivity) engine.getContext()).saveUnlocks(unlocks, categoryType);
                        }

                        if (RedStyleButton.moneyToUnlock == 0) {
                            engine.setStyle("Red");
                        } else {
                            //Comprarlo
                            engine.dinero -= RedStyleButton.moneyToUnlock;
                            RedStyleButton.moneyToUnlock = 0;
                            moneyUnlocks[0] = 0;
                            ((MainActivity) engine.getContext()).saveUnlocksINT(moneyUnlocks, unlockType);
                        }
                    }
                }
            }

            engine.getInput().emptyTouchEvents();
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        Bundle estaEscena = new Bundle();
        estaEscena.putInt("SceneType", 3);
        //de haber una PrevScene para seguir con este bucle de Bundles
        outState.putBundle("Scene", estaEscena);

        previous.onSaveInstanceState(estaEscena);
    }
}
